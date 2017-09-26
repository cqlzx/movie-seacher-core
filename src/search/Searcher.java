package search;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Searcher {
	
	IndexSearcher indexSearcher;
	QueryParser parser;
	Query query;
	
	public Searcher(String indexDirPath) throws IOException {
		Directory indexDirectory = FSDirectory.open(Paths.get(indexDirPath));
		IndexReader reader = DirectoryReader.open(indexDirectory);
		indexSearcher = new IndexSearcher(reader);
		parser = new QueryParser(SearchConstant.CONTENTS, new StandardAnalyzer());
		
		parser.setDefaultOperator(QueryParser.Operator.AND);
	}
	
	public TopDocs search(String searchQuery) throws IOException, ParseException {
		query = parser.parse(searchQuery);
		return indexSearcher.search(query, SearchConstant.MAX_SEARCH);
	}
	
	public Document getDocument(ScoreDoc scoreDoc) throws IOException {
		return indexSearcher.doc(scoreDoc.doc);
	}
	
}
