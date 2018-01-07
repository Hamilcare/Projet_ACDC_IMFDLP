package controller;

import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import acdc_imfdlp.ContainerDoublonsView;

public class TableSelectionListener implements ListSelectionListener {

    ContainerDoublonsView vueDoublons;

    public TableSelectionListener(ContainerDoublonsView cdv) {
        vueDoublons = cdv;
    }

    @Override
    public void valueChanged(ListSelectionEvent arg0) {

        ArrayList<String> selectedData = new ArrayList<>();
        JTable table = vueDoublons.getTable();
        int[] selectedRow = table.getSelectedRows();

        for (int i = 0; i < selectedRow.length; i++) {

            selectedData.add((String) table.getValueAt(selectedRow[i], 1));
        }

        for (String s : selectedData) {
            System.out.println("row : " + s);
        }
        vueDoublons.setSelectedFile(selectedData);
    }

}
