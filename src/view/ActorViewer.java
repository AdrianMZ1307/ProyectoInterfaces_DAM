package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.gestionarConexion;
import controller.gestionarFormulario;
import controller.gestionarPeliculas;
import model.actor;
import model.pelicula;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Calendar;
import javax.swing.JTextArea;
import java.awt.Color;

public class ActorViewer extends JDialog {

	private static actor a;
	private final JPanel frmFilmViewer = new JPanel();
	public static Locale language;
	private static Properties properties = new Properties();
	private static File file;
	private static ArrayList<JMenu> listMenu = new ArrayList<>();
	private static ArrayList<JLabel> listLabel = new ArrayList<>();
	private static ArrayList<JMenuItem> listMenuItem = new ArrayList<>();
	private static ArrayList<JButton> listButtons = new ArrayList<>();
	private static JMenuBar mnBar;
	private static JMenu mnFile, mnHelp, mnIdiomas;
	private static JMenuItem mnItConnection, mnItInformes, mnItGetHelp, mnItExit, mnItSpanish, mnIGallician,
			mnItEnglish;
	private JTextField txtNombreActor;
	private JLabel lblFechaNacimiento;
	private JSpinner spFechaNacimiento;

	private HelpSet helpset = null;
	private HelpBroker browser = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, actor actor) {
		try {
			a = actor;
			ActorViewer dialog = new ActorViewer();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ActorViewer() {
		setModal(true);

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
				browser.enableHelpKey(getContentPane(), "ver_datos", helpset);
				break;
			case "en":
				helpURL = this.getClass().getResource("/help/help_en_US.hs");
				helpset = new HelpSet(null, helpURL);
				browser = helpset.createHelpBroker();
				browser.enableHelpKey(getContentPane(), "ver_datos", helpset);
				break;
			case "gl":
				helpURL = this.getClass().getResource("/help/help_gl_ES.hs");
				helpset = new HelpSet(null, helpURL);
				browser = helpset.createHelpBroker();
				browser.enableHelpKey(getContentPane(), "ver_datos", helpset);
				break;
			default:
				break;
			}

		} catch (IOException | HelpSetException ex) {
			ex.printStackTrace();
		}

		setTitle("EDITANDO A:" + a.getNombre());
		setBounds(100, 100, 550, 350);
		getContentPane().setLayout(new BorderLayout());
		frmFilmViewer.setBorder(new EmptyBorder(5, 5, 5, 5));
		frmFilmViewer.setMinimumSize(new Dimension(600, 350));
		getContentPane().add(frmFilmViewer, BorderLayout.CENTER);
		JLabel lblFoto = new JLabel("");

		txtNombreActor = new JTextField();
		txtNombreActor.setEditable(false);
		txtNombreActor.setColumns(10);

		JLabel lblNombreActor = new JLabel("Nombre del Actor");

		lblFechaNacimiento = new JLabel("Fecha de Nacimiento");

		spFechaNacimiento = new JSpinner();
		spFechaNacimiento.setEnabled(false);
		spFechaNacimiento.setModel(new SpinnerDateModel(new Date(1647558000000L), null, null, Calendar.DAY_OF_YEAR));
		GroupLayout gl_frmFilmViewer = new GroupLayout(frmFilmViewer);
		gl_frmFilmViewer.setHorizontalGroup(gl_frmFilmViewer.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_frmFilmViewer.createSequentialGroup().addContainerGap()
						.addComponent(lblFoto, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE).addGap(18)
						.addGroup(gl_frmFilmViewer.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_frmFilmViewer.createSequentialGroup()
										.addComponent(lblNombreActor, GroupLayout.PREFERRED_SIZE, 124,
												GroupLayout.PREFERRED_SIZE)
										.addGap(21)
										.addComponent(txtNombreActor, GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE))
								.addGroup(gl_frmFilmViewer.createSequentialGroup()
										.addComponent(lblFechaNacimiento, GroupLayout.PREFERRED_SIZE, 127,
												GroupLayout.PREFERRED_SIZE)
										.addGap(18).addComponent(spFechaNacimiento, GroupLayout.DEFAULT_SIZE, 313,
												Short.MAX_VALUE)))
						.addContainerGap()));
		gl_frmFilmViewer.setVerticalGroup(gl_frmFilmViewer.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_frmFilmViewer.createSequentialGroup().addContainerGap()
						.addGroup(gl_frmFilmViewer.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_frmFilmViewer.createSequentialGroup()
										.addGroup(gl_frmFilmViewer.createParallelGroup(Alignment.BASELINE)
												.addComponent(txtNombreActor, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblNombreActor))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(gl_frmFilmViewer.createParallelGroup(Alignment.BASELINE)
												.addComponent(spFechaNacimiento, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblFechaNacimiento))
										.addContainerGap())
								.addGroup(gl_frmFilmViewer.createSequentialGroup()
										.addComponent(lblFoto, GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
										.addGap(19)))));
		frmFilmViewer.setLayout(gl_frmFilmViewer);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setActionCommand("");
		buttonPane.add(btnGuardar);
		getRootPane().setDefaultButton(btnGuardar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setActionCommand("");
		buttonPane.add(btnCancelar);

		try {
			int blobLength = (int) a.getFoto().length();
			byte[] blobAsBytes;
			blobAsBytes = a.getFoto().getBytes(1, blobLength);
			BufferedImage bufferedImage;
			bufferedImage = ImageIO.read(new ByteArrayInputStream(blobAsBytes));
			ImageIcon img = new ImageIcon(bufferedImage);
			Image image = img.getImage();
			Image newimg = image.getScaledInstance(170, 180, java.awt.Image.SCALE_DEFAULT);
			ImageIcon icon = new ImageIcon(newimg);
			lblFoto.setIcon(icon);

			txtNombreActor.setText(a.getNombre());
			spFechaNacimiento.setValue(a.getFechaNacimiento());

		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mnBar = new JMenuBar();
		setJMenuBar(mnBar);
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

		lblNombreActor.setName("lblFiltro");
		lblFechaNacimiento.setName("colFecha");

		btnCancelar.setName("btnCancelar");
		btnGuardar.setName("btnGuardar");

		listLabel.add(lblNombreActor);
		listLabel.add(lblFechaNacimiento);

		listButtons.add(btnCancelar);
		listButtons.add(btnGuardar);

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

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				gestionarFormulario.traducirMenu(listMenu, listMenuItem);
				gestionarFormulario.traducirBotones(listButtons);
				gestionarFormulario.traducirEtiquetas(listLabel);
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
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
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					gestionarConexion.conectar();
					actor act = new actor(txtNombreActor.getText(), a.getFechaNacimiento(), a.getFoto());
					// gestionarPeliculas.modificarActor(act);
					gestionarConexion.getConexion().commit();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				gestionarConexion.cerrarConexion();
				dispose();
			}
		});
		mnItSpanish.addActionListener(new ActionListener() {
			// METODO PARA CERRAR EL PROGRAMA
			public void actionPerformed(ActionEvent e) {
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

					URL helpURL = this.getClass().getResource("/help/help.hs");

					helpset = new HelpSet(null, helpURL);
					browser = helpset.createHelpBroker();
					browser.enableHelpKey(getContentPane(), "ver_datos", helpset);
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
					File file = new File(gestionarConexion.class.getResource("/data/default.properties").getPath());
					properties.setProperty("LANG", String.valueOf("en_US"));
					FileOutputStream fileOS = new FileOutputStream(file);
					properties.store(fileOS, null);
					fileOS.close();
					Locale.setDefault(new Locale("en_US"));

					gestionarFormulario.traducirMenu(listMenu, listMenuItem);
					gestionarFormulario.traducirBotones(listButtons);
					gestionarFormulario.traducirEtiquetas(listLabel);

					URL helpURL = this.getClass().getResource("/help/help_en_US.hs");

					helpset = new HelpSet(null, helpURL);
					browser = helpset.createHelpBroker();
					browser.enableHelpKey(getContentPane(), "ver_datos", helpset);
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
					File file = new File(gestionarConexion.class.getResource("/data/default.properties").getPath());
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
					browser.enableHelpKey(getContentPane(), "ver_datos", helpset);
				} catch (IOException | HelpSetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

	}
}
