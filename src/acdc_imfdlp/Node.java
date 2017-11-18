package acdc_imfdlp;

/**
 * Imports gestion de fichiers, filtrage des fichiers
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.filefilter.*;
/**
 * Imports construction de l'arborescence
 */
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

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
    private CacheFile cacheFile;
    private boolean searchDoublons;
    
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
     * Appel de la méthode de parcours de l'arborescence au démarrage du Thread
     */
    @Override
    public void run() {
        
        try {
            createNode(root, filePath);

        } catch (IOException ex) {
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Création des noeuds à partir de la liste des fichiers et dossiers depuis le répertoire racine défini
     * @param node Modèle d'arbre utilisé pour la modélisation de l'arborescence
     * @param fileRoot Répertoire racine
     */
    private void createNode(DefaultMutableTreeNode node, File fileRoot) throws FileNotFoundException, IOException {
        
        File[] files = fileRoot.listFiles();
        //File[] files = filter(fileRoot);
        
        
        cacheFile = new CacheFile();
        cacheFile.formatCacheFileName(fileRoot.getAbsolutePath());
        cacheFiles(files);
        
        if (files == null) {
            return;
        }

        for (File file : files)
        {
            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new FileNode(file));
            node.add(childNode);
            
            if (file.isDirectory()) {
                createNode(childNode, file);
            }
            else if (file.isFile() && searchDoublons) {
                /*if(!md5Table.containsKey(md5))
                {
                    listFile = new ArrayList();
                    listFile.add(file);
                    md5Table.put(md5, listFile);
                }
                else {
                    md5Table.get(md5).add(file);
                }*/
            }
        }
    }

    /**
     * 
     * @param files
     * @throws IOException 
     */
    protected void cacheFiles(File[] files) throws IOException {
        
        for (File file : files) {
            if (file.isFile()) {
                cacheFile.cache(file);
            }
        }
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public HashMap<String, ArrayList<File>> doublons() {
        
        ArrayList<ArrayList<File>> listFile = new ArrayList();
        
        return md5Table;
    }
    
    /**
     * 
     * @return 
     */
    public ArrayList<ArrayList<File>> setDoublons() {
        
        return null;
    }

    /**
     * Retourne le TreeModel à partir duquel sera créé l'arborescence dans l'IHM
     * @return Modèle d'arbre pour une utilisation avec JTree
     */
    @Override
    public TreeModel treeModel() {
        
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
     * Retourne la taille d'un fichier ou d'un dossier
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
     * Initialisation du répertoire racine
     * @param path Chemin d'accès
     */
    public void setFilePath(String path) {
        
        this.filePath = new File(path);
    }
    
    /**
     * Initialisation du modèle d'arbre de l'arborescence (DefaultMutableTreeNode)
     */
    public void setRoot() {
        
        root = new DefaultMutableTreeNode(filePath);
    }
    
    /**
     * Retourne le modèle d'arbre de l'arborescence
     * @return Modèle d'arbre par défaut
     */
    public DefaultMutableTreeNode getRoot() {
        
        return this.root;
    }

    /**
     * Méthode de filtrage des fichiers des l'arborescence
     * @param fileRoot Répertoire racine à partir duquel lister les fichiers
     * @return 
     */
    @Override
    public File[] filter(File fileRoot) {
        
        //return fileRoot.listFiles((FileFilter) FileFilterUtils.or(filters));
        return fileRoot.listFiles();
    }
    
    /**
     * Méthode initialisant les différents filtres à appliquer aux fichiers et dossiers (depuis l'IHM)
     * @param filters Tableau du/des filtre(s) à appliquer
     */
    public void setFilters(IOFileFilter[] filters) {
        
        this.filters = filters;
    }

    /**
     * 
     * @param searchDoublons 
     */
    public void setSearchDoublons(boolean searchDoublons) {
        
        this.searchDoublons = searchDoublons;
    }
}
