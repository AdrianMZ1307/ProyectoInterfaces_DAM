package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.gestionarConexion;
import controller.gestionarPeliculas;
import model.actor;
import model.genero;
import model.pelicula;

import javax.swing.JDesktopPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

public class InformesView extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			InformesView dialog = new InformesView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public InformesView() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 150);
		getContentPane().setLayout(new BorderLayout());
		
		JButton btnActores = new JButton("ACTORES");
		getContentPane().add(btnActores, BorderLayout.NORTH);
		
		JButton btnPeliculas = new JButton("PELICULAS");
		getContentPane().add(btnPeliculas, BorderLayout.SOUTH);
		
		JButton btnPeliculasGeneros = new JButton("PELICULAS_GENEROS");
		getContentPane().add(btnPeliculasGeneros, BorderLayout.WEST);
		
		JButton btnPeliculasActor = new JButton("PELICULAS_ACTOR");
		getContentPane().add(btnPeliculasActor, BorderLayout.EAST);
		
		JButton btnPeliculas_Actores = new JButton("ACTORES_PELICULAS");
		getContentPane().add(btnPeliculas_Actores);
		
		btnActores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = JOptionPane.showInputDialog(null, "Introduce el inicio del nombre del Autor");
				if (nombre == null) {
					nombre = "";
				}
				gestionarConexion.conectar();
				gestionarPeliculas.informeActores(nombre);
				gestionarConexion.cerrarConexion();
			}
		});
		btnPeliculas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = JOptionPane.showInputDialog(null, "Introduce el inicio del Titulo de la Pelicula");
				if (nombre == null) {
					nombre = "";
				}
				gestionarConexion.conectar();
				gestionarPeliculas.informePeliculas(nombre);
				gestionarConexion.cerrarConexion();
			}
		});
		btnPeliculasGeneros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gestionarConexion.conectar();
				ArrayList<genero> listaGeneros = gestionarPeliculas.listarGeneros();
				Object[] generoList = listaGeneros.toArray();
				String genero = String
						.valueOf(JOptionPane.showInputDialog(null, "Que Genero de pelicula quieres listar?", "ELIGE",
								JOptionPane.QUESTION_MESSAGE, null, generoList, generoList[0]));
				gestionarPeliculas.informePeliculasGenero(genero);
				gestionarConexion.cerrarConexion();
			}
		});
		btnPeliculasActor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gestionarConexion.conectar();
				ArrayList<actor> listaActores = gestionarPeliculas.listarActores();
				Object[] actoresList = listaActores.toArray();
				String actor = String
						.valueOf(JOptionPane.showInputDialog(null, "De que actor quieres ver sus peliculas?", "ELIGE",
								JOptionPane.QUESTION_MESSAGE, null, actoresList, actoresList[0]));
				gestionarPeliculas.informePeliculasDeActor(actor);
				gestionarConexion.cerrarConexion();
			}
		});
		btnPeliculas_Actores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gestionarConexion.conectar();
				ArrayList<pelicula> listaPeliculas = gestionarPeliculas.listarPeliculas();
				Object[] peliculaList = listaPeliculas.toArray();
				String pelicula = String
						.valueOf(JOptionPane.showInputDialog(null, "De que pelicula quieres listar a los actores?", "ELIGE",
								JOptionPane.QUESTION_MESSAGE, null, peliculaList, peliculaList[0]));
				gestionarPeliculas.informePeliculasActor(pelicula);
				gestionarConexion.cerrarConexion();
			}
		});

	}
}
