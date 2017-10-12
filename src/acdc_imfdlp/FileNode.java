package acdc_imfdlp;

import java.io.File;

/**
 *
 * @author Cédric GARCIA
 */
public class FileNode {

    private File file;

    public FileNode(File file) {
        
        this.file = file;
    }

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