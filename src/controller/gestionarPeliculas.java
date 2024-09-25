package controller;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import view.WelcomeMenu;

public class gestionarPeliculas {

	/**
	 * Este metodo lee de la base de datos y devuelve TODAS las peliculas
	 * 
	 * @return
	 */
	public static ArrayList<pelicula> listarPeliculas() {
		ArrayList<pelicula> listaPeliculas = new ArrayList<>();
		try {
			String consulta = "SELECT * FROM pelicula";
			PreparedStatement sentencia = gestionarConexion.getConexion().prepareStatement(consulta);
			ResultSet tablas = gestionarConexion.getConexion().getMetaData().getTables(null, null, "pelicula", null);
			if (tablas.next()) {
				ResultSet resultado = sentencia.executeQuery(consulta);
				while (resultado.next()) {
					listaPeliculas.add(new pelicula(resultado.getString(1), resultado.getString(2),
							resultado.getFloat(3), resultado.getInt(4), resultado.getDate(5), resultado.getBlob(6)));
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al cargar las Peliculas");
		}
		return listaPeliculas;
	}

	/**
	 * Este metodo lee de la base de datos y devuelve TODOS los actores
	 * 
	 * @return
	 */
	public static ArrayList<actor> listarActores() {
		ArrayList<actor> listaActores = new ArrayList<>();
		try {
			String consulta = "SELECT * FROM actor";
			PreparedStatement sentencia = gestionarConexion.getConexion().prepareStatement(consulta);
			ResultSet tablas = gestionarConexion.getConexion().getMetaData().getTables(null, null, "actor", null);
			if (tablas.next()) {
				ResultSet resultado = sentencia.executeQuery(consulta);
				while (resultado.next()) {
					listaActores.add(new actor(resultado.getString(1), resultado.getDate(2), resultado.getBlob(3)));
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al cargar los Actores");
		}
		return listaActores;
	}

	/**
	 * Este metodo lee de la base de datos y devuelve TODOS los generos de peliculas
	 * existentes en la base de datos
	 * 
	 * @return
	 */
	public static ArrayList<genero> listarGeneros() {
		ArrayList<genero> listaGeneros = new ArrayList<genero>();
		try {
			String consulta = "SELECT * FROM genero";
			PreparedStatement st = gestionarConexion.getConexion().prepareStatement(consulta);
			ResultSet resultado = st.executeQuery();
			while (resultado.next()) {
				listaGeneros.add(new genero(resultado.getString(1)));
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al cargar las Peliculas");
		}
		return listaGeneros;
	}

	public static ArrayList<pelicula> listarPeliculasFiltrar(String tituloPelicula) {
		ArrayList<pelicula> listaPeliculas = new ArrayList<>();
		try {
			String consulta = "SELECT * FROM pelicula WHERE titulo LIKE CONCAT(?,'%')";
			PreparedStatement sentencia = gestionarConexion.getConexion().prepareStatement(consulta);
			sentencia.setString(1, tituloPelicula);
			ResultSet resultado = sentencia.executeQuery();
			while (resultado.next()) {
				listaPeliculas.add(new pelicula(resultado.getString(1), resultado.getString(2), resultado.getFloat(3),
						resultado.getInt(4), resultado.getDate(5), resultado.getBlob(6)));
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al cargar las Peliculas");
		}
		return listaPeliculas;
	}

	public static ArrayList<actor> listarActoresFiltrar(String nombreActor) {
		ArrayList<actor> listaActores = new ArrayList<>();
		try {
			String consulta = "SELECT * FROM actor WHERE nombre LIKE CONCAT(?,'%')";
			PreparedStatement sentencia = gestionarConexion.getConexion().prepareStatement(consulta);
			sentencia.setString(1, nombreActor);
			ResultSet resultado = sentencia.executeQuery();
			while (resultado.next()) {
				listaActores.add(new actor(resultado.getString(1), resultado.getDate(2), resultado.getBlob(3)));
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al cargar los Actores");
		}
		return listaActores;
	}

	public static void modificarPelicula(pelicula p) {
		ArrayList<pelicula> listaPeliculas = new ArrayList<>();
		try {
			String consulta = "UPDATE pelicula " + "SET sinopsis = ?, puntuacion = ?,"
					+ "duracion = ?, fechaEstreno = ? WHERE titulo = ?";
			PreparedStatement sentencia = gestionarConexion.getConexion().prepareStatement(consulta);
			sentencia.setString(1, p.getSinopsis());
			sentencia.setFloat(2, p.getPuntuacion());
			sentencia.setInt(3, p.getDuracion());
			sentencia.setDate(4, p.getFechaEstreno());
			sentencia.setString(5, p.getTitulo());
			sentencia.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al cargar las Peliculas");
		}
	}

	public static void informePeliculas(String nombre) {
		URL informe = gestionarPeliculas.class.getResource("/Informes/peliculas.jrxml");
		try {
			String consulta = "SELECT * FROM pelicula WHERE titulo LIKE CONCAT(?,'%')";
			PreparedStatement st = gestionarConexion.getConexion().prepareStatement(consulta);
			st.setString(1, nombre);
			ResultSet resultado = st.executeQuery();

			JRResultSetDataSource ds = new JRResultSetDataSource(resultado);

			HashMap<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("Titulo", nombre);

			JasperReport report = JasperCompileManager.compileReport(informe.getFile());

			JasperPrint visor = JasperFillManager.fillReport(report, parametros, ds);

			JasperViewer.viewReport(visor, false);

		} catch (SQLException | JRException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al cargar el Informe");
		}
	}

	public static void informePeliculasGenero(String genero) {
		URL informe = gestionarPeliculas.class.getResource("/Informes/peliculas_genero.jrxml");
		try {
			String consulta = "SELECT * FROM pelicula \r\n" + "INNER JOIN pelicula_genero  \r\n"
					+ "ON pelicula.titulo = pelicula_genero.pelicula\r\n" + "WHERE ? = pelicula_genero.genero \r\n"
					+ "GROUP BY pelicula.titulo,pelicula_genero.genero";
			PreparedStatement st = gestionarConexion.getConexion().prepareStatement(consulta);
			st.setString(1, genero);
			ResultSet resultado = st.executeQuery();

			JRResultSetDataSource ds = new JRResultSetDataSource(resultado);

			HashMap<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("Genero", genero);

			JasperReport report = JasperCompileManager.compileReport(informe.getFile());

			JasperPrint visor = JasperFillManager.fillReport(report, parametros, ds);

			JasperViewer.viewReport(visor, false);

		} catch (SQLException | JRException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al cargar el Informe");
		}
	}

	public static void informePeliculasDeActor(String actor) {
		URL informe = gestionarPeliculas.class.getResource("/Informes/peliculas_actor.jrxml");
		try {
			String consulta = "SELECT * FROM pelicula\r\n" + "INNER JOIN pelicula_actor  \r\n"
					+ "ON pelicula.titulo = pelicula_actor.pelicula\r\n" + "WHERE ? = pelicula_actor.nombre_actor \r\n"
					+ "GROUP BY pelicula.titulo";
			PreparedStatement st = gestionarConexion.getConexion().prepareStatement(consulta);
			st.setString(1, actor);
			ResultSet resultado = st.executeQuery();

			JRResultSetDataSource ds = new JRResultSetDataSource(resultado);

			HashMap<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("Pelicula", actor);

			JasperReport report = JasperCompileManager.compileReport(informe.getFile());

			JasperPrint visor = JasperFillManager.fillReport(report, parametros, ds);

			JasperViewer.viewReport(visor, false);

		} catch (SQLException | JRException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al cargar el Informe");
		}
	}

	public static void informePeliculasActor(String pelicula) {
		URL informe = gestionarPeliculas.class.getResource("/Informes/actores_pelicula.jrxml");
		try {
			String consulta = "SELECT * FROM peliculas.actor\r\n" + "INNER JOIN pelicula_actor  \r\n"
					+ "ON actor.nombre = pelicula_actor.nombre_actor\r\n" + "WHERE ? = pelicula_actor.pelicula";
			PreparedStatement st = gestionarConexion.getConexion().prepareStatement(consulta);
			st.setString(1, pelicula);
			ResultSet resultado = st.executeQuery();

			JRResultSetDataSource ds = new JRResultSetDataSource(resultado);

			HashMap<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("Pelicula", pelicula);

			JasperReport report = JasperCompileManager.compileReport(informe.getFile());

			JasperPrint visor = JasperFillManager.fillReport(report, parametros, ds);

			JasperViewer.viewReport(visor, false);

		} catch (SQLException | JRException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al cargar el Informe");
		}
	}

	public static void informeActores(String nombre) {
		URL informe = gestionarPeliculas.class.getResource("/Informes/actores.jrxml");
		try {
			String consulta = "SELECT * FROM actor WHERE nombre LIKE CONCAT(?,'%')";
			PreparedStatement st = gestionarConexion.getConexion().prepareStatement(consulta);
			st.setString(1, nombre);
			ResultSet resultado = st.executeQuery();

			JRResultSetDataSource ds = new JRResultSetDataSource(resultado);

			HashMap<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("Nombre", nombre);

			JasperReport report = JasperCompileManager.compileReport(informe.getFile());

			JasperPrint visor = JasperFillManager.fillReport(report, parametros, ds);

			JasperViewer.viewReport(visor, false);
		} catch (SQLException | JRException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al cargar el Informe");
		}
	}

	public static pelicula obtenerPelicula(String titulo) {
		pelicula p = null;
		try {
			String consulta = "SELECT * FROM pelicula WHERE titulo = ? ";
			PreparedStatement st = gestionarConexion.getConexion().prepareStatement(consulta);
			st.setString(1, titulo);
			ResultSet resultado = st.executeQuery();
			while (resultado.next()) {
				p = new pelicula(resultado.getString(1), resultado.getString(1), resultado.getFloat(3),
						resultado.getInt(4), resultado.getDate(5), resultado.getBlob(6));
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al cargar la Pelicula");
		}
		return p;
	}

	public static actor obtenerActor(String nombre) {
		actor a = null;
		try {
			String consulta = "SELECT * FROM actor WHERE nombre = ? ";
			PreparedStatement st = gestionarConexion.getConexion().prepareStatement(consulta);
			st.setString(1, nombre);
			ResultSet resultado = st.executeQuery();
			while (resultado.next()) {
				a = new actor(resultado.getString(1), resultado.getDate(2), resultado.getBlob(3));
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al cargar al Actor");
		}
		return a;
	}

}
