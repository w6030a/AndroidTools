package utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FileUtils {
	public static ArrayList<String> resolveRegexFilePath(String directory, ArrayList<String> filePaths) {
		Set<String> tempFilePaths = new HashSet<String>();
		
		for(String filePath : filePaths) {
			File temp = new File(filePath);
			String folderPath = filePath.substring(0, temp.getAbsolutePath().lastIndexOf(File.separator)-1);
			String filePathRegex = filePath.substring(temp.getAbsolutePath().lastIndexOf(File.separator)-1, filePath.length());
			
			File fioJobFolder = new File(directory);
			FilenameFilter filter = createFileNameFilter(filePathRegex);
			ArrayList<String> fileList = new ArrayList<String> (Arrays.asList(fioJobFolder.list(filter)));
			for(int j = 0; j < fileList.size(); j++) {
				tempFilePaths.add(String.format("%s%s", folderPath, fileList.get(j)));
				System.out.println(String.format("Job File %d: %$s", j+1, fileList.get(j)));
			}
		}
		
		return new ArrayList<String>(tempFilePaths);
	}

	private static FilenameFilter createFileNameFilter(final String filePathRegex) {
		FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.matches(filePathRegex);
			}
		};
		return filter;
	}
}
