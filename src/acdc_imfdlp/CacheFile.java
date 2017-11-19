package acdc_imfdlp;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Cédric GARCIA
 */
public class CacheFile {

    private String cacheFileName;
    protected File cacheFile;
    private File currentFile;
    private FileWriter cacheWriter;
    private BufferedWriter bufferedWriter;

    /**
     * 
     */
    public CacheFile() {

    }
    
    /**
     * 
     * @param absolutePath 
     */
    protected void formatCacheFileName(String absolutePath) {
        
        cacheFileName = absolutePath;
        cacheFileName = cacheFileName.replaceAll("\\\\", "_");
        cacheFileName = cacheFileName.replaceAll(":", "");
        cacheFileName = "D:\\Cache_ACDC\\" + cacheFileName + ".cache";
    }
    
    /**
     * 
     * @param hash
     * @return 
     */
    protected String formatCacheLine(String hash) {
        
        return "file=[" + currentFile.getAbsolutePath() + "];hash=[" + hash + "];timestamp=[" + currentFile.lastModified() + "];*"
                + System.getProperty("line.separator");
    }

    /**
     * 
     * @throws IOException 
     */
    protected void writeCacheFile(File file) throws IOException {

        currentFile = file;
        cacheWriter = new FileWriter(cacheFileName, true);
        bufferedWriter = new BufferedWriter(cacheWriter);
        bufferedWriter.write(formatCacheLine(hash()));
        bufferedWriter.close();
        cacheWriter.close();
    }

    /**
     * 
     * @throws IOException 
     */
    protected void readCacheFile(File file) throws IOException {

        currentFile = file;
        boolean isCached = true;
        
        File cache = new File(getCacheFileName());
        List<String> lines = FileUtils.readLines(cache);
        for (String line : lines) {
            if (extractNameFromCache(line).equals(currentFile.getAbsolutePath())
                    && (extractLastModifiedFromCache(line) == currentFile.lastModified())) {
                isCached = true;
                break;
            }
            if (!extractNameFromCache(line).equals(currentFile.getAbsolutePath())) {
                isCached = false;
            }
            if (extractNameFromCache(line).equals(currentFile.getAbsolutePath())
                    && (extractLastModifiedFromCache(line) != currentFile.lastModified())) {
                FileUtils.write(cache, formatCacheLine(hash()));
                isCached = true;
                break;
            }
            if (!(new File(extractNameFromCache(line)).exists())) {
                FileUtils.write(cache, "");
                break;
            }
        }
        if (!isCached) {
            writeCacheFile(currentFile);
        }
    }
    
    /**
     * 
     * @param currentLine
     * @return 
     */
    private long extractLastModifiedFromCache(String currentLine) {
        
        return Long.parseLong(currentLine.substring(currentLine.indexOf(";timestamp=[") + 12, currentLine.indexOf("];*")));
    }
    
    /**
     * 
     * @param currentLine
     * @return 
     */
    private String extractNameFromCache(String currentLine) {
        
        return currentLine.substring(currentLine.indexOf("file=[") + 6, currentLine.indexOf("];hash"));
    }
    
    /**
     * Retourne le hash md5 d'un fichier
     * @return Hash md5 du fichier
     * @throws IOException Erreur si l'on a par ex. pas les droits sur le fichier
     */
    public String hash() throws IOException {
        
        try (FileInputStream fStream = new FileInputStream(currentFile)) {
            return DigestUtils.md5Hex(fStream);
        }
    }

    /**
     * 
     * @return 
     */
    public String getCacheFileName() {
        
        return cacheFileName;
    }
}
