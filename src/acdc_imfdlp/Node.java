package acdc_imfdlp;

/**
 * Imports gestion de fichiers
 */
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.io.filefilter.*;
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
    private File filePath;
    private HashMap<String, ArrayList<File>> md5Table;
    private IOFileFilter[] filters;
    
    /**
     * Constructeur par défaut
     */
    public Node() {
        
        this.md5Table = new HashMap<>();
    }
    
    /**
     * Constructeur
     * @param root Modèle d'arbre à initialiser
     * @param filePath Répertoire source à initialiser
     */
    public Node(DefaultMutableTreeNode root, File filePath) {
        
        this.root = root;
        this.filePath = filePath;
        this.md5Table = new HashMap<>();
    }
    
    /**
     * Méthode appelée au démarrage du Thread
     */
    @Override
    public void run() {
        
        createNode(root, filePath);
    }
    
    /**
     * Créé les noeuds à partir de la liste des fihciers et dossiers du répertoire source
     * @param node Modèle d'arbre
     * @param fileRoot Répertoire source
     */
    private void createNode(DefaultMutableTreeNode node, File fileRoot) {
        
        String md5;
        File[] files = filter(fileRoot);
        ArrayList<File> listFile = new ArrayList();
        
        if (files == null) return;

        for (File file : files)
        {
            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new FileNode(file));
            node.add(childNode);
            if (file.isDirectory() && file.length() != 0) {
                createNode(childNode, file);
            }
            else if (file.isFile()) {
                
                //long start = System.nanoTime();
                try {
                    md5 = hash(file);
                    if(!md5Table.containsKey(md5))
                    {
                        listFile = new ArrayList();
                        listFile.add(file);
                        md5Table.put(md5, listFile);
                    }
                    else {
                        md5Table.get(md5).add(file);
                    }
                } catch (IOException e) {
                    
                }
                //long end = System.nanoTime();
                //System.out.println((end - start));
            }
        }
        //System.out.println(md5Table + "\n");
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
        
        return new DefaultTreeModel(this.root);
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
     * Initialisation du répertoire source
     * @param path Chemin d'accès
     */
    public void setFilePath(String path) {
        
        this.filePath = new File(path);
    }
    
    /**
     * Initialisation du DefaultMutableTreeNode
     */
    public void setRoot() {
        
        root = new DefaultMutableTreeNode(filePath);
    }
    
    /**
     * 
     * @return 
     */
    public DefaultMutableTreeNode getRoot() {
        
        return this.root;
    }

    /**
     * Méthode filtrant les fichiers par type
     * @param fileRoot Répertoire racine à partir duquel lister les fichiers
     * @return 
     */
    @Override
    public File[] filter(File fileRoot) {
        
        return fileRoot.listFiles((FileFilter) FileFilterUtils.or(filters));
    }
    
    /**
     * Méthode initialisant les différents filtres à appliquer aux fichiers et dossiers (depuis l'IHM)
     * @param filters Filtres à appliquer
     */
    public void setFilters(IOFileFilter[] filters) {
        
        this.filters = filters;
    }
}

