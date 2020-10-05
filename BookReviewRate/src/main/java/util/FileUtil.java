package util;

import java.io.File;
import java.nio.file.Files;

public class FileUtil {

	public static boolean removeDirectory(String directoryPath) {
		File file  = new File(directoryPath);
		return deleteDir(file);
	}
	
	private static boolean deleteDir(File file) {
		File[] contents = file.listFiles();
		if (contents != null) {
			for (File f : contents) {
				if (! Files.isSymbolicLink(f.toPath())) {
					deleteDir(f);
				}
			}
		}
		return file.delete();
	}
	
}
