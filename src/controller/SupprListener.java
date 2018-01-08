package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import acdc_imfdlp.ContainerSupressionPossible;
import utils.Deletor;

public class SupprListener implements KeyListener {

    ContainerSupressionPossible container;

    public SupprListener(ContainerSupressionPossible vueDoublons) {
        super();
        this.container = vueDoublons;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
            System.out.println("suppr");
            new Deletor(container);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent e) {

        // TODO Auto-generated method stub

    }

}
