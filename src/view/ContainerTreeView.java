package view;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import acdc_imfdlp.FileNode;
import controller.MyTreeSelectionListener;

public class ContainerTreeView extends Container {

	private static final long serialVersionUID = -6552967543421183140L;
	/**
	 * référence vers la MainFrame
	 */
	MainFrame mainFrame;
	/**
	 * référence vers le listener attaché
	 */
	MyTreeSelectionListener listener;

	/**
	 * le splitpane contenant le jtree et le piechart
	 */
	JSplitPane splitPane;

	/**
	 * le contenant du pieChart
	 */
	ContainerPieChartView pieChart;

	public ContainerTreeView(MainFrame mf) {
		mainFrame = mf;
		this.setLayout(new BorderLayout());
		listener = new MyTreeSelectionListener(mainFrame.getTree(), this);
		mainFrame.getTree().addTreeSelectionListener(listener);
		this.addSplitPane();
	}

	/**
	 * refresh la vue
	 */
	public void refreshTreeView() {

		mainFrame.getTree().removeTreeSelectionListener(listener);
		listener = new MyTreeSelectionListener(mainFrame.getTree(), this);
		mainFrame.getTree().addTreeSelectionListener(listener);
		this.add(new JScrollPane(mainFrame.getTree()), BorderLayout.CENTER);

	}

	/**
	 * gère l'ajout du splitPane à la vue
	 */
	public void addSplitPane() {
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setLeftComponent(new JScrollPane(mainFrame.getTree()));
		//splitPane.setRightComponent(mainFrame.getPieChartView());
		this.addPieChart();
		//splitPane.getLeftComponent().setMinimumSize(new Dimension(100, 100));
		this.add(splitPane, BorderLayout.CENTER);
	}

	/**
	 * gère l'ajout du pieChart au SplitPane
	 */
	public void addPieChart() {
		FileNode fn = new FileNode(mainFrame.getNode().getFilePath());
		pieChart = new ContainerPieChartView(fn);
		splitPane.setRightComponent(pieChart);

	}

	/**
	 * refresh le pieChart
	 * 
	 * @param fn
	 *            le file node à partir duquel le dataset du chart est construit
	 */
	public void refreshPieChart(FileNode fn) {
		pieChart = new ContainerPieChartView(fn);
		splitPane.setRightComponent(pieChart);
	}

}
