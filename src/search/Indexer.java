package search;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexer {

	private IndexWriter indexWriter;
	
	public Indexer(String indexDirectoryPath) throws IOException {
		Directory indexDirectory = FSDirectory.open(Paths.get((indexDirectoryPath)));
		
		StandardAnalyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		
		indexWriter = new IndexWriter(indexDirectory, config);
	}
	
//	private Document getDocument(File file) throws IOException {
//		Document document = new Document();
//		
//	}
}
