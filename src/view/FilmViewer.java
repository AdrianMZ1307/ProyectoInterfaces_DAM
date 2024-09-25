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

public class FilmViewer extends JDialog {

	private static pelicula p;
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
	private JTextField txtTituloPelicula;
	private JLabel lblFechaEstreno;
	private JSpinner spFechaEstreno;
	private JLabel lblSinopsis;

	private HelpSet helpset = null;
	private HelpBroker browser = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, pelicula pelicula) {
		try {
			p = pelicula;
			FilmViewer dialog = new FilmViewer();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FilmViewer() {
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
		setTitle("EDITANDO: " + p.getTitulo());
		setBounds(100, 100, 550, 350);
		getContentPane().setLayout(new BorderLayout());
		frmFilmViewer.setBorder(new EmptyBorder(5, 5, 5, 5));
		frmFilmViewer.setMinimumSize(new Dimension(600, 350));
		getContentPane().add(frmFilmViewer, BorderLayout.CENTER);
		JLabel lblPortada = new JLabel("");

		txtTituloPelicula = new JTextField();
		txtTituloPelicula.setEditable(false);
		txtTituloPelicula.setColumns(10);

		JSpinner spPuntuacion = new JSpinner();
		spPuntuacion.setModel(new SpinnerNumberModel(new Float(0), new Float(0), new Float(10), new Float(1)));

		JSpinner spDuracion = new JSpinner();
		spDuracion.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));

		JLabel lblDuracion = new JLabel("Duracion (minutos)");

		JLabel lblPuntuacion = new JLabel("Puntuacion (0/10)");

		JLabel lblTituloPelicula = new JLabel("Titulo de la Pelicula");

		lblFechaEstreno = new JLabel("Fecha de Estreno");

		spFechaEstreno = new JSpinner();
		spFechaEstreno.setEnabled(false);
		spFechaEstreno.setModel(new SpinnerDateModel(new Date(1647558000000L), null, null, Calendar.DAY_OF_YEAR));

		lblSinopsis = new JLabel("Sinopsis");

		JTextArea txtArea = new JTextArea();
		txtArea.setBackground(Color.LIGHT_GRAY);
		txtArea.setLineWrap(true);
		GroupLayout gl_frmFilmViewer = new GroupLayout(frmFilmViewer);
		gl_frmFilmViewer.setHorizontalGroup(gl_frmFilmViewer.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_frmFilmViewer.createSequentialGroup().addContainerGap()
						.addComponent(lblPortada, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE).addGap(18)
						.addGroup(gl_frmFilmViewer.createParallelGroup(Alignment.LEADING)
								.addComponent(lblSinopsis, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_frmFilmViewer.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblPuntuacion, GroupLayout.PREFERRED_SIZE, 124,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblTituloPelicula, GroupLayout.PREFERRED_SIZE, 124,
												GroupLayout.PREFERRED_SIZE))
								.addComponent(lblDuracion, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblFechaEstreno, GroupLayout.PREFERRED_SIZE, 127,
										GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_frmFilmViewer.createParallelGroup(Alignment.LEADING).addComponent(txtArea)
								.addComponent(txtTituloPelicula, GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
								.addComponent(spPuntuacion, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 313,
										Short.MAX_VALUE)
								.addComponent(spDuracion, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 313,
										Short.MAX_VALUE)
								.addComponent(spFechaEstreno, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 313,
										Short.MAX_VALUE))
						.addContainerGap()));
		gl_frmFilmViewer.setVerticalGroup(gl_frmFilmViewer.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_frmFilmViewer.createSequentialGroup().addContainerGap()
						.addGroup(gl_frmFilmViewer.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_frmFilmViewer.createSequentialGroup()
										.addGroup(gl_frmFilmViewer.createParallelGroup(Alignment.BASELINE)
												.addComponent(txtTituloPelicula, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblTituloPelicula))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(gl_frmFilmViewer.createParallelGroup(Alignment.BASELINE)
												.addComponent(spPuntuacion, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblPuntuacion))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(gl_frmFilmViewer.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblDuracion).addComponent(spDuracion,
														GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(gl_frmFilmViewer.createParallelGroup(Alignment.BASELINE)
												.addComponent(spFechaEstreno, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblFechaEstreno))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_frmFilmViewer.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblSinopsis)
												.addComponent(txtArea, GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE))
										.addGap(33))
								.addGroup(gl_frmFilmViewer.createSequentialGroup()
										.addComponent(lblPortada, GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
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
			int blobLength = (int) p.getPortada().length();
			byte[] blobAsBytes;
			blobAsBytes = p.getPortada().getBytes(1, blobLength);
			BufferedImage bufferedImage;
			bufferedImage = ImageIO.read(new ByteArrayInputStream(blobAsBytes));
			ImageIcon img = new ImageIcon(bufferedImage);
			Image image = img.getImage();
			Image newimg = image.getScaledInstance(170, 180, java.awt.Image.SCALE_DEFAULT);
			ImageIcon icon = new ImageIcon(newimg);
			lblPortada.setIcon(icon);

			txtTituloPelicula.setText(p.getTitulo());
			spPuntuacion.setValue(p.getPuntuacion());
			spDuracion.setValue(p.getDuracion());
			spFechaEstreno.setValue(p.getFechaEstreno());
			txtArea.setText(p.getSinopsis());

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

		lblTituloPelicula.setName("lblFiltro");
		lblPuntuacion.setName("lblPuntuacion");
		lblDuracion.setName("lblDuracion");
		lblSinopsis.setName("lblSinopsis");
		lblFechaEstreno.setName("colFecha");

		btnCancelar.setName("btnCancelar");
		btnGuardar.setName("btnGuardar");

		listLabel.add(lblTituloPelicula);
		listLabel.add(lblPuntuacion);
		listLabel.add(lblDuracion);
		listLabel.add(lblSinopsis);
		listLabel.add(lblFechaEstreno);

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
					pelicula peli = new pelicula(txtTituloPelicula.getText(), txtArea.getText(),
							Float.valueOf(String.valueOf(spPuntuacion.getValue())),
							Integer.parseInt(String.valueOf(spDuracion.getValue())), p.getFechaEstreno(),
							p.getPortada());
					gestionarPeliculas.modificarPelicula(peli);
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
