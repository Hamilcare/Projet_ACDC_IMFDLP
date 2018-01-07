package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import acdc_imfdlp.ContainerDoublonsView;

/**
 * Se charge de la supression des fichiers désignés par l'utilisateur
 * 
 * @author Valentin
 *
 */
public class Deletor extends JFrame {

    ContainerDoublonsView vueDoublons;

    public Deletor(ContainerDoublonsView cdb) {
        vueDoublons = cdb;
        this.setTitle("Confirmez supression");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        String listeDesPaths = this.formatArray();
        String message = "Etes vous sur de vouloir supprimer les fichiers suivants : \n" + listeDesPaths;
        int choix = JOptionPane.showConfirmDialog(this, message, "lol", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        switch (choix) {
            case JOptionPane.YES_OPTION:
                System.out.println("Following files will be deleted: \n" + listeDesPaths);
                this.deleteFiles();
            default:
                break;
        }

    }

    public String formatArray() {

        ArrayList<String> pathToDelete = vueDoublons.getSelectedFile();
        StringBuilder sb = new StringBuilder();
        for (String s : pathToDelete) {
            sb.append(s).append("\n");
        }
        return sb.toString();
    }

    /**
     * Delete selected files
     */
    public void deleteFiles() {

        ArrayList<String> pathToDelete = vueDoublons.getSelectedFile();
        for (String s : pathToDelete) {
            try {
                Files.deleteIfExists(Paths.get(s));
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        vueDoublons.restartVueDoublons();
    }

}
