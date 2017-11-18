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
     * Constructeur
     * @param file Fichier ou dossier
     */
    public FileNode(File file) {
        
        this.file = file;
    }

    /**
     * Amélioration de l'écriture du nom des fichiers et dossiers
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
