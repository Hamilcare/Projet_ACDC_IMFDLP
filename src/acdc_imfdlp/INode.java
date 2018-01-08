package acdc_imfdlp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.tree.TreeModel;

/**
 * Interface de l'API
 * 
 * @author Cédric GARCIA
 */
public interface INode {

    public String hash(File file) throws IOException;

    public HashMap<String, ArrayList<File>> doublons(File file) throws IOException;

    public TreeModel treeModel();

    public File[] filter(File fileRoot);

    public void filterValentin(File fileRoot);
}
