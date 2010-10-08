/*
 * Lemmatizing library for Lucene
 * Copyright (C) 2010 Lars Buitinck
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package nl.rug.eco.lucene;

import java.io.*;
import java.util.*;
import com.google.common.collect.Iterables;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.process.Morphology;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;

/**
 * A tokenizer that retrieves the lemmas (base forms) of English words.
 * Relies internally on the sentence splitter and tokenizer supplied with
 * the Stanford POS tagger.
 *
 * @author  Lars Buitinck
 * @version 2010.1008
 */
public class EnglishLemmaTokenizer extends TokenStream {
    private Iterator<TaggedWord> tagged;
    private PositionIncrementAttribute posIncr;
    private TaggedWord currentWord;
    private TermAttribute termAtt;
    private boolean lemmaNext;

    /**
     * Construct a tokenizer processing the given input and a tagger
     * using the given model file.
     */
    public EnglishLemmaTokenizer(Reader input, String posModelFile)
            throws Exception {
        this(input, EnglishLemmaAnalyzer.makeTagger(posModelFile));
    }

    /**
     * Construct a tokenizer processing the given input using the given tagger.
     */
    public EnglishLemmaTokenizer(Reader input, MaxentTagger tagger) {
        super();

        lemmaNext = false;
        posIncr = addAttribute(PositionIncrementAttribute.class);
        termAtt = addAttribute(TermAttribute.class);

        List<ArrayList<? extends HasWord>> tokenized =
            MaxentTagger.tokenizeText(input);
        tagged = Iterables.concat(tagger.process(tokenized)).iterator();
    }

    /**
     * Consumers use this method to advance the stream to the next token.
     * The token stream emits inflected forms and lemmas interleaved (form1,
     * lemma1, form2, lemma2, etc.), giving lemmas and their inflected forms
     * the same PositionAttribute.
     */
    @Override
    public final boolean incrementToken() throws IOException {
        if (lemmaNext) {
            // Emit a lemma
            posIncr.setPositionIncrement(1);
            String tag  = currentWord.tag();
            String form = currentWord.word();
            termAtt.setTermBuffer(Morphology.stemStatic(form, tag).word());
        } else if (!tagged.hasNext()) {
            return false;
        } else {
            // Emit inflected form
            posIncr.setPositionIncrement(0);    // next in same position
            currentWord = tagged.next();
            termAtt.setTermBuffer(currentWord.word());
        }

        lemmaNext = !lemmaNext;
        return true;
    }
}
