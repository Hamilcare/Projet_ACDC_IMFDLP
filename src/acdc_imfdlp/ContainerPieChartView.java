package acdc_imfdlp;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JPanel;

import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import view.PieChart_AWT;

public class ContainerPieChartView extends Container {

	MainFrame mainFrame;
	JPanel pieChart;

	private PieDataset dataset;

	private PieDataset createDataset() {
		this.setLayout(new GridLayout(1, 3));
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("IPhone 5s", new Double(20));
		dataset.setValue("SamSung Grand", new Double(20));
		dataset.setValue("MotoG", new Double(40));
		dataset.setValue("Nokia Lumia", new Double(10));
		return dataset;
	}

	public ContainerPieChartView(MainFrame mf) {
		mainFrame = mf;
		dataset = this.createDataset();
		pieChart = new PieChart_AWT("Répartition taille", mainFrame.getNode()).createDemoPanel();
		pieChart.setSize(560, 367);
		//pieChart.setVisible(true);
		this.add(pieChart);
		this.add(pieChart);
		this.add(pieChart);
	}

	public ContainerPieChartView(FileNode fn) {
		dataset = this.createDataset();
		//pieChart = new PieChart_AWT("Répartition taille", n)
	}

}
