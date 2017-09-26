package search;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
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
	
	private Document createDocument(File file) throws IOException {
		Document document = new Document();
		
		Field contentField = new TextField(SearchConstant.CONTENTS, new FileReader(file));
//		System.out.println(new FileReader(file));
		Field fileNameField = new TextField(SearchConstant.FILE_NAME, file.getName(), Field.Store.YES);
		//index file path
		Field filePathField = new TextField(SearchConstant.FILE_PATH, file.getCanonicalPath(), Field.Store.YES);

		document.add(contentField);
		document.add(fileNameField);
		document.add(filePathField);

		return document;	
	}
	
	public void indexOneFile(File file) throws IOException {
		System.out.println("Indexing " + file.getCanonicalPath());
		Document doc = createDocument(file);
		indexWriter.addDocument(doc);
	}
	
	public int createIndex(String fileDirPath, FileFilter filter) throws IOException {
//		System.out.println(fileDirPath);
		File f = new File(fileDirPath);
		
		File[] files = f.listFiles();
//		System.out.println(files.toString());
		
		for (File file : files) {
			if (!file.isDirectory() && file.exists() && !file.isHidden() && file.canRead() && filter.accept(file)) {
				indexOneFile(file);
			}
		}
		return indexWriter.numDocs();
	}
	
	public void close() throws IOException {
		indexWriter.close();
	}
}
