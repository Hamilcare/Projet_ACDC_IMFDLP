package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

import acdc_imfdlp.ContainerFiltreView;

public class ButtonLancerRechercheListener implements ActionListener {

    ContainerFiltreView vueFiltre;

    public ButtonLancerRechercheListener(ContainerFiltreView cfv) {
        vueFiltre = cfv;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String listeFiltre = (vueFiltre.getInputFilter().getText());
        if (!listeFiltre.isEmpty()) {
            //Etape 1 : récupérer les filtres sous forme de String[]
            String[] arrayStrFilter = listeFiltreToArray(listeFiltre);
            //Etape2 : transformer les str de filtre en IOFilter
            IOFileFilter[] arrayIOFileFilter = strToIOFileFilter(arrayStrFilter);
            //Etape3 : Repasser la main au ContainerFiltreView afin de lancer la recherche
            vueFiltre.lancerRecherche(arrayIOFileFilter);
        }
    }

    public IOFileFilter[] strToIOFileFilter(String[] tabStrFiltre) {

        //IOFileFilter[] resul = new IOFileFilter[tabStrFiltre.length + 2];
        //        IOFileFilter[] resul = new IOFileFilter[tabStrFiltre.length + 1];
        IOFileFilter[] resul = new IOFileFilter[tabStrFiltre.length];
        //resul[0] = new CacheFilter();
        //resul[1] = FileFilterUtils.directoryFileFilter();
        //resul[0] = FileFilterUtils.directoryFileFilter();
        for (int i = 0; i < resul.length; i++) {
            resul[i] = FileFilterUtils.suffixFileFilter(tabStrFiltre[i], IOCase.INSENSITIVE);
        }

        System.out.println("IOFILEFILTER : ");
        for (IOFileFilter iof : resul) {
            System.out.println(iof);
        }

        return resul;

    }

    /**
     * 
     * @param listeFiltre
     *            entrée par l'utilisateur
     * @return un tableau de string contenant un filtre par ligne
     */
    private String[] listeFiltreToArray(String listeFiltre) {

        String[] tmpTab = listeFiltre.split(" ");
        ArrayList<String> tmpArray = new ArrayList<>();

        for (String s : tmpTab) {
            s = s.trim();
            if (!s.isEmpty())
                tmpArray.add(s);
        }

        String[] resul = new String[tmpArray.size()];
        resul = tmpArray.toArray(resul);

        System.out.println("Array Filtre : ");
        for (String s : resul) {
            System.out.println(s);
        }
        return resul;
    }

}
