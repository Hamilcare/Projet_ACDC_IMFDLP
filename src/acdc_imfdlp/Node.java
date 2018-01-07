package acdc_imfdlp;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

/**
 * Classe de création de l'arborescence et d'appel des méthodes de mise en cache
 * des fichiers
 * 
 * @author Cédric GARCIA
 */
public class Node implements INode, Runnable {

    /**
     * Attributs
     */
    private DefaultMutableTreeNode             root;
    private File                               filePath;
    protected HashMap<String, ArrayList<File>> md5Table = new HashMap<>();;

    private IOFileFilter[]                     filters;
    private CacheFile                          cacheFile;

    /**
     * Constructeur par défaut
     */
    public Node() {

    }

    /**
     * Constructeur
     * 
     * @param root
     *            Modèle d'arbre à initialiser
     * @param filePath
     *            Répertoire racine à initialiser
     */
    public Node(DefaultMutableTreeNode root, File filePath) {

        this.root = root;
        this.filePath = filePath;
        this.md5Table = new HashMap<>();
    }

    /**
     * Démarrage du Thread
     */
    @Override
    public void run() {

        try {
            createNode(root, filePath);
        }
        catch (IOException e) {

        }
    }

    /**
     * Création de l'arborescence à partir de la liste des fichiers et dossiers
     * 
     * @param node
     *            Modèle d'arbre utilisé pour modéliser l'arborescence
     * @param fileRoot
     *            Répertoire racine
     */
    private void createNode(DefaultMutableTreeNode node, File fileRoot) throws FileNotFoundException, IOException {

        File[] files = filter(fileRoot);

        if (files == null) {
            return;
        }

        for (File file : files) {
            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new FileNode(file));
            node.add(childNode);

            if (file.isDirectory()) {
                createNode(childNode, file);
            }
        }
    }

    /**
     * Retourne le Hash md5 d'un fichier
     * 
     * @param file
     *            Fichier à hasher
     * @return Hash md5 du fichier
     * @throws IOException
     */
    @Override
    public String hash(File file) throws IOException {

        try (FileInputStream fStream = new FileInputStream(file)) {
            return DigestUtils.md5Hex(fStream);
        }
    }

    /**
     * Recherche, cache, hash et création de la HashMap des doublons à partir du
     * répertoire souhaité
     * 
     * @param fileRoot
     *            Répertoire racine
     * @return HashMap des listes de doublons associés à une clé md
     * @throws java.io.IOException
     */
    @Override
    public HashMap<String, ArrayList<File>> doublons(File fileRoot) throws IOException {

        File[] files = filter(fileRoot);

        cacheFile = new CacheFile();
        cacheFile.formatCacheFileName(fileRoot.getAbsolutePath());
        File cache = new File(cacheFile.getCacheFileName());
        String md5 = "";
        ArrayList listFile;

        if (files == null) {
        }

        for (File file : files) {
            if (file.isDirectory() && file.list().length > 0) {
                doublons(file);
            }
            else if (file.isFile()) {
                md5 = hash(file);
                if (!cache.exists()) {
                    cacheFile.writeCacheFile(file, md5);
                }
                else {
                    cacheFile.readCacheFile(file);
                }

                if (!md5Table.containsKey(md5)) {
                    listFile = new ArrayList();
                    listFile.add(file);
                    md5Table.put(md5, listFile);
                }
                else {
                    md5Table.get(md5).add(file);
                }
            }
        }
        return md5Table;
    }

    /**
     * 
     * @return la table des hash
     */
    public HashMap<String, ArrayList<File>> getMd5Table() {

        return md5Table;
    }

    /**
     * Retourne le TreeModel à partir duquel sera créé l'arborescence dans l'IHM
     * 
     * @return TreeModel utilisable pour la génération du JTree
     */
    @Override
    public TreeModel treeModel() {

        return new DefaultTreeModel(this.root);
    }

    /**
     * Initialisation du répertoire racine
     * 
     * @param path
     *            Chemin d'accès
     */
    protected void setFilePath(String path) {

        this.filePath = new File(path);
    }

    /**
     * 
     * @return le file associé au node
     */
    public File getFilePath() {

        return filePath;
    }

    /**
     * Initialisation du modèle d'arbre de l'arborescence
     * (DefaultMutableTreeNode)
     */
    protected void setRoot() {

        root = new DefaultMutableTreeNode(filePath);
    }

    /**
     * Retourne le modèle d'arbre de l'arborescence
     * 
     * @return Modèle d'arbre de l'arborescence
     */
    protected DefaultMutableTreeNode getRoot() {

        return this.root;
    }

    /**
     * Méthode de filtrage des fichiers de l'arborescence
     * 
     * @param fileRoot
     *            Répertoire racine à partir duquel lister les fichiers
     * @return Tableau des fichiers passés à travers le/les filtre(s)
     */
    @Override
    public File[] filter(File fileRoot) {

        return fileRoot.listFiles((FileFilter) FileFilterUtils.or(filters));
    }

    /**
     * Méthode initialisant les différents filtres à appliquer aux fichiers et
     * dossiers (depuis l'IHM)
     * 
     * @param filters
     *            Tableau du/des filtre(s) à appliquer
     */
    protected void setFilters(IOFileFilter[] filters) {

        this.filters = filters;
    }

    @Override
    public String toString() {

        String tree = "";
        File file = new File("");
        Enumeration en = root.depthFirstEnumeration();

        while (en.hasMoreElements()) {
            DefaultMutableTreeNode n = (DefaultMutableTreeNode) en.nextElement();

            try {
                // file = ((FileNode) n.getUserObject()).getFile();
            }
            catch (Exception e) {

            }

            if (n != null) {
                tree += file.getAbsolutePath() + "\n";
            }
        }
        return tree;
    }
}