package acdc_imfdlp;

import java.util.ArrayList;

import javax.swing.JTable;

public interface ContainerSupressionPossible {

    public ArrayList<String> getFilePathToDelete();

    public void setFilePathToDelete(ArrayList<String> toDelete);

    public void restartVue();

    public JTable table();

}
