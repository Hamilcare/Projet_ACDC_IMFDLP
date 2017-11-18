package acdc_imfdlp;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Cédric GARCIA
 */
public class CacheFile {

    private String cacheFileName;
    private File currentFile;
    private FileWriter cacheWriter;
    private BufferedWriter bufferedWriter;
    private FileReader cacheReader;
    private BufferedReader bufferedReader;

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
        
        return "file=[" + currentFile.getName() + "];hash=[" + hash + "];timestamp=[" + currentFile.lastModified() + "];";
    }
    
    protected void cache(File file) throws IOException {
        
        currentFile = file;
        
        File cache = new File("D:\\Cache_ACDC\\" + getCacheFileName());
        if(!cache.exists())
        {
            writeCacheFile();
        } else {
            readCacheFile();
        }
    }

    /**
     * 
     * @throws IOException 
     */
    protected void writeCacheFile() throws IOException {

        cacheWriter = new FileWriter(cacheFileName, true);
        bufferedWriter = new BufferedWriter(cacheWriter);
        bufferedWriter.write(formatCacheLine(hash()));
        bufferedWriter.newLine();
        bufferedWriter.close();
        cacheWriter.close();
    }

    /**
     * 
     * @throws IOException 
     */
    protected void readCacheFile() throws IOException {

        cacheReader = new FileReader(cacheFileName);
        bufferedReader = new BufferedReader(cacheReader);
        
        String currentLine;
        while ((currentLine = bufferedReader.readLine()) != null) {
            processLine(currentLine);
        }
        
        bufferedReader.close();
        cacheReader.close();
    }
    
    /**
     * 
     * @param currentLine
     * @throws IOException 
     */
    protected void processLine(String currentLine) throws IOException {
        
        String fileNameFromCache = extractCacheValue(currentLine)[0];
        long lastModifiedFromCache = Long.valueOf(extractCacheValue(currentLine)[1]);
        
        if((currentFile.getName().equals(fileNameFromCache)) &&
                !(currentFile.lastModified() == lastModifiedFromCache)) {
            replaceValueFromCache(currentLine, lastModifiedFromCache);
        }
        else if(!currentFile.getName().equals(fileNameFromCache)) {
            writeCacheFile();
        }
        else if(!(currentFile.getName().equals(fileNameFromCache)) &&
                !(currentFile.lastModified() == lastModifiedFromCache)) {
            currentLine = "";
            deleteValueFromCache(currentLine);
        }
    }
    
    /**
     * 
     * @param currentLine
     * @return 
     */
    private String[] extractCacheValue(String currentLine) {
        
        String[] values = new String[2];
        values[0] = currentLine.substring(currentLine.indexOf("file=[") + 1, currentLine.indexOf("];h"));
        values[1] = currentLine.substring(currentLine.indexOf(";timestamp=[") + 1, currentLine.indexOf("]"));
        
        return values;
    }
    
    /**
     * 
     * @param currentLine
     * @throws IOException 
     */
    private void deleteValueFromCache(String currentLine) throws IOException {
        
        cacheWriter = new FileWriter(cacheFileName, true);
        bufferedWriter = new BufferedWriter(cacheWriter);
        bufferedWriter.write(currentLine + System.getProperty("line.separator"));
        bufferedWriter.close();
        cacheWriter.close();
    }
    
    /**
     * 
     * @param currentLine
     * @param lastModifiedFromCache
     * @throws IOException 
     */
    private void replaceValueFromCache(String currentLine, long lastModifiedFromCache) throws IOException {
        
        cacheWriter = new FileWriter(cacheFileName, true);
        bufferedWriter = new BufferedWriter(cacheWriter);
        bufferedWriter.write(currentLine.replaceAll(String.valueOf(lastModifiedFromCache), String.valueOf(currentFile.lastModified())));
        bufferedWriter.close();
        cacheWriter.close();
    }
    
    /**
     * Retourne le hash md5 d'un fichier
     * @return Hash md5 du fichier
     * @throws IOException Erreur si l'on a par ex. pas les droits sur le fichier
     */
    public String hash() throws IOException {
        
        return DigestUtils.md5Hex(new FileInputStream(currentFile));
    }

    /**
     * 
     * @return 
     */
    public String getCacheFileName() {
        
        return cacheFileName;
    }
}
