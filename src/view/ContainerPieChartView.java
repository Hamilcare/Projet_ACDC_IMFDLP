package view;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JPanel;

import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import acdc_imfdlp.FileNode;

public class ContainerPieChartView extends Container {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5817385041667820252L;
	MainFrame mainFrame;
	JPanel pieChart;

	private PieDataset createDataset() {
		this.setLayout(new GridLayout(1, 3));
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("IPhone 5s", new Double(20));
		dataset.setValue("SamSung Grand", new Double(20));
		dataset.setValue("MotoG", new Double(40));
		dataset.setValue("Nokia Lumia", new Double(10));
		return dataset;
	}

	public ContainerPieChartView(FileNode fn) {
		this.createDataset();
		pieChart = new PieChart_AWT("Répartition taille", fn).createDemoPanel();
		this.add(pieChart);
	}

}
