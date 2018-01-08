package Controleur;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import acdc_imfdlp.FileNode;

public class JtreeListener implements TreeSelectionListener {

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		System.out.println("coucou");
		JTree tree = (JTree) e.getSource();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (node == null)
			//Nothing is selected.  
			return;

		FileNode Test = (FileNode) node.getUserObject();
		//		System.out.println(Test.getFile().getAbsolutePath());
		//		System.out.println(Test.getFile().length());

	}

}
