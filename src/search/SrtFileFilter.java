package search;

import java.io.File;
import java.io.FileFilter;

public class SrtFileFilter implements FileFilter {

	@Override
	public boolean accept(File pathname) {
		return pathname.getName().endsWith(".srt");
	}
	
}
