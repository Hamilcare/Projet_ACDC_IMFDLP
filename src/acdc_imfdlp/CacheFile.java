package acdc_imfdlp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * Classe de cache des fichiers
 * 
 * @author Cédric GARCIA
 */
public class CacheFile {

	/**
	 * Attributs
	 */
	private final Node node;
	private String cacheFileName;
	private File currentFile;
	private FileWriter cacheWriter;
	private BufferedWriter bufferedWriter;

	/**
	 * Constructeur par défaut
	 */
	public CacheFile() {

		this.node = new Node();
	}

	/**
	 * Formatage du nom du fichier de cache à partir du nom du dossier courant
	 * 
	 * @param absolutePath
	 *            Chemin absolu du dossier courant
	 */
	protected void formatCacheFileName(String absolutePath) {

		cacheFileName = absolutePath;
		cacheFileName = cacheFileName.replaceAll("\\\\", "_");
		cacheFileName = cacheFileName.replaceAll(":", "");
		//cacheFileName = "cache//" + cacheFileName + ".cache";
		cacheFileName = cacheFileName + ".cache";
	}

	/**
	 * Formatage par défaut des lignes à inscrire dans les fichiers de cache
	 * 
	 * @param hash
	 *            Hash du fichier courant
	 * @return Ligne formatée
	 */
	protected String formatCacheLine(String hash) {

		return "file=[" + currentFile.getName() + "];hash=[" + hash + "];timestamp=[" + currentFile.lastModified()
				+ "];*" + System.getProperty("line.separator");
	}

	/**
	 * Première écriture du fichier de cache pour le dossier courant s'il n'existe
	 * pas
	 * 
	 * @param file
	 *            Fichier courant à inscrire dans le cache
	 * @param md5
	 *            Hash du fichier courant
	 * @throws IOException
	 */
	protected void writeCacheFile(File file, String md5) throws IOException {

		currentFile = file;
		cacheWriter = new FileWriter(cacheFileName, true);
		bufferedWriter = new BufferedWriter(cacheWriter);
		bufferedWriter.write(formatCacheLine(md5));
		bufferedWriter.close();
		cacheWriter.close();
	}

	/**
	 * Lecture et écriture dans le fichier de cache s'il existe déjà. Gère les cas
	 * où le fichier a été modifié, supprimé, ou nouvellement ajouté au dossier
	 * courant
	 * 
	 * @param file
	 *            Fichir courant
	 * @throws IOException
	 */
	protected void readCacheFile(File file) throws IOException {

		currentFile = file;
		boolean isCached = true;
		StringBuilder inputBuffer = new StringBuilder();

		File cache = new File(getCacheFileName());
		List<String> lines = FileUtils.readLines(cache);
		for (String line : lines) {

			inputBuffer.append(line);
			inputBuffer.append('\n');
			String inputStr = inputBuffer.toString();

			if (extractNameFromCache(line).equals(currentFile.getName())
					&& (extractLastModifiedFromCache(line) == currentFile.lastModified())) {
				isCached = true;
				break;
			}
			if (!extractNameFromCache(line).equals(currentFile.getName())) {
				isCached = false;
			}
			if (extractNameFromCache(line).equals(currentFile.getName())
					&& (extractLastModifiedFromCache(line) != currentFile.lastModified())) {
				//FileUtils.write(cache, formatCacheLine(node.hash(currentFile)));
				inputStr = inputStr.replaceAll(line, formatCacheLine(node.hash(currentFile)));
				try (FileOutputStream out = new FileOutputStream(cache)) {
					out.write(inputStr.getBytes());
				}
				break;
			}
		}
		if (!isCached) {
			writeCacheFile(currentFile, node.hash(currentFile));
		}
	}

	/**
	 * Extraction du Nom du fichier depuis le cache (s'il est caché)
	 * 
	 * @param currentLine
	 *            Ligne courante du fichier de cache
	 * @return Nom du fichier
	 */
	private String extractNameFromCache(String currentLine) {

		return currentLine.substring(currentLine.indexOf("file=[") + 6, currentLine.indexOf("];hash"));
	}

	/**
	 * Extraction du Hash du fichier depuis le cache (s'il est caché)
	 * 
	 * @param currentLine
	 *            Ligne courante du fichier de cache
	 * @return Valeur du Hash
	 */
	private String extractHashFromCache(String currentLine) {

		return currentLine.substring(currentLine.indexOf("hash=[") + 6, currentLine.indexOf("];timestamp"));
	}

	/**
	 * Extraction du TimeStamp du fichier courant depuis le cache (s'il est caché)
	 * 
	 * @param currentLine
	 *            Ligne courante du fichier de cache
	 * @return Valeur du TimeStamp
	 */
	private long extractLastModifiedFromCache(String currentLine) {

		return Long
				.parseLong(currentLine.substring(currentLine.indexOf(";timestamp=[") + 12, currentLine.indexOf("];*")));
	}

	/**
	 * Nom du fichier de cache pour le dossier courant
	 * 
	 * @return Fichier de cache dans lequel écrire
	 */
	protected String getCacheFileName() {

		return cacheFileName;
	}
}
