package util;

import java.io.File;
import java.nio.file.Files;

/**
 * 
 * Utility class {@link FileUtil} which is used to remove directory and sub directories. 
 * 
 * @author Dora
 *
 */
public class FileUtil {

	/**
	 * This method is used to remove directory. 
	 * 
	 * @param directoryPath
	 * @return
	 */
	public static boolean removeDirectory(String directoryPath) {
		File file  = new File(directoryPath);
		return deleteDir(file);
	}
	
	/**
	 * This method is used to delete file and its sub directories if exist.
	 * 
	 * @param file
	 * @return true if success otherwise false.
	 */
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
