package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import acdc_imfdlp.ContainerDoublonsView;
import utils.Deletor;

public class SupprListener implements KeyListener {

    ContainerDoublonsView vueDoublons;

    public SupprListener(ContainerDoublonsView vueDoublons) {
        super();
        this.vueDoublons = vueDoublons;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
            System.out.println("suppr");
            new Deletor(vueDoublons);
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
