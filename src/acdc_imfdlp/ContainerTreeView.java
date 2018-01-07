package acdc_imfdlp;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JScrollPane;

import controller.MyTreeSelectionListener;

public class ContainerTreeView extends Container {

    MainFrame               mainFrame;
    MyTreeSelectionListener listener;

    public ContainerTreeView(MainFrame mf) {
        mainFrame = mf;
        this.setLayout(new BorderLayout());
        listener = new MyTreeSelectionListener(mainFrame.getTree());
        mainFrame.getTree().addTreeSelectionListener(listener);
        this.add(new JScrollPane(mainFrame.getTree()), BorderLayout.CENTER);
    }

    public void refreshTreeView() {

        mainFrame.getTree().removeTreeSelectionListener(listener);
        listener = new MyTreeSelectionListener(mainFrame.getTree());
        mainFrame.getTree().addTreeSelectionListener(listener);
        this.add(new JScrollPane(mainFrame.getTree()), BorderLayout.CENTER);

    }

}
