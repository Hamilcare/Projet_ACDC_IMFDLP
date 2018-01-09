package view;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JPanel;

import acdc_imfdlp.FileNode;

/**
 * Contient la vue du pie chart utilisé dans treeView
 * 
 * @author valentin
 *
 */
public class ContainerPieChartView extends Container {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5817385041667820252L;

	/**
	 * Jpanel contenant le pieChart
	 */
	JPanel pieChart;

	/**
	 * 
	 * @param fn
	 *            le file node à partir duquel le dataset du chart est construit
	 */
	public ContainerPieChartView(FileNode fn) {
		this.setLayout(new GridLayout(1, 3));
		pieChart = new PieChart_AWT("Répartition taille", fn).createDemoPanel();
		this.add(pieChart);
	}

}
