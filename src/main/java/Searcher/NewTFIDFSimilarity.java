package main.java.Searcher;

import org.apache.lucene.search.similarities.TFIDFSimilarity;

public class NewTFIDFSimilarity extends TFIDFSimilarity {
    @Override
    public float tf(float v) {
        return (float) Math.log((double)v + 1);
    }

    @Override
    public float idf(long docFreq, long docCount) {
        return (float)Math.log((double) docCount/docFreq);
    }

    @Override
    public float lengthNorm(int i) {
        return 1;
    }
}
