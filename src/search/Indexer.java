package search;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
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
	
	private Document createDocument(File file) throws IOException {
		Document document = new Document();
		Field contentField = new Field(SearchConstant.CONTENTS, new FileReader(file), null);
		
		Field fileNameField = new Field(SearchConstant.FILE_NAME, file.getName(), null);
		//index file path
		Field filePathField = new Field(SearchConstant.FILE_PATH, file.getCanonicalPath(), null);

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
		System.out.println(f.getAbsolutePath());
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
