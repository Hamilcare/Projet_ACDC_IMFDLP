package utils;

import java.io.File;

/**
 * fourni une methode afin de calculer le poids réel d'un dossier
 * 
 * @author valentin
 *
 */
public class FileSizeComputer {

	public static long computeFileSize(File currentFile, long currentComputedSize) {
		if (currentFile.isFile()) {
			return currentFile.length();
		} else {
			long size = 0;
			for (File f : currentFile.listFiles()) {
				size = size + computeFileSize(f, size);
			}
			return size;
		}
	}

}
