package acdc_imfdlp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import controller.SupprListener;
import controller.TableSelectionListener;

/**
 * 
 * @author Valentin La vue permettant de consulter les doublons.
 */
public class ContainerDoublonsView extends Container {

    private static final long        serialVersionUID = 7553782934445993773L;

    MainFrame                        mainFrame;

    final String[]                   headers          = { "FilenName", "AbsolutePath", "Weight" };

    Object[][]                       data;

    HashMap<String, ArrayList<File>> doublons;

    JTable                           table;

    Node                             lastNode;

    ArrayList<String>                selectedFile;

    ListSelectionModel               cellSelectionModel;

    public ContainerDoublonsView(MainFrame mf) {
        mainFrame = mf;
        lastNode = mf.getNode();
        //this.addKeyListener(new SupprListener());
        //table.addK
    }

    /**
     * 
     * @return la JTable associée
     */
    public JTable getTable() {

        return table;
    }

    /**
     * 
     * @return la liste des path des fichiers selectionnes
     */
    public ArrayList<String> getSelectedFile() {

        return selectedFile;
    }

    /**
     * 
     * @param selectedFile
     *            positionne la liste des path des fichiers selectionnes
     */
    public void setSelectedFile(ArrayList<String> selectedFile) {

        this.selectedFile = selectedFile;
    }

    /**
     * Lance le hash et set le tableau Data
     * 
     * @param b
     */
    public void loadDoublonsView(boolean forceReload) {

        if (data == null || mainFrame.getNode() != lastNode && !forceReload) {
            System.out.println("Loading doublons");
            this.computeData();
            //table = new JTable(data, headers);
            //Hack pour rendre la table non editable
            table = new JTable(data, headers) {

                private static final long serialVersionUID = 1L;

                public boolean isCellEditable(int row, int column) {

                    return false;
                };
            };

            //Toute la ligne est selectionnée
            table.setRowSelectionAllowed(true);

            table.addKeyListener(new SupprListener(this));

            cellSelectionModel = table.getSelectionModel();
            cellSelectionModel.addListSelectionListener(new TableSelectionListener(this));
            this.setLayout(new BorderLayout());
            this.add(new JScrollPane(table), BorderLayout.CENTER);
        }
    }

    /**
     * Remplie le tableau data qui alimentera la JTable présentant les doublons
     */
    public void computeData() {

        this.computeDoublons();
        data = new Object[this.countRows()][3];
        int rowIndex = 0;
        for (Entry<String, ArrayList<File>> entry : doublons.entrySet()) {
            for (File f : entry.getValue()) {
                data[rowIndex][0] = f.getName();
                data[rowIndex][1] = f.getAbsolutePath();
                data[rowIndex][2] = f.length();
                rowIndex++;
            }
            data[rowIndex][0] = "";
            data[rowIndex][1] = "";
            data[rowIndex][2] = "";
            rowIndex++;
        }

    }

    /**
     * 
     * @return le nombre de ligne que contiendra le tableau
     */
    private int countRows() {

        int nbRows = 0;
        for (Entry<String, ArrayList<File>> entry : doublons.entrySet()) {
            nbRows = nbRows + entry.getValue().size();
        }

        nbRows = nbRows + doublons.size() + 1;

        return nbRows;
    }

    /**
     * 
     * @return Une Map contenant uniquement les fichiers en double
     */
    public void computeDoublons() {

        HashMap<String, ArrayList<File>> clean = new HashMap<>();
        try {
            //Il faut écraser la map précédente ou on aura des lignes en double
            mainFrame.getNode().md5Table = new HashMap<>();
            mainFrame.getNode().doublons(mainFrame.getNode().getFilePath());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        for (Entry<String, ArrayList<File>> entry : mainFrame.getNode().getMd5Table().entrySet()) {
            if (entry.getValue().size() > 1) {
                clean.put(entry.getKey(), entry.getValue());
            }
        }

        doublons = clean;

    }

    public void restartVueDoublons() {

        mainFrame.refreshData();

    }

}
