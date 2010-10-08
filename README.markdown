lucene-stanford-lemmatizer
==========================

This is a library that adds some NLP capabilities to the Lucene search engine
library: lemmatization and filtering based on part-of-speech (POS) tag. The
combination of lemmatizing and POS filtering is intended to replace stemming
and stop lists.

Lemmatizing is similar to stemming, except smarter: it takes into account the
context of a word to determine the correct lemma/stem.

To build, the following packages are required:

* [Lucene 3.x](http://lucene.apache.org/java/)
* [Stanford POS Tagger](http://nlp.stanford.edu/software/tagger.shtml)
* [Ant](http://ant.apache.org/)
* [Guava](http://code.google.com/p/guava-libraries/)

To build, set your CLASSPATH to include all the relevant libraries, then
issue `ant jar`.

To use this package inside a Lucene-based search engine, construct an
EnglishLemmaAnalyzer instead of a StandardAnalyzer (or whatever you normally
use). Pass the filename of a Stanford POS Tagger model file to the
constructor; model files are usually in the `models/` directory in the
Stanford POS Tagger source code directory.
