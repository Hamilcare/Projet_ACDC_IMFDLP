package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import org.apache.commons.io.filefilter.IOFileFilter;

import controller.ButtonLancerRechercheListener;
import controller.SupprListener;
import controller.TableSelectionListener;
import utils.TextPrompt;

public class ContainerFiltreView extends Container implements ContainerSupressionPossible {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5912635172318571302L;
	MainFrame mainFrame;
	Container bandeauFiltre;
	JTextField inputFilter;
	File[] fichiersFiltres;
	JTable table;
	JScrollPane containerTable;
	final String[] headers = { "Nom du Fichier", "Chemin", "Poids (octects)", "Date de Derniere Modif" };
	Object[][] data;

	ListSelectionModel cellSelectionModel;

	ArrayList<String> selectedFile;

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

		bandeauFiltre = new Container();
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
		//fichiersFiltres = mainFrame.getNode().filter(mainFrame.getNode().getFilePath());
		mainFrame.getNode().filterValentin(mainFrame.getNode().getFilePath());
		fichiersFiltres = new File[mainFrame.getNode().getResultFilter().size()];
		fichiersFiltres = mainFrame.getNode().getResultFilter().toArray(fichiersFiltres);
		System.out.println("Taille array file : " + fichiersFiltres.length);
		this.printFilterResult();
		mainFrame.pack();
		for (IOFileFilter iof : mainFrame.getNode().getFilters()) {
			System.out.println("Node filter : " + iof);
		}

	}

	/**
	 * Se charge de remplir la JTable ain de présenter les résultats de la recherche
	 */
	public void printFilterResult() {

		mainFrame.getNode().setResultFilter(new ArrayList<File>());
		if (containerTable != null)
			this.remove(containerTable);
		this.computeData();
		DefaultTableModel model = new DefaultTableModel(data, headers) {
			/**
			 * 
			 */
			private static final long serialVersionUID = -7625969081019988414L;

			@Override
			public Class<?> getColumnClass(int colNum) {
				switch (colNum) {
				case 0:
					return String.class;
				case 1:
					return String.class;
				case 2:
					return Long.class;
				case 3:
					return java.util.Date.class;

				default:
					return String.class;
				}
			}
		};

		table = new JTable(model) {

			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {

				return false;
			}
		};

		//table.setAutoCreateRowSorter(true);
		table.setRowSorter(new TableRowSorter<DefaultTableModel>(model));
		//Toute la ligne est selectionnée
		table.setRowSelectionAllowed(true);

		table.addKeyListener(new SupprListener(this));

		cellSelectionModel = table.getSelectionModel();
		cellSelectionModel.addListSelectionListener(new TableSelectionListener(this));
		containerTable = new JScrollPane(table);
		this.add(containerTable, BorderLayout.CENTER);
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

	public void removeJTable() {

		System.out.println("REmove JTable");
		if (table != null) {
			System.out.println("remove OK");
			this.remove(containerTable);
		}
	}

	public void loadFiltreView(boolean forceRefresh) {

		if (this.inputFilter == null) {
			this.bandeauFiltre();
		}
		if (forceRefresh && this.inputFilter != null) {
			this.remove(bandeauFiltre);
			this.bandeauFiltre();
			this.removeJTable();
		}

	}

	@Override
	public ArrayList<String> getFilePathToDelete() {

		return selectedFile;
	}

	@Override
	public void setFilePathToDelete(ArrayList<String> toDelete) {

		selectedFile = toDelete;

	}

	@Override
	public void restartVue() {

		mainFrame.refreshData(2);

	}

	@Override
	public JTable table() {

		return table;
	}

}
