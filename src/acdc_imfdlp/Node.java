package acdc_imfdlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Cédric GARCIA
 */
public class Node implements INode, Runnable {

    private DefaultMutableTreeNode root;
    private File fileRoot;
    
    public Node(DefaultMutableTreeNode root, File fileRoot) {
        
        this.fileRoot = fileRoot;
        this.root = root;
    }
    
    private void createNode(DefaultMutableTreeNode node, File fileRoot) {
        
        File[] files = fileRoot.listFiles();
        
        if (files == null) return;

        for (File file : files)
        {
            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new FileNode(file));
            node.add(childNode);
            if (file.isDirectory())
            {
                createNode(childNode, file);
            }
        }
    }
    
    /**
     * 
     */
    @Override
    public void run() {
        
        createNode(root, fileRoot);
    }

    /**
     * 
     * @param path
     * @return 
     */
    @Override
    public INode tree(String path) {
        
        return null;
    }

    /**
     * 
     * @param path
     * @param profondeur
     * @return 
     */
    @Override
    public INode tree(String path, int profondeur) {
        
        return null;
    }

    /**
     * 
     * @return 
     */
    @Override
    public File[] doublons() {
        
        return null;
    }

    /**
     * 
     * @return 
     */
    @Override
    public DefaultTreeModel treeModel() {
        
        return null;
    }
    
    /**
     * 
     * @param file
     * @return 
     */
    public String filename(File file) {
        
        return file.getName();
    }

    /**
     * 
     * @param file
     * @return
     * @throws IOException 
     */
    public String hash(File file) throws IOException {
        
        return DigestUtils.md5Hex(new FileInputStream(file));
    }

    /**
     * 
     * @param file
     * @return 
     */
    public long weight(File file) {
        
        return file.length();
    }

    /**
     * 
     * @param file
     * @return 
     */
    public String absolutePath(File file) {
        
        return file.getAbsolutePath();
    }

    /**
     * 
     * @return 
     */
    @Override
    public INode[] child() {
        
        return null;
    }

    /**
     * 
     * @return 
     */
    @Override
    public INode filter() {
        
        return null;
    }

    @Override
    public String filename() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String hash() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long weight() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String absolutePath() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
