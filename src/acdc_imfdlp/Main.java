package acdc_imfdlp;

/**
 * Imports ActionListener
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Import gestion de fichiers
 */
import org.apache.commons.io.filefilter.*;
import org.apache.commons.io.IOCase;
/**
 * Imports graphiques + création de l'arborescence
 */
import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
/**
 *
 * @author Cédric GARCIA
 */
public class Main extends JFrame implements Runnable {

    /**
     * Variables membres
     */
    private DefaultTreeModel treeModel;
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
        node.setFilePath("D:\\BTS SN\\DUGAST");
        node.setRoot();
        node.setFilters(new IOFileFilter[] {FileFilterUtils.suffixFileFilter("", IOCase.INSENSITIVE), FileFilterUtils.directoryFileFilter()});
        
        treeModel = node.treeModel();
        tree = new JTree(treeModel);
        tree.setShowsRootHandles(true);
        JScrollPane scrollPane = new JScrollPane(tree);
        
        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                //tree.removeAll();
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
     * @param args 
     */
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Main());
    }
}
