package search;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class SearchTest {

	String indexDir = "indexes";
	String dataDir = "subtitles";
	Indexer indexer;
	Searcher searcher;
	
	public static void main(String[] args) {
		SearchTest tester;
		try {
			tester = new SearchTest();
//			tester.createIndex();
			tester.search("dollars ");
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private void createIndex() throws IOException {
		indexer = new Indexer(indexDir);
		long startTime = System.currentTimeMillis();
		int numIndexed = indexer.createIndex(dataDir, new SrtFileFilter());
		long endTime = System.currentTimeMillis();
		indexer.close();
		System.out.println("numIndexed: " + numIndexed + ". Time used: " + (endTime - startTime) + "ms");
	}

	private void search(String searchQuery) throws IOException, ParseException {
		searcher = new Searcher(indexDir);
		long startTime = System.currentTimeMillis();
		TopDocs hits = searcher.search(searchQuery);
		long endTime = System.currentTimeMillis();
		
		System.out.println(hits.totalHits + " documents found. Time used: " + (endTime - startTime) + "ms");
		
		for (ScoreDoc scoreDoc : hits.scoreDocs) {
			Document doc = searcher.getDocument(scoreDoc);
			System.out.println("File: " + doc.get(SearchConstant.FILE_PATH));
		}
	}
}
