package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import acdc_imfdlp.MainFrame;

public class ButtonArbreListener implements ActionListener {

    MainFrame frame;

    public ButtonArbreListener(MainFrame mf) {
        frame = mf;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        frame.enableTreeView();

    }

}
