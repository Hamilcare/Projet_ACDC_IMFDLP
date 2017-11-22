package acdc_imfdlp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import org.apache.commons.io.filefilter.*;
import javax.swing.*;
import javax.swing.tree.TreeModel;
/**
 *
 * @author Cédric GARCIA
 */
public class Main extends JFrame implements Runnable {

    /**
     * Variables membres
     */
    private TreeModel treeModel;
    private JTree tree;
    private Thread explorer;
    private Node node;

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
        
        node = new Node();
        node.setFilePath("D:\\FIL\\A1");
        node.setRoot();
        
        treeModel = node.treeModel();
        tree = new JTree(treeModel);
        tree.setShowsRootHandles(true);
        JScrollPane scrollPane = new JScrollPane(tree);
        
        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                tree.removeAll();
                CacheFilter cacheFilter = new CacheFilter();
                node.setFilters(new IOFileFilter[] {cacheFilter, FileFilterUtils.directoryFileFilter() });
                explorer = new Thread(node);
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
    }
    
    /**
     * Main
     * 
     * @param args 
     */
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Main());
    }
    
    /**
     * Classe filtre d'exclusion des fichiers de cache
     */
    public class CacheFilter implements IOFileFilter {

        String cache = ".cache";
        @Override
        public boolean accept(File file) {
            
            return !file.getName().contains(cache);
        }

        @Override
        public boolean accept(File dir, String name) {
            throw new UnsupportedOperationException();
        }
    }
}


