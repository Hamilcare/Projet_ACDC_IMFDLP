package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.MainFrame;

public class ButtonPieChartListener implements ActionListener {

	MainFrame frame;

	public ButtonPieChartListener(MainFrame mf) {
		frame = mf;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		frame.enablePieChartView();

	}

}
