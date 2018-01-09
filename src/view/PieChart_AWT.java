package view;

import java.io.File;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;

import acdc_imfdlp.FileNode;
import utils.FileSizeComputer;

/**
 * 
 * @author https://www.tutorialspoint.com/jfreechart/jfreechart_pie_chart.htm
 *
 */
public class PieChart_AWT extends ApplicationFrame {
	FileNode n;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PieChart_AWT(String title, FileNode n) {
		super(title);
		this.n = n;
		setContentPane(createDemoPanel());
	}

	private static PieDataset createDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("IPhone 5s", new Double(20));
		dataset.setValue("SamSung Grand", new Double(20));
		dataset.setValue("MotoG", new Double(40));
		dataset.setValue("Nokia Lumia", new Double(10));
		return dataset;
	}

	private PieDataset computeDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		if (n.getFile().isDirectory()) {
			for (File f : n.getFile().listFiles()) {
				dataset.setValue(f.getName(), FileSizeComputer.computeFileSize(f, 0));
			}
		} else {
			dataset.setValue(n.getFile().getName(), n.getFile().length());
		}
		return dataset;

	}

	private static JFreeChart createChart(PieDataset dataset) {

		JFreeChart chart = ChartFactory.createPieChart("Repartition taille", // chart title 
				dataset, // data    
				true, // include legend   
				true, false);

		return chart;
	}

	public JPanel createDemoPanel() {
		JFreeChart chart = createChart(computeDataset());
		return new ChartPanel(chart);
	}

}
