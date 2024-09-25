package controller;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.util.*;

import javax.swing.JOptionPane;

import view.WelcomeMenu;

public class gestionarConexion {
	private static Connection con;
	private static Properties properties = new Properties();

	/**
	 * Este metodo realiza la conexion con la base de datos
	 */
	public static void conectar() {
		try {
			File file = new File(gestionarConexion.class.getResource("/data/connection.properties").getPath());
			properties.load(new FileInputStream(file));
			Class.forName("com.mysql.jdbc.Driver");
			String dirBD = "jdbc:mysql://" + properties.get("IP") + ":" + properties.get("PORT") + "/"
					+ properties.get("BD");
			con = DriverManager.getConnection(dirBD, properties.get("USER") + "", properties.get("PASSWORD") + "");
			con.setAutoCommit(false);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Ha Ocurrido un error al Conectar");
			e.printStackTrace();
			System.exit(0);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Ha Ocurrido un error al cargar el Driver");
			e.printStackTrace();
			System.exit(0);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "No se encuentra el fichero properties", "ERROR",
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Ha ocurrido un error al leer el fichero properties", "ERROR",
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			e.printStackTrace();
		}
	}

	public static Connection getConexion() {
		File file = new File(gestionarConexion.class.getResource("/data/connection.properties").getPath());
		return con;
	}

	/**
	 * Este metodo finaliza la conexion con la base de datos
	 */
	public static void cerrarConexion() {
		try {
			File file = new File(gestionarConexion.class.getResource("/data/connection.properties").getPath());
			con.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Ha Ocurrido un error al cerrar la conexión");
			System.exit(0);
		}
	}

	/**
	 * Este petodo se usa para modificar el fichero de conexion en el que el usuario
	 * modifica: ·IP ·PUERTO ·USUARIO ·CONTRASEÑA ·BASE DE DATOS A USAR
	 * 
	 * @param datos
	 */
	public static void cambiarProperties(String[] datos) {
		try {
			File file = new File(gestionarConexion.class.getResource("/data/connection.properties").getPath());
			properties.load(new FileInputStream(file));
			properties.setProperty("IP", datos[0]);
			properties.setProperty("PORT", datos[1]);
			properties.setProperty("BD", datos[2]);
			properties.setProperty("USER", datos[3]);
			properties.setProperty("PASSWORD", datos[4]);
			FileOutputStream fileOS = new FileOutputStream(file);
			properties.store(fileOS, null);
			fileOS.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "No se encuentra el fichero properties", "ERROR",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Ha ocurrido un error al leer el fichero properties", "ERROR",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	/**
	 * Este metodo lee los datos del fichero de conexion y los devuelve
	 * 
	 * @return
	 */
	public static String[] leerProperties() {
		String[] datos = new String[5];
		try {
			File file = new File(gestionarConexion.class.getResource("/data/connection.properties").getPath());
			properties.load(new FileInputStream(file));
			datos[0] = String.valueOf(properties.get("IP"));
			datos[1] = String.valueOf(properties.get("PORT"));
			datos[2] = String.valueOf(properties.get("BD"));
			datos[3] = String.valueOf(properties.get("USER"));
			datos[4] = String.valueOf(properties.get("PASSWORD"));
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "No se encuentra el fichero properties", "ERROR",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Ha ocurrido un error al leer el fichero properties", "ERROR",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datos;
	}
}
