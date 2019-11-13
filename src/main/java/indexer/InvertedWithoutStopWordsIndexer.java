
package main.java.indexer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class InvertedWithoutStopWordsIndexer extends Indexer {

    public InvertedWithoutStopWordsIndexer(String indexPath, String docsPath) {
        super(indexPath, docsPath);
    }

    public void index() {
        final Path docDir = Paths.get(this.docsPath);
        if (!Files.isReadable(docDir)) {
            System.out.println("Document directory '" + docDir.toAbsolutePath() + "' does not exist or is not readable," +
                    " please check the path");
            System.exit(1);
        }

        Date start = new Date();
        try {
            Directory indexDir = FSDirectory.open(Paths.get(this.indexPath));

            System.out.println("Indexing to directory '" + this.indexPath + "'...");
            Analyzer analyzer = new SimpleAnalyzer();
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
            iwc.setOpenMode(OpenMode.CREATE);
            IndexWriter writer = new IndexWriter(indexDir, iwc);

            this.indexDocs(writer, docDir);

            writer.close();

            Date end = new Date();
            System.out.println(end.getTime() - start.getTime() + " total milliseconds");
        } catch (IOException e) {
            System.out.println(" caught a " + e.getClass() +
                    "\n with message: " + e.getMessage());
        }
    }
}





