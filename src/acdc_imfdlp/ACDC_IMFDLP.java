package acdc_imfdlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.HashMap;

import javax.swing.filechooser.FileSystemView;

import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Cédric
 */
public class ACDC_IMFDLP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    {
        FileSystemView fsv = FileSystemView.getFileSystemView();
         
        File[] drives = File.listRoots();
        System.out.println("Drive Letter: " + drives[0]);
        
        //File[] listFiles = drives[0].listFiles();
        File indexableDirectory = new File("D:\\FIL\\A1\\Projet_ACDC\\ACDC_IMFDLP\\md5");
        File[] listFiles = indexableDirectory.listFiles();
        FileInputStream fis = null;
        
        HashMap map = new HashMap();
        
        
        for(int i = 0; i < listFiles.length; i ++)
        {
            System.out.println(listFiles[i].getAbsoluteFile());
            
            fis = new FileInputStream(listFiles[i]);
            String md5String = DigestUtils.md5Hex(fis);
            System.out.println(md5String);
            
            map.put(listFiles[i].getAbsoluteFile(), md5String);
        }
        
        /*if (drives != null && drives.length > 0) {
            for (File aDrive : drives) {
                System.out.println("Drive Letter: " + aDrive);
                System.out.println("\tType: " + fsv.getSystemTypeDescription(aDrive));
                System.out.println("\tTotal space: " + aDrive.getTotalSpace());
                System.out.println("\tFree space: " + aDrive.getFreeSpace());
                System.out.println();
            }
        }*/
    }
}
