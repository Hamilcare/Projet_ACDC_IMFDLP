package acdc_imfdlp;

import java.io.File;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Cédric GARCIA
 */
public interface INode {
    
    public INode tree(String path);
    
    public INode tree(String path, int profondeur);
    
    public File[] doublons();
    
    public DefaultTreeModel treeModel();

    public String filename();

    public String hash();

    public long weight();
    
    public String absolutePath();
    
    public INode[] child();
    
    public INode filter();
}
