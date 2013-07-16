lucene-stanford-lemmatizer
==========================

This is a library that adds NLP capabilities to Lucene-based search engines:
lemmatization and filtering based on part-of-speech (POS) tag. It used the
[state-of-the-art](http://tinyurl.com/pos-tagging) Stanford POS Tagger for
NLP support.

Lemmatizing is similar to stemming, except smarter: it takes into account
the context of a word to determine the correct lemma/stem. POS filtering is
a smarter replacement for stop lists. It allows filtering out all pronouns,
adverbs, etc.

For lemmatization and POS tagging to work best, your queries should be English
sentences instead of just bunches of keywords.

Getting started
---------------
Download this package and

* [Lucene 3.x](http://lucene.apache.org/core/)
* [Stanford POS Tagger](http://nlp.stanford.edu/software/tagger.shtml)
* [Ant](http://ant.apache.org/)
* [Guava](http://code.google.com/p/guava-libraries/)

Set your CLASSPATH to include the above, then issue `ant jar`.

In your search code, construct an EnglishLemmaAnalyzer instead of a
StandardAnalyzer (or whatever you normally use). Pass the filename of a
Stanford POS Tagger model file to the constructor (found in the `models/`
directory in the Stanford POS Tagger source directory.

Going further
-------------
It is possible to determine which parts-of-speech should be indexed by
subclassing the tokenizer. See the
[API docs](http://larsmans.github.com/lucene-stanford-lemmatizer)
for details.

Bugs
----
The implementation is limited to English, because the Stanford lemmatizer
only handles that languages. The POS tagger does Chinese and German, so it
should be possible to add those languages.
