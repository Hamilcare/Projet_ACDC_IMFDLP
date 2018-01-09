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

	public File getFile() {
		return file;
	}

	/**
	 * 
	 * @return taille
	 */
	public long getTaille() {
		System.out.println("Appel getTaille sur " + file.getName());
		if (taille == 0L)
			this.setTaille();
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
		System.out.println(currentFile.getName());
		System.out.println(currentFile.isDirectory());
		if (currentFile.isDirectory()) {
			long size = 0;
			for (File f : currentFile.listFiles()) {
				size = size + computeFileSize(f, size);
			}
			return size;
		} else {
			return currentFile.length();
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
