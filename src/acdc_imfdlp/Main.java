package acdc_imfdlp;

/**
 * Imports ActionListener
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Import gestion de fichiers
 */
import java.io.File;
/**
 * Imports graphiques + création de l'arborescence
 */
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Cédric GARCIA
 */
public class Main extends JFrame implements Runnable {

    /**
     * Variables membres
     */
    private DefaultMutableTreeNode root;
    private DefaultTreeModel treeModel;
    private File rootPath;
    private JTree tree;
    private Thread explorer;

    /**
     * Constructeur par défaut
     */
    public Main() {
        
        super("Il me faut de la place !");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * Initialisation du Thread de la fenêtre principale
     */
    @Override
    public void run() {
        
        setRootPath("D:\\Users\\Cédric\\Downloads");
        root = new DefaultMutableTreeNode(rootPath);
        treeModel = new DefaultTreeModel(root);

        tree = new JTree(treeModel);
        tree.setShowsRootHandles(true);
        JScrollPane scrollPane = new JScrollPane(tree);
        
        String fileExt = "";
        
        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                explorer = new Thread(new Node(root, rootPath, fileExt));
                explorer.start();
            }
        });

        /**
         * Placement des deux éléments de la fenêtre de test
         */
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSearch)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
                .addContainerGap())
        );
        
        pack();
        this.setVisible(true);
        
        /*while(this.explorer.isAlive()) {
            tree.setEnabled(false);
        }*/
    }
    
    /**
     * Main
     * @param args 
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main());
    }

    /**
     * Initialisation du répertoire source
     * @param path Chelin d'accès
     */
    public void setRootPath(String path) {
        
        this.rootPath = new File(path);
    }
}
