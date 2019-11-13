package main.java.Searcher;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.search.similarities.TFIDFSimilarity;
import org.apache.lucene.store.FSDirectory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Searcher {
    String index = "index";
    String field = "contents";
    Analyzer analyzer;

    public Searcher(){}

    public Map<String, ArrayList<String>> search(String queries, int hitsPerPage) throws Exception {
        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
        IndexSearcher searcher = new IndexSearcher(reader);
        Similarity similarity = new NewTFIDFSimilarity();
        searcher.setSimilarity(similarity);
        BufferedReader in = null;
        in = Files.newBufferedReader(Paths.get(queries), StandardCharsets.UTF_8);
        QueryParser parser = new QueryParser(field, this.analyzer);
        String line = "";
        Map<String,ArrayList<String>> finalResult = new HashMap<String, ArrayList<String>>();
        line = in.readLine();
        do {
            ArrayList<String> results = new ArrayList<String>();
            line = line.trim();
            if (line.length() == 0) {
                break;
            }
            Query query = parser.parse(line);
            System.out.println("Searching for: " + query.toString(field));
            List<ResultDoc> docs = doPagingSearch(in, searcher, query, hitsPerPage, false);
            for (int i=0; i<docs.size(); i++){
                results.add("doc: " + docs.get(i).getId() + " score:" + docs.get(i).getScore() + " docTitle: " + docs.get(i).getTitle() + " path:" + docs.get(i).getPath());
            }
            finalResult.put(query.toString(field), results);
            line = in.readLine();
            System.out.println("-------------------------------------------------------");
        } while (line != null);
        return finalResult;
    }

    public ArrayList<ResultDoc> doPagingSearch(BufferedReader in, IndexSearcher searcher, Query query, int hitsPerPage, boolean interactive) throws IOException {
        TopDocs results = searcher.search(query, 5 * hitsPerPage);
        ScoreDoc[] hits = results.scoreDocs;
        int numTotalHits = Math.toIntExact(results.totalHits.value);
        System.out.println(numTotalHits + " total matching documents");
        int start = 0;
        int end = Math.min(numTotalHits, hitsPerPage);
        ArrayList<ResultDoc> resultDocs = new ArrayList<ResultDoc>();

        for (int i = start; i < end; ++i) {
            Document doc = searcher.doc(hits[i].doc);
            resultDocs.add(new ResultDoc(hits[i].doc, hits[i].score, doc.get("path"), doc.get("title")));
        }
        return resultDocs;
    }
}
