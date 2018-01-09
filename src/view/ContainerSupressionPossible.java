package view;

import java.util.ArrayList;

import javax.swing.JTable;

/**
 * Un container dans lequel la supression est possible
 * 
 * @author valentin
 *
 */
public interface ContainerSupressionPossible {
	/**
	 * 
	 * @return la liste des chemins absolues des fichiers à supprimer
	 */
	public ArrayList<String> getFilePathToDelete();

	/**
	 * positionne la liste des chemins absolues des fichiers à supprimer
	 * 
	 * @param toDelete
	 */
	public void setFilePathToDelete(ArrayList<String> toDelete);

	/**
	 * Refresh la vue après supression
	 */
	public void restartVue();

	/**
	 * 
	 * @return la jtable à partir de laquelle les fichiers à supprimer sont
	 *         déterminés
	 */
	public JTable table();

}
