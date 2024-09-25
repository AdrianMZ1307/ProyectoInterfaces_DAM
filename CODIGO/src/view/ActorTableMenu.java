package view;

import java.io.*;

import javax.help.*;
import javax.swing.*;

import model.*;
import controller.*;

import java.net.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.*;
import java.awt.*;

public class ActorTableMenu {

	private JFrame frmTablaActores;
	private static JTable tbActores;
	private JTextField txtFiltro;
	private JMenuItem mnItAyuda;
	private static JMenuBar mnBar;
	private static JMenu mnFile, mnHelp, mnIdiomas;
	private static JMenuItem mnItConnection, mnItInformes, mnItGetHelp, mnItExit, mnItSpanish, mnIGallician,
			mnItEnglish;
	private HelpSet helpset = null;
	private HelpBroker browser = null;
	private ResourceBundle rb;
	public static Locale language;
	private static Properties properties = new Properties();
	private static File file;
	private static ArrayList<JMenu> listMenu = new ArrayList<>();
	private static ArrayList<JLabel> listLabel = new ArrayList<>();
	private static ArrayList<JMenuItem> listMenuItem = new ArrayList<>();
	private static ArrayList<JButton> listButtons = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ActorTableMenu window = new ActorTableMenu();
					window.frmTablaActores.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ActorTableMenu() {
		initialize();

		try {
			File file = new File(gestionarConexion.class.getResource("/data/default.properties").getPath());
			properties.load(new FileInputStream(file));
			String lang[] = String.valueOf(properties.get("LANG")).split("_");
			language = new Locale(lang[0], lang[1]);
			System.out.println(lang[0]);
			Locale.setDefault(language);
			URL helpURL = null;

			switch (lang[0]) {
			case "es":
				helpURL = this.getClass().getResource("/help/help.hs");
				helpset = new HelpSet(null, helpURL);
				browser = helpset.createHelpBroker();
				browser.enableHelpOnButton(mnItGetHelp, "tabla_peliculas", helpset);
				browser.enableHelpKey(frmTablaActores.getContentPane(), "tabla_peliculas", helpset);
				break;
			case "en":
				helpURL = this.getClass().getResource("/help/help_en_US.hs");
				helpset = new HelpSet(null, helpURL);
				browser = helpset.createHelpBroker();
				browser.enableHelpOnButton(mnItGetHelp, "tabla_peliculas", helpset);
				browser.enableHelpKey(frmTablaActores.getContentPane(), "tabla_peliculas", helpset);
				break;
			case "gl":
				helpURL = this.getClass().getResource("/help/help_gl_ES.hs");
				helpset = new HelpSet(null, helpURL);
				browser = helpset.createHelpBroker();
				browser.enableHelpOnButton(mnItGetHelp, "tabla_peliculas", helpset);
				browser.enableHelpKey(frmTablaActores.getContentPane(), "tabla_peliculas", helpset);
				break;
			default:
				break;
			}

		} catch (HelpSetException | IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTablaActores = new JFrame();
		frmTablaActores.setTitle("Tabla Actores - AdrianMZ");
		frmTablaActores.setBounds(100, 100, 450, 300);
		frmTablaActores.setMinimumSize(new Dimension(450, 300));
		frmTablaActores.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frmTablaActores.getContentPane().setLayout(new BorderLayout(0, 0));
		Panel filterPane = new Panel();
		frmTablaActores.getContentPane().add(filterPane, BorderLayout.NORTH);
		Panel filmPane = new Panel();
		frmTablaActores.getContentPane().add(filmPane, BorderLayout.SOUTH);

		JScrollPane scPeliculas = new JScrollPane();
		scPeliculas.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frmTablaActores.getContentPane().add(scPeliculas, BorderLayout.CENTER);

		tbActores = new JTable();
		tbActores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbActores.setEnabled(true);
		scPeliculas.setViewportView(tbActores);
		tbActores.setAutoCreateRowSorter(true);
		filterPane.setLayout(new GridLayout(0, 3, 0, 0));

		JLabel lblFiltro = new JLabel();
		lblFiltro.setHorizontalAlignment(SwingConstants.CENTER);
		lblFiltro.setText("Nombre del Actor");
		filterPane.add(lblFiltro);
		lblFiltro.setName("lblFiltro");

		txtFiltro = new JTextField();
		filterPane.add(txtFiltro);
		txtFiltro.setColumns(10);

		JButton btnFiltro = new JButton("Buscar");
		filterPane.add(btnFiltro);
		btnFiltro.setName("btnFiltro");

		JButton btnVer = new JButton("VER DATOS");
		btnVer.setEnabled(false);
		filmPane.add(btnVer);
		btnVer.setName("btnVer");

		JMenuBar mnBar = new JMenuBar();
		frmTablaActores.setJMenuBar(mnBar);

		mnFile = new JMenu("FILE");
		mnFile.setIcon(new ImageIcon(this.getClass().getResource("/Icons/file.png")));
		mnBar.add(mnFile);

		ImageIcon imgConnection = new ImageIcon(this.getClass().getResource("/Icons/connection.png"));
		Image image = imgConnection.getImage();
		Image newimg = image.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
		imgConnection = new ImageIcon(newimg);

		mnItConnection = new JMenuItem("CONNECTION");
		mnItConnection.setIcon(imgConnection);
		mnFile.add(mnItConnection);

		mnItConnection.addActionListener(new ActionListener() {
			// METODO PARA CERRAR EL PROGRAMA
			public void actionPerformed(ActionEvent e) {
				ConnectionMenu menu = new ConnectionMenu();
				menu.show();
			}
		});

		imgConnection = new ImageIcon(this.getClass().getResource("/Icons/exit.png"));
		image = imgConnection.getImage();
		newimg = image.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
		imgConnection = new ImageIcon(newimg);

		mnItInformes = new JMenuItem("INFORMES");
		mnItInformes.setIcon(imgConnection);
		mnFile.add(mnItInformes);

		mnItInformes.addActionListener(new ActionListener() {
			// METODO PARA CERRAR EL PROGRAMA
			public void actionPerformed(ActionEvent e) {
				InformesView.main(null);
			}
		});

		mnItExit = new JMenuItem("EXIT");
		mnItExit.setIcon(imgConnection);
		mnFile.add(mnItExit);

		mnItExit.addActionListener(new ActionListener() {
			// METODO PARA CERRAR EL PROGRAMA
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		imgConnection = new ImageIcon(this.getClass().getResource("/Icons/exit.png"));
		image = imgConnection.getImage();
		newimg = image.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);

		mnHelp = new JMenu("HELP");
		mnHelp.setIcon(new ImageIcon(this.getClass().getResource("/Icons/help.png")));
		mnBar.add(mnHelp);

		mnItGetHelp = new JMenuItem("GET HELP");
		mnItGetHelp.setIcon(new ImageIcon(this.getClass().getResource("/Icons/get_help.png")));
		mnHelp.add(mnItGetHelp);
		mnItGetHelp.setName("mnItGetHelp");

		mnIdiomas = new JMenu("IDIOMAS");
		mnIdiomas.setIcon(new ImageIcon(this.getClass().getResource("/Icons/language.png")));
		mnHelp.add(mnIdiomas);

		mnItSpanish = new JMenuItem("ESPAÑOL");
		mnItSpanish.setIcon(new ImageIcon(this.getClass().getResource("/Icons/spain.png")));
		mnIdiomas.add(mnItSpanish);

		mnIGallician = new JMenuItem("GALLEGO");
		mnIGallician.setIcon(new ImageIcon(this.getClass().getResource("/Icons/language.png")));
		mnIdiomas.add(mnIGallician);

		mnItEnglish = new JMenuItem("INGLÉS");
		mnItEnglish.setIcon(new ImageIcon(this.getClass().getResource("/Icons/usa.png")));
		mnIdiomas.add(mnItEnglish);

		mnFile.setName("mnFile");
		mnHelp.setName("mnHelp");
		mnIdiomas.setName("mnIdiomas");
		mnItConnection.setName("mnItConnection");
		mnIGallician.setName("mnIGallician");
		mnItEnglish.setName("mnItEnglish");
		mnItSpanish.setName("mnItSpanish");
		mnItConnection.setName("mnItConnection");
		mnItExit.setName("mnItExit");
		mnItInformes.setName("mnItInformes");

		listLabel.add(lblFiltro);
		listButtons.add(btnVer);
		listButtons.add(btnFiltro);

		listMenu.add(mnFile);
		listMenu.add(mnHelp);
		listMenu.add(mnIdiomas);

		listMenuItem.add(mnItConnection);
		listMenuItem.add(mnIGallician);
		listMenuItem.add(mnItEnglish);
		listMenuItem.add(mnItSpanish);
		listMenuItem.add(mnItConnection);
		listMenuItem.add(mnItExit);
		listMenuItem.add(mnItInformes);
		listMenuItem.add(mnItGetHelp);

		mnItSpanish.addActionListener(new ActionListener() {
			// METODO PARA CERRAR EL PROGRAMA
			public void actionPerformed(ActionEvent e) {
				gestionarConexion.conectar();
				try {
					File file = new File(gestionarConexion.class.getResource("/data/default.properties").getPath());
					properties.setProperty("LANG", String.valueOf("es_ES"));
					FileOutputStream fileOS = new FileOutputStream(file);
					properties.store(fileOS, null);
					fileOS.close();
					Locale.setDefault(new Locale("es_ES"));

					gestionarFormulario.traducirMenu(listMenu, listMenuItem);
					gestionarFormulario.traducirBotones(listButtons);
					gestionarFormulario.traducirEtiquetas(listLabel);
					if (tbActores != null) {
						ArrayList<actor> listaActores = gestionarPeliculas.listarActores();
						tablaActores tablaModel = new tablaActores(listaActores);
						tablaModel.traducirTabla();
						tbActores.setModel(tablaModel);
					}

					URL helpURL = this.getClass().getResource("/help/help.hs");

					helpset = new HelpSet(null, helpURL);
					browser = helpset.createHelpBroker();
					browser.enableHelpOnButton(mnItGetHelp, "tabla_peliculas", helpset);
					browser.enableHelpKey(frmTablaActores.getContentPane(), "tabla_peliculas", helpset);
				} catch (IOException | HelpSetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				gestionarConexion.cerrarConexion();
			}
		});
		mnItEnglish.addActionListener(new ActionListener() {
			// METODO PARA CERRAR EL PROGRAMA
			public void actionPerformed(ActionEvent e) {
				gestionarConexion.conectar();
				try {
					File file = new File(gestionarConexion.class.getResource("/data/default.properties").getPath());
					properties.setProperty("LANG", String.valueOf("en_US"));
					FileOutputStream fileOS = new FileOutputStream(file);
					properties.store(fileOS, null);
					fileOS.close();
					Locale.setDefault(new Locale("en_US"));

					gestionarFormulario.traducirMenu(listMenu, listMenuItem);
					gestionarFormulario.traducirBotones(listButtons);
					gestionarFormulario.traducirEtiquetas(listLabel);
					if (tbActores != null) {
						ArrayList<actor> listaActores = gestionarPeliculas.listarActores();
						tablaActores tablaModel = new tablaActores(listaActores);
						tablaModel.traducirTabla();
						tbActores.setModel(tablaModel);
					}

					URL helpURL = this.getClass().getResource("/help/help_en_US.hs");

					helpset = new HelpSet(null, helpURL);
					browser = helpset.createHelpBroker();
					browser.enableHelpOnButton(mnItGetHelp, "tabla_peliculas", helpset);
					browser.enableHelpKey(frmTablaActores.getContentPane(), "tabla_peliculas", helpset);
				} catch (IOException | HelpSetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				gestionarConexion.cerrarConexion();
			}
		});
		mnIGallician.addActionListener(new ActionListener() {
			// METODO PARA CERRAR EL PROGRAMA
			public void actionPerformed(ActionEvent e) {
				gestionarConexion.conectar();
				try {
					File file = new File(gestionarConexion.class.getResource("/data/default.properties").getPath());
					properties.setProperty("LANG", String.valueOf("gl_ES"));
					FileOutputStream fileOS = new FileOutputStream(file);
					properties.store(fileOS, null);
					fileOS.close();
					Locale.setDefault(new Locale("gl_ES"));

					gestionarFormulario.traducirMenu(listMenu, listMenuItem);
					gestionarFormulario.traducirBotones(listButtons);
					gestionarFormulario.traducirEtiquetas(listLabel);
					if (tbActores != null) {
						ArrayList<actor> listaActores = gestionarPeliculas.listarActores();
						tablaActores tablaModel = new tablaActores(listaActores);
						tablaModel.traducirTabla();
						tbActores.setModel(tablaModel);
					}

					URL helpURL = this.getClass().getResource("/help/help_gl_ES.hs");

					helpset = new HelpSet(null, helpURL);
					browser = helpset.createHelpBroker();
					browser.enableHelpOnButton(mnItGetHelp, "tabla_peliculas", helpset);
					browser.enableHelpKey(frmTablaActores.getContentPane(), "tabla_peliculas", helpset);
				} catch (IOException | HelpSetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				gestionarConexion.cerrarConexion();
			}
		});

		// ----- VENTANA
		// --------------------------------------------------------------------------------------------------------
		frmTablaActores.addWindowListener(new WindowAdapter() {
			// ------- ABRIR
			// ----------------------------------------------------------------------------------------------------
			@Override
			public void windowOpened(WindowEvent e) {
				try {
					File file = new File(gestionarConexion.class.getResource("/data/default.properties").getPath());
					properties.load(new FileInputStream(file));
					String lang[] = String.valueOf(properties.get("LANG")).split("_");
					language = new Locale(lang[0], lang[1]);
					Locale.setDefault(language);

					gestionarConexion.conectar();

					cargarTabla();

					if (!listMenu.isEmpty() && !listMenu.isEmpty()) {
						gestionarFormulario.traducirMenu(listMenu, listMenuItem);
					}
					if (!listButtons.isEmpty()) {
						gestionarFormulario.traducirBotones(listButtons);
					}
					if (!listLabel.isEmpty()) {
						gestionarFormulario.traducirEtiquetas(listLabel);
					}
					gestionarConexion.cerrarConexion();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos", "ERROR",
							JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}

			// ------- CERRAR
			// ----------------------------------------------------------------------------------------------------
			@Override
			public void windowClosing(WindowEvent e) {
				gestionarConexion.cerrarConexion();
			}
		});

		// ----- BOTONES
		// --------------------------------------------------------------------------------------------------------
		// ------- RATÓN ENTRA
		// Cuando el ratón entra al area del botón "Ver" se activará si hay una película
		// seleccionada
		// ----------------------------------------------------------------------------------------------------
		btnVer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (tbActores.getSelectedColumn() != -1) {
					btnVer.setEnabled(true);
				} else {
					btnVer.setEnabled(false);
				}
			}
		});

		tbActores.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					gestionarConexion.conectar();
					actor a = gestionarPeliculas
							.obtenerActor(String.valueOf(tbActores.getValueAt(tbActores.getSelectedRow(), 0)));
					ActorViewer.main(null, a);
					gestionarConexion.cerrarConexion();
				}
			}
		});

