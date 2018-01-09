package acdc_imfdlp;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import controller.MyTreeSelectionListener;

public class ContainerTreeView extends Container {

	MainFrame mainFrame;
	MyTreeSelectionListener listener;
	JSplitPane splitPane;

	public ContainerTreeView(MainFrame mf) {
		mainFrame = mf;
		this.setLayout(new BorderLayout());
		listener = new MyTreeSelectionListener(mainFrame.getTree());
		mainFrame.getTree().addTreeSelectionListener(listener);
		//this.add(new JScrollPane(mainFrame.getTree()), BorderLayout.CENTER);
		this.addSplitPane();
	}

	public void refreshTreeView() {

		mainFrame.getTree().removeTreeSelectionListener(listener);
		listener = new MyTreeSelectionListener(mainFrame.getTree());
		mainFrame.getTree().addTreeSelectionListener(listener);
		this.add(new JScrollPane(mainFrame.getTree()), BorderLayout.CENTER);

	}

	public void addSplitPane() {
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setLeftComponent(new JScrollPane(mainFrame.getTree()));
		splitPane.setRightComponent(mainFrame.getPieChartView());
		//splitPane.getLeftComponent().setMinimumSize(new Dimension(100, 100));
		this.add(splitPane, BorderLayout.CENTER);
	}

}
