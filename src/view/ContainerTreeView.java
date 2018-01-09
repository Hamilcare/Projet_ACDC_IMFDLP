package view;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import acdc_imfdlp.FileNode;
import controller.MyTreeSelectionListener;

public class ContainerTreeView extends Container {

	MainFrame mainFrame;
	MyTreeSelectionListener listener;
	JSplitPane splitPane;

	ContainerPieChartView pieChart;

	public ContainerTreeView(MainFrame mf) {
		mainFrame = mf;
		this.setLayout(new BorderLayout());
		listener = new MyTreeSelectionListener(mainFrame.getTree(), this);
		mainFrame.getTree().addTreeSelectionListener(listener);
		//this.add(new JScrollPane(mainFrame.getTree()), BorderLayout.CENTER);
		this.addSplitPane();
	}

	public void refreshTreeView() {

		mainFrame.getTree().removeTreeSelectionListener(listener);
		listener = new MyTreeSelectionListener(mainFrame.getTree(), this);
		mainFrame.getTree().addTreeSelectionListener(listener);
		this.add(new JScrollPane(mainFrame.getTree()), BorderLayout.CENTER);

	}

	public void addSplitPane() {
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setLeftComponent(new JScrollPane(mainFrame.getTree()));
		//splitPane.setRightComponent(mainFrame.getPieChartView());
		this.addPieChart();
		//splitPane.getLeftComponent().setMinimumSize(new Dimension(100, 100));
		this.add(splitPane, BorderLayout.CENTER);
	}

	public void addPieChart() {
		FileNode fn = new FileNode(mainFrame.getNode().getFilePath());
		pieChart = new ContainerPieChartView(fn);
		splitPane.setRightComponent(pieChart);

	}

	public void refreshPieChart(FileNode fn) {
		pieChart = new ContainerPieChartView(fn);
		splitPane.setRightComponent(pieChart);
	}

}
