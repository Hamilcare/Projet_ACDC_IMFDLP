package acdc_imfdlp;

/**
 * Imports gestion de fichiers
 */
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Import création de l'arborescence
 */
import javax.swing.tree.TreeModel;

/**
 * Interface de l'API
 * @author Cédric GARCIA
 */
public interface INode {
    
    public HashMap<String, ArrayList<File>> doublons();
    
    public TreeModel treeModel();

    public String filename(File file);

    public long weight(File file);
    
    public String absolutePath(File file);
    
    public File[] filter(File fileRoot);
}
