package acdc_imfdlp;

/**
 * Import gestion de fichiers
 */
import java.io.File;

/**
 *
 * @author Cédric GARCIA
 */
public class FileNode {

	/**
	 * Variable membre
	 */
	private final File file;
	/**
	 * taille du fichier porté
	 */
	private long taille;

	/**
	 * 
	 * @return taille
	 */
	public long getTaille() {
		return taille;
	}

	/**
	 * 
	 * calcul et positionne taille
	 */
	public void setTaille() {
		this.taille = computeFileSize(this.file, 0);
	}

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

	/**
	 * Constructeur
	 * 
	 * @param file
	 *            Fichier ou dossier
	 */
	public FileNode(File file) {

		this.file = file;
	}

	/**
	 * Amélioration de l'écriture du nom des fichiers et dossiers
	 * 
	 * @return
	 */
	@Override
	public String toString() {

		String name = file.getName();
		if (name.equals("")) {
			return file.getAbsolutePath();
		} else {
			return name;
		}
	}
}
