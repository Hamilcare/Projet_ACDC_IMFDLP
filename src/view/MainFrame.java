package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

import acdc_imfdlp.Node;
import controller.ButtonArbreListener;
import controller.ButtonDoublonsLister;
import controller.ButtonFiltreListener;
import controller.ButtonStartListener;
import utils.CacheFilter;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -6954342052589521863L;
	/**
	 * Référence vers la treeView
	 */
	ContainerTreeView treeView;
	/**
	 * Référence vers la vue des doublons
	 */
	ContainerDoublonsView doublonsView;
	/**
	 * Référence vers la vue des filtres
	 */
	ContainerFiltreView filtreView;
	/**
	 * le container de la vue principale il contient la treeView ou doublonsView ou
	 * filtreView
	 */
	Container principal = this.getContentPane();
	/**
	 * référence vers la vue active
	 */
	Container currentView;

	/**
	 * treemodel associé
	 */
	private TreeModel treeModel;
	/**
	 * Jtree découlant du treemodel
	 */
	private JTree tree;
	/**
	 * thread se chargeant de créer l'arbre
	 */
	private Thread explorer;
	/**
	 * la racine de l'arbre
	 */
	private Node node;

	private final String NOM_BOUTON_RESTART = "Restart";
	private final String NOM_BOUTTON_ARBRE = "Arbre";
	private final String NOM_BOUTON_DOUBLONS = "Doublons";
	private final String NOM_BOUTON_FILTRE = "Filtrer";

	/**
	 * Initialise la main frame et les vues
	 */
	public MainFrame() {

		this.setTitle("IL ME FAUT DE LA PLACE");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setMainLayout();

		this.init();//Initialise le node
		this.buildTree();//Construit l'arbre

		//pieChartView = new ContainerPieChartView(this.getTreeModel().getRoot().get);//Est une dépendance de treeView
		treeView = new ContainerTreeView(this);
		doublonsView = new ContainerDoublonsView(this);
		filtreView = new ContainerFiltreView(this);
		//pieChartView = new ContainerPieChartView(this);

		currentView = treeView;
		principal.add(creerBandeauSuperieur(), BorderLayout.NORTH);
		principal.add(treeView, BorderLayout.CENTER);
		System.out.println(treeView);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

	}

	/**
	 * positionne le layout
	 */
	public void setMainLayout() {

		principal.setLayout(new BorderLayout());
	}

	/**
	 * 
	 * @return treeView
	 */
	public ContainerTreeView getTreeView() {

		return treeView;
	}

	/**
	 * 
	 * @return doublonsView
	 */
	public ContainerDoublonsView getDoublonsView() {

		return doublonsView;
	}

	/**
	 * @return la vue active
	 */
	public Container getCurrentView() {

		return currentView;
	}

	/**
	 * Initialise l'application : -Demande le point de départ de l'arbo -Construit
	 * l'arbo
	 */
	public void init() {

		node = new Node();
		JFileChooser chooser;
		String choosertitle = "Select root Directory";

		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle(choosertitle);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		//
		// disable the "All files" option.
		//
		chooser.setAcceptAllFileFilterUsed(false);
		//Selection du point de départ    
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
			System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
			//Initialisation du node
			node.setFilePath(chooser.getSelectedFile().getAbsolutePath());
			node.setRoot();
		} else {
			System.out.println("No Selection ");
			System.exit(1);
		}

	}

	/**
	 * 
	 * @return le bandeau avec les boutons
	 */
	private Container creerBandeauSuperieur() {

		JPanel bandeauSuperieur = new JPanel();
		bandeauSuperieur.setLayout(new GridLayout(1, 5));

		JButton boutonSelectionArbo = new JButton(NOM_BOUTON_RESTART);
		boutonSelectionArbo.addActionListener(new ButtonStartListener(this));
		bandeauSuperieur.add(boutonSelectionArbo);

		JButton boutonVueArbre = new JButton(NOM_BOUTTON_ARBRE);
		boutonVueArbre.addActionListener(new ButtonArbreListener(this));
		bandeauSuperieur.add(boutonVueArbre);

		JButton boutonVueDoublons = new JButton(NOM_BOUTON_DOUBLONS);
		boutonVueDoublons.addActionListener(new ButtonDoublonsLister(this));
		bandeauSuperieur.add(boutonVueDoublons);

		JButton boutonVueFiltre = new JButton(NOM_BOUTON_FILTRE);
		boutonVueFiltre.addActionListener(new ButtonFiltreListener(this));
		bandeauSuperieur.add(boutonVueFiltre);

		return bandeauSuperieur;
	}

	/**
	 * construit le jtree
	 */
	public void buildTree() {

		treeModel = node.treeModel();
		tree = new JTree(treeModel);
		tree.setShowsRootHandles(true);
		tree.removeAll();
		CacheFilter cacheFilter = new CacheFilter();
		node.setFilters(new IOFileFilter[] { cacheFilter, FileFilterUtils.directoryFileFilter() });
		explorer = new Thread(node);
		explorer.start();
	}

	/**
	 * Refresh TreeView code 0 : treeView 1 : DoublonsView
	 */
	public void restart(int code) {

		this.init();
		this.buildTree();

		treeView = new ContainerTreeView(this);
		doublonsView = new ContainerDoublonsView(this);
		filtreView = new ContainerFiltreView(this);
		switch (code) {
		case 0:
			this.enableTreeView();
			break;
		case 1:
			this.enableDoublonsView();
		default:
			this.enableTreeView();
			break;
		}

		this.pack();
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	public static void main(String[] args) {

		MainFrame fenetre = new MainFrame();
		fenetre.createCacheDirectory();

		fenetre.setVisible(true);

	}

	/**
	 * crée le répertoire de cache si absent
	 */
	public void createCacheDirectory() {
		File theDir = new File("cache");

		// if the directory does not exist, create it
		if (!theDir.exists()) {
			System.out.println("creating directory: " + theDir.getName());
			boolean result = false;

			try {
				theDir.mkdir();
				result = true;
			} catch (SecurityException se) {
				System.out.println("SecurityException" + se);
			}
			if (result) {
				System.out.println("DIR created");
			}

		}
	}

	/**
	 * @return la treeView
	 */
	public ContainerTreeView getContainerTreeView() {

		return treeView;
	}

	public void setPrincipal(ContainerTreeView treeView) {

		this.treeView = treeView;
	}

	/**
	 * @return le treeModel
	 */
	public TreeModel getTreeModel() {

		return treeModel;
	}

	/**
	 * positionne treeModel
	 * 
	 * @param treeModel
	 */
	public void setTreeModel(TreeModel treeModel) {

		this.treeModel = treeModel;
	}

	/**
	 * 
	 * @return getTree
	 */
	public JTree getTree() {

		return tree;
	}

	/**
	 * position le Jtree
	 * 
	 * @param tree
	 */
	public void setTree(JTree tree) {

		this.tree = tree;
	}

	public Thread getExplorer() {

		return explorer;
	}

	public void setExplorer(Thread explorer) {

		this.explorer = explorer;
	}

	/**
	 * @return node
	 */
	public Node getNode() {

		return node;
	}

	/**
	 * positionne node
	 * 
	 * @param node
	 */
	public void setNode(Node node) {

		this.node = node;
	}

	/**
	 * Passe la vue active sur les filtres
	 */
	public void enableFiltreView() {

		filtreView.loadFiltreView(false);
		principal.remove(currentView);
		principal.add(filtreView, BorderLayout.CENTER);
		currentView = filtreView;
		this.pack();
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	/**
	 * Passe la vue active sur doublons
	 */
	public void enableDoublonsView() {

		doublonsView.loadDoublonsView(false);
		principal.remove(currentView);
		principal.add(doublonsView, BorderLayout.CENTER);
		currentView = doublonsView;
		this.pack();
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

	}

	/**
	 * Passe la vue active sur l'arbre
	 */
	public void enableTreeView() {

		principal.remove(currentView);
		principal.add(treeView, BorderLayout.CENTER);
		currentView = treeView;
		this.pack();
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	/**
	 * Refresh les data après une suppression
	 * 
	 * @param code
	 *            code 0 : treeView 1 : DoublonsView 2:FiltreView
	 */
	public void refreshData(int code) {

		//Sans ça les fils de l aracine ne sont pas refresh
		IOFileFilter[] tmpFilter = getNode().getFilters();
		node.setFilePath(node.getFilePath().getAbsolutePath());
		node.setRoot();
		this.buildTree();
		treeView = new ContainerTreeView(this);
		doublonsView = new ContainerDoublonsView(this);

		if (code == 1) {
			filtreView = new ContainerFiltreView(this);
			doublonsView.loadDoublonsView(true);
			this.enableDoublonsView();
		}

		if (code == 2) {
			filtreView.loadFiltreView(true);
			filtreView.lancerRecherche(tmpFilter);
			this.enableFiltreView();
		}

	}

}
