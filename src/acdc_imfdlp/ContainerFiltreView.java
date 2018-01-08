package acdc_imfdlp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.io.File;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.commons.io.filefilter.IOFileFilter;

import controller.ButtonLancerRechercheListener;
import utils.TextPrompt;

public class ContainerFiltreView extends Container {

    MainFrame      mainFrame;
    JTextField     inputFilter;
    File[]         fichiersFiltres;
    JTable         table;
    final String[] headers = { "FilenName", "AbsolutePath", "Weight", "Derniere Modif" };
    Object[][]     data;

    public ContainerFiltreView(MainFrame mf) {
        mainFrame = mf;
        this.setLayout(new BorderLayout());

    }

    public JTextField getInputFilter() {

        return inputFilter;
    }

    /**
     * Construit le bandeau supérieur de la vue Filtre
     */
    public void bandeauFiltre() {

        Container bandeauFiltre = new Container();
        bandeauFiltre.setLayout(new FlowLayout());

        bandeauFiltre.add(new JLabel("Saisir les extensions de fichiers : "));

        inputFilter = new JTextField(40);
        TextPrompt placeHolder = new TextPrompt("png pdf txt", inputFilter, TextPrompt.Show.ALWAYS);
        placeHolder.changeAlpha(0.5f);
        bandeauFiltre.add(inputFilter);

        JButton validerFiltre = new JButton("Lancer la recherche");
        validerFiltre.addActionListener(new ButtonLancerRechercheListener(this));
        bandeauFiltre.add(validerFiltre);

        this.add(bandeauFiltre, BorderLayout.NORTH);
    }

    /**
     * Lance la recher des fichiers
     * 
     * @param tabFiltre
     *            les filtres choisis par l'user
     */
    public void lancerRecherche(IOFileFilter[] tabFiltre) {

        mainFrame.getNode().setFilters(tabFiltre);
        fichiersFiltres = mainFrame.getNode().filter(mainFrame.getNode().getFilePath());
        System.out.println("Taille array file : " + fichiersFiltres.length);
        this.printFilterResult();
        mainFrame.pack();
        for (IOFileFilter iof : mainFrame.getNode().getFilters()) {
            System.out.println("Node filter : " + iof);
        }

    }

    /**
     * Se charge de remplir la JTable ain de présenter les résultats de la
     * recherche
     */
    private void printFilterResult() {

        this.computeData();
        table = new JTable(data, headers) {

            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {

                return false;
            };
        };

        //Toute la ligne est selectionnée
        table.setRowSelectionAllowed(true);

        this.add(new JScrollPane(table), BorderLayout.CENTER);
    }

    /**
     * Rempli le tableau source de la JTable
     */
    public void computeData() {

        data = new Object[fichiersFiltres.length][headers.length];
        int rowIndex = 0;
        for (File f : fichiersFiltres) {
            data[rowIndex][0] = f.getName();
            data[rowIndex][1] = f.getAbsolutePath();
            data[rowIndex][2] = f.length();
            data[rowIndex][3] = new Date(f.lastModified());
            rowIndex++;
        }

    }

    public void loadFiltreView() {

        this.bandeauFiltre();
    }

}
