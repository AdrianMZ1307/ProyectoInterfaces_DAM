package view;

import controller.*;
import model.pelicula;
import model.tablaPeliculas;

import java.awt.*;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class WelcomeMenu {

	private JFrame frmAdrianmzTrabajo;
	public static Locale language;
	private static Properties properties = new Properties();
	private static File file;
	private static ArrayList<JMenu> listMenu = new ArrayList<>();
	private static ArrayList<JLabel> listLabel = new ArrayList<>();
	private static ArrayList<JMenuItem> listMenuItem = new ArrayList<>();
	private static ArrayList<JButton> listButtons = new ArrayList<>();

	private HelpSet helpset = null;
	private HelpBroker browser = null;

	private static JMenuBar mnBar;
	private static JMenu mnFile, mnHelp, mnIdiomas;
	private static JMenuItem mnItConnection, mnItInformes, mnItGetHelp, mnItExit, mnItSpanish, mnIGallician,
			mnItEnglish;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomeMenu window = new WelcomeMenu();
					window.frmAdrianmzTrabajo.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WelcomeMenu() {
		initialize();
		try {
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
				browser.enableHelpOnButton(mnItGetHelp, "principal", helpset);
				browser.enableHelpKey(frmAdrianmzTrabajo.getContentPane(), "principal", helpset);
				break;
			case "en":
				helpURL = this.getClass().getResource("/help/help_en_US.hs");
				helpset = new HelpSet(null, helpURL);
				browser = helpset.createHelpBroker();
				browser.enableHelpOnButton(mnItGetHelp, "principal", helpset);
				browser.enableHelpKey(frmAdrianmzTrabajo.getContentPane(), "principal", helpset);
				break;
			case "gl":
				helpURL = this.getClass().getResource("/help/help_gl_ES.hs");
				helpset = new HelpSet(null, helpURL);
				browser = helpset.createHelpBroker();
				browser.enableHelpOnButton(mnItGetHelp, "principal", helpset);
				browser.enableHelpKey(frmAdrianmzTrabajo.getContentPane(), "principal", helpset);
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
		frmAdrianmzTrabajo = new JFrame();
		frmAdrianmzTrabajo.setTitle("AdrianMZ - Trabajo Final - Desarrollo de Interfaces");
		frmAdrianmzTrabajo.setBounds(100, 100, 450, 216);
		frmAdrianmzTrabajo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAdrianmzTrabajo.setMinimumSize(new Dimension(450, 215));

		JLabel lblWelcome = new JLabel("WELCOME");
		lblWelcome.setIcon(new ImageIcon(WelcomeMenu.class.getResource("/Icons/films.png")));
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(new Font("Times New Roman", Font.BOLD, 43));
		lblWelcome.setName("lblWelcome");

		JButton btnConnect = new JButton("CONNECT TO BD");
		ImageIcon imgConnection = new ImageIcon(WelcomeMenu.class.getResource("/Icons/connect_database.png"));
		Image image = imgConnection.getImage();
		Image newimg = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
		imgConnection = new ImageIcon(newimg);
		btnConnect.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		btnConnect.setIcon(imgConnection);
		btnConnect.setName("btnConnect");

		JLabel lblConnecting = new JLabel("");

		GroupLayout groupLayout = new GroupLayout(frmAdrianmzTrabajo.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblWelcome, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup().addComponent(btnConnect)
								.addPreferredGap(ComponentPlacement.RELATED, 160, Short.MAX_VALUE).addComponent(
										lblConnecting, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap().addComponent(lblWelcome)
				.addPreferredGap(ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup().addComponent(btnConnect).addGap(20))
						.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblConnecting, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
								.addContainerGap()))));
		frmAdrianmzTrabajo.getContentPane().setLayout(groupLayout);

		mnBar = new JMenuBar();
		frmAdrianmzTrabajo.setJMenuBar(mnBar);
		mnFile = new JMenu("FILE");
		mnFile.setIcon(new ImageIcon(this.getClass().getResource("/Icons/file.png")));
		mnBar.add(mnFile);

		imgConnection = new ImageIcon(this.getClass().getResource("/Icons/connection.png"));
		image = imgConnection.getImage();
		newimg = image.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
		imgConnection = new ImageIcon(newimg);

		mnItConnection = new JMenuItem("CONNECTION");
		mnItConnection.setIcon(imgConnection);
		mnFile.add(mnItConnection);

		imgConnection = new ImageIcon(this.getClass().getResource("/Icons/exit.png"));
		image = imgConnection.getImage();
		newimg = image.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
		imgConnection = new ImageIcon(newimg);

		mnItInformes = new JMenuItem("INFORMES");
		mnItInformes.setIcon(imgConnection);
		mnFile.add(mnItInformes);

		mnItExit = new JMenuItem("EXIT");
		mnItExit.setIcon(imgConnection);
		mnFile.add(mnItExit);

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

		listButtons.add(btnConnect);
		listLabel.add(lblWelcome);
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

		frmAdrianmzTrabajo.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				gestionarFormulario.traducirMenu(listMenu, listMenuItem);
				gestionarFormulario.traducirBotones(listButtons);
				gestionarFormulario.traducirEtiquetas(listLabel);
			}
		});
		mnItConnection.addActionListener(new ActionListener() {
			// METODO PARA CERRAR EL PROGRAMA
			public void actionPerformed(ActionEvent e) {
				ConnectionMenu menu = new ConnectionMenu();
				menu.show();
			}
		});
		mnItInformes.addActionListener(new ActionListener() {
			// METODO PARA CERRAR EL PROGRAMA
			public void actionPerformed(ActionEvent e) {
				InformesView.main(null);
			}
		});
		mnItExit.addActionListener(new ActionListener() {
			// METODO PARA CERRAR EL PROGRAMA
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnItSpanish.addActionListener(new ActionListener() {
			// METODO PARA CERRAR EL PROGRAMA
			public void actionPerformed(ActionEvent e) {
				try {
					File file = new File(this.getClass().getResource("/data/default.properties").getPath());
					properties.setProperty("LANG", String.valueOf("es_ES"));
					FileOutputStream fileOS = new FileOutputStream(file);
					properties.store(fileOS, null);
					fileOS.close();
					Locale.setDefault(new Locale("es_ES"));
					gestionarFormulario.traducirMenu(listMenu, listMenuItem);
					gestionarFormulario.traducirBotones(listButtons);
					gestionarFormulario.traducirEtiquetas(listLabel);

					URL helpURL = this.getClass().getResource("/help/help.hs");

					helpset = new HelpSet(null, helpURL);
					browser = helpset.createHelpBroker();
					browser.enableHelpOnButton(mnItGetHelp, "principal", helpset);
					browser.enableHelpKey(frmAdrianmzTrabajo.getContentPane(), "principal", helpset);

				} catch (IOException | HelpSetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnItEnglish.addActionListener(new ActionListener() {
			// METODO PARA CERRAR EL PROGRAMA
			public void actionPerformed(ActionEvent e) {
				try {
					File file = new File(this.getClass().getResource("/data/default.properties").getPath());
					properties.setProperty("LANG", String.valueOf("en_US"));
					FileOutputStream fileOS = new FileOutputStream(file);
					properties.store(fileOS, null);
					properties.store(fileOS, null);
					fileOS.close();
					Locale.setDefault(new Locale("en_US"));

					gestionarFormulario.traducirMenu(listMenu, listMenuItem);
					gestionarFormulario.traducirBotones(listButtons);
					gestionarFormulario.traducirEtiquetas(listLabel);

					URL helpURL = this.getClass().getResource("/help/help_en_US.hs");

					helpset = new HelpSet(null, helpURL);
					browser = helpset.createHelpBroker();
					browser.enableHelpOnButton(mnItGetHelp, "principal", helpset);
					browser.enableHelpKey(frmAdrianmzTrabajo.getContentPane(), "principal", helpset);
				} catch (IOException | HelpSetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		mnIGallician.addActionListener(new ActionListener() {
			// METODO PARA CERRAR EL PROGRAMA
			public void actionPerformed(ActionEvent e) {
				try {
					File file = new File(this.getClass().getResource("/data/default.properties").getPath());
					properties.setProperty("LANG", String.valueOf("gl_ES"));
					FileOutputStream fileOS = new FileOutputStream(file);
					properties.store(fileOS, null);
					fileOS.close();
					Locale.setDefault(new Locale("gl_ES"));

					gestionarFormulario.traducirMenu(listMenu, listMenuItem);
					gestionarFormulario.traducirBotones(listButtons);
					gestionarFormulario.traducirEtiquetas(listLabel);

					URL helpURL = this.getClass().getResource("/help/help_gl_ES.hs");

					helpset = new HelpSet(null, helpURL);
					browser = helpset.createHelpBroker();
					browser.enableHelpOnButton(mnItGetHelp, "principal", helpset);
					browser.enableHelpKey(frmAdrianmzTrabajo.getContentPane(), "principal", helpset);
				} catch (IOException | HelpSetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		// METODOS DEL MENU DE BIENVENIDA
		btnConnect.addActionListener(new ActionListener() {
			// METODO PARA CONECTAR CON LA BASE DE DATOS
			public void actionPerformed(ActionEvent e) {
				gestionarConexion.conectar();
				if (gestionarConexion.getConexion() != null) {
					gestionarConexion.cerrarConexion();
					FilmTableMenu.main(null);
					frmAdrianmzTrabajo.dispose();
				}
			}
		});
	}
}
