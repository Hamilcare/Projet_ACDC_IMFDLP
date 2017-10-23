package acdc_imfdlp;

/**
 * Imports gestion de fichiers
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
/**
 * Imports construction de l'arborescence
 */
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
/**
 * Import génération du hash
 */
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Cédric GARCIA
 */
public class Node implements INode, Runnable {

    /**
     * Variables membres
     */
    private DefaultMutableTreeNode root;
    private File fileRoot;
    private HashMap<File, String> md5Table;
    private String fileExt = "";
    
    /**
     * Constructeur
     * @param root Modèle d'arbre à initialiser
     * @param fileRoot Répertoire source à initialiser
     */
    public Node(DefaultMutableTreeNode root, File fileRoot, String fileExt) {
        
        this.root = root;
        this.fileRoot = fileRoot;
        this.fileExt = fileExt;
        
        md5Table = new HashMap<>();
    }
    
    /**
     * Méthode appelée au démarrage du Thread
     */
    @Override
    public void run() {
        
        createNode(root, fileRoot);
    }
    
    /**
     * Créé les noeuds à partir de la liste des fihciers et dossiers du répertoire source
     * @param node Modèle d'arbre
     * @param fileRoot Répertoire source
     */
    private void createNode(DefaultMutableTreeNode node, File fileRoot) {
        
        File[] files;
        
        if (fileExt.equals("")) {
            files = fileRoot.listFiles();
        } else {
            files = filter(fileRoot, fileExt);
        }
        
        if (files == null) return;

        for (File file : files)
        {
            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new FileNode(file));
            node.add(childNode);
            if (file.isDirectory()) {
                createNode(childNode, file);
            }
            if (file.isFile()) {
                try {
                    md5Table.put(file, hash(file));
                    //System.out.println(file.getAbsolutePath() + " " + hash(file));
                    System.out.println(md5Table);
                } catch (IOException e) {
                    //System.out.println(e);
                }
            }
        }
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
     * Retourne le nom d'un fichier ou d'un dossier
     * @param file Fichier ou dossier
     * @return  Nom
     */
    @Override
    public String filename(File file) {
        
        return file.getName();
    }

    /**
     * Retourne le hash md5 d'un fichier
     * @param file Fichier
     * @return Hash md5 du fichier
     * @throws IOException Erreur si l'on a par ex. pas les droits sur le fichier
     */
    @Override
    public String hash(File file) throws IOException {
        
        return DigestUtils.md5Hex(new FileInputStream(file));
    }

    /**
     * Retourne le taille d'un fichier ou d'un dossier
     * @param file Fichier ou dossier
     * @return Taille
     */
    @Override
    public long weight(File file) {
        
        return file.length();
    }

    /**
     * Retourne le chemin d'accès complet d'un fichier ou d'un dossuier
     * @param file Fichier ou dossier
     * @return Chemin d'accès complet
     */
    @Override
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
     * Méthode filtrant les fichiers par type
     * @param fileRoot Répertoire racine à partir duquel lister les fichiers
     * @param ext Exitension du fichier
     * @return 
     */
    @Override
    public File[] filter(File fileRoot, String ext) {
        
        return fileRoot.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File fileRoot, String ext) {
                return ext.toLowerCase().endsWith(ext);
            }
        });
    }
}

