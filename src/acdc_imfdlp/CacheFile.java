package acdc_imfdlp;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.apache.commons.codec.digest.DigestUtils;

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
    
    protected void cache(File file) throws IOException {
        
        currentFile = file;
        //if (!cacheFile.exists()) {
            writeCacheFile();
        /*} else {
            readCacheFile();
        }*/
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
        
        cacheFile = new File(getCacheFileName());
    }
    
    /**
     * 
     * @param hash
     * @return 
     */
    protected String formatCacheLine(String hash) {
        
        return "file=[" + currentFile.getAbsolutePath() + "];hash=[" + hash + "];timestamp=[" + currentFile.lastModified() + "];*";
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

        /*try (Scanner scanner = new Scanner(new File(getCacheFileName()))) {
            while (scanner.hasNextLine()) {
                
                String line = scanner.nextLine();
                System.out.println(line);
                processLine(line);
                break;
            }
        }*/
        
        try (Stream<String> stream = Files.lines(Paths.get(getCacheFileName()))) {
            stream.forEach(currentLine -> {
                try {
                    processLine(currentLine);
                } catch (IOException e) {
                    
                }
            });
        }
    }
    
    /**
     * 
     * @param currentLine
     * @throws IOException 
     */
    protected void processLine(String currentLine) throws IOException {
        
        String fileNameFromCache = extractNameFromCache(currentLine);
        long lastModifiedFromCache = extractLastModifiedFromCache(currentLine);
        
        System.out.println(fileNameFromCache + lastModifiedFromCache);
        
        if((currentFile.getAbsolutePath().equals(fileNameFromCache)) &&
                !(currentFile.lastModified() == lastModifiedFromCache)) {
            replaceValueFromCache(currentLine, lastModifiedFromCache);
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
