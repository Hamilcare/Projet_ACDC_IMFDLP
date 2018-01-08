package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import acdc_imfdlp.MainFrame;

public class ButtonFiltreListener implements ActionListener {
	MainFrame frame;

	public ButtonFiltreListener(MainFrame mf) {
		frame = mf;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		frame.enableFiltreView();
	}

}
