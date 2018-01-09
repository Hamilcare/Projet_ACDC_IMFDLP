package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.MainFrame;

public class ButtonStartListener implements ActionListener {

    MainFrame frame;

    public ButtonStartListener(MainFrame mf) {
        frame = mf;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        frame.restart(0);

    }

}
