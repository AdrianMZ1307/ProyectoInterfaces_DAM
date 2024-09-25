package controller;

import java.awt.Image;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import javax.swing.*;

import model.pelicula;
import model.tablaPeliculas;
import view.ConnectionMenu;
import view.InformesView;

public class gestionarFormulario {
	private static Properties properties = new Properties();
	private static JMenuBar mnBar;
	private static JMenu mnFile, mnHelp, mnIdiomas;
	private static JMenuItem mnItConnection, mnItInformes, mnItExit, mnItSpanish, mnIGallician, mnItEnglish;
	private static JTable tbPeliculas;

	/**
	 * Este metodo traduce los menu superiores
	 * @param listMenu
	 * @param listMenuItems
	 */
	public static void traducirMenu(ArrayList<JMenu> listMenu, ArrayList<JMenuItem> listMenuItems) {
		try {
			ResourceBundle rb = ResourceBundle.getBundle("data.language_file");

			for (JMenu mn : listMenu) {
				String valor = rb.getString(mn.getName());
				mn.setText(valor);

			}
			for (JMenuItem mnIt : listMenuItems) {
				String valor = rb.getString(mnIt.getName());
				mnIt.setText(valor);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Este metodo traduce todos los botones
	 * @param listButtons
	 */
	public static void traducirBotones(ArrayList<JButton> listButtons) {
		try {
			ResourceBundle rb = ResourceBundle.getBundle("data.language_file");
			for (JButton btn : listButtons) {
				String valor = rb.getString(btn.getName());
				btn.setText(valor);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Este metodo traduce las etiquetas del sistema
	 * @param listLabel
	 */

	public static void traducirEtiquetas(ArrayList<JLabel> listLabel) {
		try {
			ResourceBundle rb = ResourceBundle.getBundle("data.language_file");
			for (JLabel lbl : listLabel) {
				String valor = rb.getString(lbl.getName());
				lbl.setText(valor);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
