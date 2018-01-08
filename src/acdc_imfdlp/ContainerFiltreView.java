package acdc_imfdlp;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class ContainerFiltreView extends Container {
	MainFrame mainFrame;
	DefaultListModel<String> lm = new DefaultListModel<>();

	public ContainerFiltreView(MainFrame mf) {
		mainFrame = mf;
		this.setLayout(new BorderLayout());
	}

	public void bandeauFiltre() {
		lm.add(0, "coucou");
		lm.add(1, "salut");
		this.add(new JList<String>(lm), BorderLayout.NORTH);
	}

	public void loadFiltreView() {
		this.bandeauFiltre();
	}

}