		// ------- FILTRAR TABLA
		// Botón encargado de filtrar las peliculas de la tabla cuyo titulo comienze
		// como el texto indicado por el usuario
		// ----------------------------------------------------------------------------------------------------
		btnFiltro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarTablaFiltrada(txtFiltro.getText());
			}
		});

		// ------- VER DATOS DE LA PELICULA
		// Botón que abrira un dialogo emergente con información de la pelicula
		// seleccionada en la tabla
		// para poder editar la misma
		// ----------------------------------------------------------------------------------------------------
		btnVer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tbActores.getSelectedRow() != -1) {
					actor a = gestionarPeliculas
							.obtenerActor(String.valueOf(tbActores.getValueAt(tbActores.getSelectedRow(), 0)));
					ActorViewer.main(null, a);
				}
			}
		});
	}

	// ----- METODOS
	// --------------------------------------------------------------------------------------------------------
	// ------- FILTRAR LA TABLA
	// --------------------------------------------------------------------------------------------------------
	/**
	 * Este metodo es el que gestiona el filtrado de la tabla, aqui el usuario le
	 * pasa el indice del noombre del actor y todos los que empiezen asi se
	 * mostraran en la tabla
	 * 
	 * @param nombreActor
	 */
	public static void cargarTablaFiltrada(String nombreActor) {
		try {
			gestionarConexion.conectar();
			tbActores.removeAll();
			ArrayList<actor> listaActores = gestionarPeliculas.listarActoresFiltrar(nombreActor);
			tablaActores tablaModel = new tablaActores(listaActores);
			tablaModel.traducirTabla();
			tbActores.setModel(tablaModel);
			gestionarConexion.cerrarConexion();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Error al cargar la tabla", "ERROR", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
	}

	// ------- CARGAR LA TABLA
	// --------------------------------------------------------------------------------------------------------
	/**
	 * Este metodo es el que carga la tabla completa
	 */
	public static void cargarTabla() {
		try {
			gestionarConexion.conectar();
			tbActores.removeAll();
			ArrayList<actor> listaActores = gestionarPeliculas.listarActores();
			tablaActores tablaModel = new tablaActores(listaActores);
			tablaModel.traducirTabla();
			tbActores.setModel(tablaModel);
			gestionarConexion.cerrarConexion();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Error al cargar la tabla", "ERROR", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
	}

}
