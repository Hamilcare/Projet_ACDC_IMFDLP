package controller;

import java.io.File;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import acdc_imfdlp.FileNode;
import view.ContainerTreeView;

public class MyTreeSelectionListener implements TreeSelectionListener {

	JTree tree;
	ContainerTreeView treeView;

	public MyTreeSelectionListener(JTree t, ContainerTreeView tv) {
		tree = t;
		treeView = tv;
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

		/* if nothing is selected */
		if (node == null)
			return;

		/* retrieve the node that was selected */
		FileNode nodeInfo;
		try {
			nodeInfo = (FileNode) node.getUserObject();
		} catch (Exception except) {
			nodeInfo = new FileNode((File) node.getUserObject());
		}

		/* React to the node selection. */
		System.out.println("Selected Node : " + nodeInfo.toString());
		treeView.refreshPieChart(nodeInfo);

	}

}
