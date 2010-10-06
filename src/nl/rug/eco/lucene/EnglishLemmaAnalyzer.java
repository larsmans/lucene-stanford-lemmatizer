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
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.tagger.maxent.TaggerConfig;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

/**
 * An analyzer that uses an {@link EnglishLemmaTokenizer}.
 *
 * @author  Lars Buitinck
 * @version 2010.1006
 */
public class EnglishLemmaAnalyzer extends Analyzer {
    private MaxentTagger posTagger;

    /**
     * Construct an analyzer with a tagger using the given model file.
     */
    public EnglishLemmaAnalyzer(String posModelFile) throws Exception {
        this(makeTagger(posModelFile));
    }

    /**
     * Construct an analyzer using the given tagger.
     */
    public EnglishLemmaAnalyzer(MaxentTagger tagger) {
        posTagger = tagger;
    }

    /**
     * Factory method for loading a POS tagger.
     */
    public static MaxentTagger makeTagger(String modelFile) throws Exception {
        TaggerConfig config = new TaggerConfig("-model", modelFile);
        // The final argument suppresses a "loading" message on stderr.
        return new MaxentTagger(modelFile, config, false);
    }

    @Override
    public TokenStream tokenStream(String fieldName, Reader input) {
        return new EnglishLemmaTokenizer(input, posTagger);
    }
}
