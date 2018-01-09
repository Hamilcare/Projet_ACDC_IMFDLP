package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.MainFrame;

public class ButtonDoublonsLister implements ActionListener {

    MainFrame frame;

    public ButtonDoublonsLister(MainFrame mf) {
        frame = mf;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {

        frame.enableDoublonsView();

    }

}
