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

public class FilmTableMenu {

	private JFrame frmTablaPeliculas;
	private static JTable tbPeliculas;
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
	private static InputStream is;
	private static Properties properties = new Properties();
	private static File file;
	private static ArrayList<JMenu> listMenu = new ArrayList<>();
	private static ArrayList<JLabel> listLabel = new ArrayList<>();
	private static ArrayList<JMenuItem> listMenuItem = new ArrayList<>();
	private static ArrayList<JButton> listButtons = new ArrayList<>();
	private JButton btnActores;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FilmTableMenu window = new FilmTableMenu();
					window.frmTablaPeliculas.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FilmTableMenu() {
		initialize();

		try {
			File file = new File(gestionarConexion.class.getResource("/data/default.properties").getPath());
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("./data/default.properties");
			properties.load(is);
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
				browser.enableHelpKey(frmTablaPeliculas.getContentPane(), "tabla_peliculas", helpset);
				break;
			case "en":
				helpURL = this.getClass().getResource("/help/help_en_US.hs");
				helpset = new HelpSet(null, helpURL);
				browser = helpset.createHelpBroker();
				browser.enableHelpOnButton(mnItGetHelp, "tabla_peliculas", helpset);
				browser.enableHelpKey(frmTablaPeliculas.getContentPane(), "tabla_peliculas", helpset);
				break;
			case "gl":
				helpURL = this.getClass().getResource("/help/help_gl_ES.hs");
				helpset = new HelpSet(null, helpURL);
				browser = helpset.createHelpBroker();
				browser.enableHelpOnButton(mnItGetHelp, "tabla_peliculas", helpset);
				browser.enableHelpKey(frmTablaPeliculas.getContentPane(), "tabla_peliculas", helpset);
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
		frmTablaPeliculas = new JFrame();
		frmTablaPeliculas.setTitle("Tabla Peliculas - AdrianMZ");
		frmTablaPeliculas.setBounds(100, 100, 450, 300);
		frmTablaPeliculas.setMinimumSize(new Dimension(450, 300));
		frmTablaPeliculas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frmTablaPeliculas.getContentPane().setLayout(new BorderLayout(0, 0));
		Panel filterPane = new Panel();
		frmTablaPeliculas.getContentPane().add(filterPane, BorderLayout.NORTH);
		Panel filmPane = new Panel();
		frmTablaPeliculas.getContentPane().add(filmPane, BorderLayout.SOUTH);

		JScrollPane scPeliculas = new JScrollPane();
		scPeliculas.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frmTablaPeliculas.getContentPane().add(scPeliculas, BorderLayout.CENTER);

		tbPeliculas = new JTable();
		tbPeliculas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbPeliculas.setEnabled(true);
		scPeliculas.setViewportView(tbPeliculas);
		tbPeliculas.setAutoCreateRowSorter(true);
		filterPane.setLayout(new GridLayout(0, 3, 0, 0));

		JLabel lblFiltro = new JLabel();
		lblFiltro.setHorizontalAlignment(SwingConstants.CENTER);
		lblFiltro.setText("Titulo de la Pelicula");
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
		frmTablaPeliculas.setJMenuBar(mnBar);

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

		btnActores = new JButton("VER TODOS LOS ACTORES");
		filmPane.add(btnActores);
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
				try {
					gestionarConexion.conectar();
					File file = new File(this.getClass().getResource("/data/default.properties").getPath());
					properties.setProperty("LANG", String.valueOf("es_ES"));
					FileOutputStream fileOS = new FileOutputStream(file);
					properties.store(fileOS, null);
					fileOS.close();
					Locale.setDefault(new Locale("es_ES"));

					gestionarFormulario.traducirMenu(listMenu, listMenuItem);
					gestionarFormulario.traducirBotones(listButtons);
					gestionarFormulario.traducirEtiquetas(listLabel);
					if (tbPeliculas != null) {
						ArrayList<pelicula> listaPeliculas = gestionarPeliculas.listarPeliculas();
						tablaPeliculas tablaModel = new tablaPeliculas(listaPeliculas);
						tablaModel.traducirTabla();
						tbPeliculas.setModel(tablaModel);
					}

					URL helpURL = this.getClass().getResource("/help/help.hs");

					helpset = new HelpSet(null, helpURL);
					browser = helpset.createHelpBroker();
					browser.enableHelpOnButton(mnItGetHelp, "tabla_peliculas", helpset);
					browser.enableHelpKey(frmTablaPeliculas.getContentPane(), "tabla_peliculas", helpset);
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
				try {
					gestionarConexion.conectar();
					File file = new File(this.getClass().getResource("/data/default.properties").getPath());
					properties.setProperty("LANG", String.valueOf("en_US"));
					FileOutputStream fileOS = new FileOutputStream(file);
					properties.store(fileOS, null);
					fileOS.close();
					Locale.setDefault(new Locale("en_US"));

					gestionarFormulario.traducirMenu(listMenu, listMenuItem);
					gestionarFormulario.traducirBotones(listButtons);
					gestionarFormulario.traducirEtiquetas(listLabel);
					if (tbPeliculas != null) {
						ArrayList<pelicula> listaPeliculas = gestionarPeliculas.listarPeliculas();
						tablaPeliculas tablaModel = new tablaPeliculas(listaPeliculas);
						tablaModel.traducirTabla();
						tbPeliculas.setModel(tablaModel);
					}					

					URL helpURL = this.getClass().getResource("/help/help_en_US.hs");

					helpset = new HelpSet(null, helpURL);
					browser = helpset.createHelpBroker();
					browser.enableHelpOnButton(mnItGetHelp, "tabla_peliculas", helpset);
					browser.enableHelpKey(frmTablaPeliculas.getContentPane(), "tabla_peliculas", helpset);
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
				try {
					gestionarConexion.conectar();
					File file = new File(this.getClass().getResource("/data/default.properties").getPath());
					properties.setProperty("LANG", String.valueOf("gl_ES"));
					FileOutputStream fileOS = new FileOutputStream(file);
					properties.store(fileOS, null);
					fileOS.close();
					Locale.setDefault(new Locale("gl_ES"));

					gestionarFormulario.traducirMenu(listMenu, listMenuItem);
					gestionarFormulario.traducirBotones(listButtons);
					gestionarFormulario.traducirEtiquetas(listLabel);
					if (tbPeliculas != null) {
						ArrayList<pelicula> listaPeliculas = gestionarPeliculas.listarPeliculas();
						tablaPeliculas tablaModel = new tablaPeliculas(listaPeliculas);
						tablaModel.traducirTabla();
						tbPeliculas.setModel(tablaModel);
					}

					URL helpURL = this.getClass().getResource("/help/help_gl_ES.hs");

					helpset = new HelpSet(null, helpURL);
					browser = helpset.createHelpBroker();
					browser.enableHelpOnButton(mnItGetHelp, "tabla_peliculas", helpset);
					browser.enableHelpKey(frmTablaPeliculas.getContentPane(), "tabla_peliculas", helpset);
				} catch (IOException | HelpSetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				gestionarConexion.cerrarConexion();
			}
		});

		btnActores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ActorTableMenu actorMenu = new ActorTableMenu();
				actorMenu.main(null);
			}
		});
		// ----- VENTANA
		// --------------------------------------------------------------------------------------------------------
		frmTablaPeliculas.addWindowListener(new WindowAdapter() {
			// ------- ABRIR
			// ----------------------------------------------------------------------------------------------------
			@Override
			public void windowOpened(WindowEvent e) {
				try {
					File file = new File(this.getClass().getResource("/data/default.properties").getPath());
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
				if (tbPeliculas.getSelectedColumn() != -1) {
					btnVer.setEnabled(true);
				} else {
					btnVer.setEnabled(false);
				}
			}
		});

		tbPeliculas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					gestionarConexion.conectar();
					pelicula p = gestionarPeliculas
							.obtenerPelicula(String.valueOf(tbPeliculas.getValueAt(tbPeliculas.getSelectedRow(), 0)));
					FilmViewer.main(null, p);
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
				if (tbPeliculas.getSelectedRow() != -1) {
					pelicula p = gestionarPeliculas
							.obtenerPelicula(String.valueOf(tbPeliculas.getValueAt(tbPeliculas.getSelectedRow(), 0)));
					FilmViewer.main(null, p);
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
	 * pasa el indice del titulo de la pelicula y todas las que empiezen asi se
	 * mostraran en la tabla
	 * 
	 * @param tituloPelicula
	 */
	public static void cargarTablaFiltrada(String tituloPelicula) {
		try {
			gestionarConexion.conectar();
			tbPeliculas.removeAll();
			ArrayList<pelicula> listaPeliculas = gestionarPeliculas.listarPeliculasFiltrar(tituloPelicula);
			tablaPeliculas tablaModel = new tablaPeliculas(listaPeliculas);
			tablaModel.traducirTabla();
			tbPeliculas.setModel(tablaModel);
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
			tbPeliculas.removeAll();
			ArrayList<pelicula> listaPeliculas = gestionarPeliculas.listarPeliculas();
			tablaPeliculas tablaModel = new tablaPeliculas(listaPeliculas);
			tablaModel.traducirTabla();
			tbPeliculas.setModel(tablaModel);
			gestionarConexion.cerrarConexion();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Error al cargar la tabla", "ERROR", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
	}

}
