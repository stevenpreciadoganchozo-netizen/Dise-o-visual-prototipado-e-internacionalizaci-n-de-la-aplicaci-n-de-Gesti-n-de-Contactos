package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import controlador.logica_ventana;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JProgressBar;
import java.awt.Color;
import java.util.Locale;
import java.util.ResourceBundle;

public class ventana extends JFrame {

	public JPanel contentPane; // Panel principal que contendrá todos los componentes de la interfaz.
	public JTextField txt_nombres; // Campo de texto para ingresar nombres.
	public JTextField txt_telefono; // Campo de texto para ingresar números de teléfono.
	public JTextField txt_email; // Campo de texto para ingresar direcciones de correo electrónico.
	public JTextField txt_buscar; // Campo de texto adicional.
	public JCheckBox chb_favorito; // Casilla de verificación para marcar un contacto como favorito.
	public JComboBox cmb_categoria; // Menú desplegable para seleccionar la categoría de contacto.
	public JComboBox cmb_idioma;
	public JButton btn_add; // Botón para agregar un nuevo contacto.
	public JLabel lbl_nombre;
	public JLabel lbl_telefono;
	public JLabel lbl_email;
	public JLabel lbl_buscar;
	public JButton btn_modificar; // Botón para modificar un contacto existente.
	public JButton btn_eliminar; // Botón para eliminar un contacto.
	public JList lst_contactos; // Lista para mostrar los contactos.
	public JScrollPane scrLista; // Panel de desplazamiento para la lista de contactos.
	public JTabbedPane pestañas;

	public JPanel panelContactos;
	public JPanel panelEstadisticas;

	public JTable tabla;
	public DefaultTableModel modeloTabla;
	public JScrollPane scrollTabla;

	public JButton btn_exportar;

	public JProgressBar barra;
	private ResourceBundle mensajes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		 // Invoca el método invokeLater de la clase EventQueue para ejecutar la creación de la interfaz de usuario en un hilo de despacho de eventos (Event Dispatch Thread).
	    EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            try {
	                // Dentro de este método, se crea una instancia de la clase ventana, que es la ventana principal de la aplicación.
	                ventana frame = new ventana();
	                // Establece la visibilidad de la ventana como verdadera, lo que hace que la ventana sea visible para el usuario.
	                frame.setVisible(true);
	            } catch (Exception e) {
	                // En caso de que ocurra una excepción durante la creación o visualización de la ventana, se imprime la traza de la pila de la excepción.
	                e.printStackTrace();
	            }
	        }
	    });
	}

	/**
	 * Create the frame.
	 */
	public ventana() {
		setTitle("GESTION DE CONTACTOS"); // Establece el título de la ventana.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define el comportamiento al cerrar la ventana.
		setResizable(false); // Evita que la ventana sea redimensionable.
		setBounds(100, 100, 1026, 748); // Establece el tamaño y la posición inicial de la ventana.
		contentPane = new JPanel(); // Crea un nuevo panel de contenido.
		contentPane.setBackground(
		        new Color(244,246,249)
		);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // Establece un borde vacío alrededor del panel.

		setContentPane(contentPane); // Establece el panel de contenido como el panel principal de la ventana.
		contentPane.setLayout(null); // Configura el diseño del panel como nulo para posicionar manualmente los componentes.
		pestañas = new JTabbedPane();
		pestañas.setBounds(0, 0, 1010, 709);

		panelContactos = new JPanel();
		panelContactos.setLayout(null);

		panelEstadisticas = new JPanel();
		panelEstadisticas.setLayout(null);

		pestañas.addTab("Contactos", panelContactos);
		pestañas.addTab("Estadísticas", panelEstadisticas);

		contentPane.add(pestañas);
		
		// Creación y configuración de etiquetas para los campos de entrada.
		lbl_nombre = new JLabel("NOMBRES:"); // Etiqueta para nombres.
		lbl_nombre.setBounds(25, 41, 89, 13); // Define la posición y tamaño de la etiqueta.
		lbl_nombre.setFont(new Font("Tahoma", Font.BOLD, 15)); // Configura la fuente de la etiqueta.
		panelContactos.add(lbl_nombre); // Agrega la etiqueta al panel de contenido.
		
		lbl_telefono = new JLabel("TELEFONO:");
		lbl_telefono.setBounds(25, 80, 89, 13);
		lbl_telefono.setFont(new Font("Tahoma", Font.BOLD, 15));
		panelContactos.add(lbl_telefono);
		
		lbl_email = new JLabel("EMAIL:");
		lbl_email.setBounds(25, 122, 89, 13);
		lbl_email.setFont(new Font("Tahoma", Font.BOLD, 15));
		panelContactos.add(lbl_email);
		
		lbl_buscar = new JLabel("BUSCAR POR NOMBRE:");
		lbl_buscar.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbl_buscar.setBounds(25, 570, 192, 13);
		panelContactos.add(lbl_buscar);
		
		// Creación y configuración de campos de texto para ingresar nombres, teléfonos y correos electrónicos.
		txt_nombres = new JTextField(); // Campo de texto para nombres.
		txt_nombres.setBounds(124, 28, 427, 31); // Define la posición y tamaño del campo de texto.
		txt_nombres.setFont(new Font("Tahoma", Font.PLAIN, 15)); // Configura la fuente del campo de texto.
		panelContactos.add(txt_nombres); // Agrega el campo de texto al panel de contenido.
		txt_nombres.setColumns(10); // Establece el número de columnas para el campo de texto.
		
		txt_telefono = new JTextField();
		txt_telefono.setBounds(124, 69, 427, 31);
		txt_telefono.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txt_telefono.setColumns(10);
		panelContactos.add(txt_telefono);
		
		txt_email = new JTextField();
		txt_email.setBounds(124, 110, 427, 31);
		txt_email.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txt_email.setColumns(10);
		panelContactos.add(txt_email);
		
		txt_buscar = new JTextField();
		txt_buscar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txt_buscar.setColumns(10);
		txt_buscar.setBounds(212, 560, 784, 31);
		panelContactos.add(txt_buscar);
		
		// Creación y configuración de una casilla de verificación para indicar si un contacto es favorito.
		chb_favorito = new JCheckBox("CONTACTO FAVORITO"); // Casilla de verificación.
		chb_favorito.setBounds(24, 170, 193, 21); // Define la posición y tamaño de la casilla de verificación.
		chb_favorito.setFont(new Font("Tahoma", Font.PLAIN, 15)); // Configura la fuente de la casilla de verificación.
		panelContactos.add(chb_favorito); // Agrega la casilla de verificación al panel de contenido.

		
		cmb_categoria = new JComboBox(); // Crea un nuevo JComboBox para permitir la selección de categorías.
		cmb_categoria.setBounds(300, 167, 251, 31); // Establece la posición y el tamaño del JComboBox en el panel.
		panelContactos.add(cmb_categoria); // Agrega el JComboBox al panel de contenido.

		// Arreglo que contiene las categorías disponibles.
		String[] categorias = {"Elija una Categoria", "Familia", "Amigos", "Trabajo"};
		for (String categoria : categorias) {
		    // Agrega cada categoría al JComboBox.
		    cmb_categoria.addItem(categoria);
		}
		cmb_idioma = new JComboBox();

		cmb_idioma.setBounds(820, 20, 150, 30);

		panelContactos.add(cmb_idioma);

		cmb_idioma.addItem("ES");
		cmb_idioma.addItem("EN");
		cmb_idioma.addItem("FR");
		
		cmb_idioma.addActionListener(e -> {

		    cambiarIdioma(
		            cmb_idioma
		            .getSelectedItem()
		            .toString()
		    );
		});

		btn_add = new JButton("AGREGAR"); // Crea un nuevo botón con el texto "AGREGAR".
		btn_add.setBackground(
		        new Color(37,99,235)
		);

		btn_add.setForeground(Color.WHITE);
		btn_add.setFont(new Font("Tahoma", Font.PLAIN, 15)); // Configura la fuente del botón.
		btn_add.setBounds(601, 70, 125, 65); // Establece la posición y el tamaño del botón en el panel.
		panelContactos.add(btn_add); // Agrega el botón al panel de contenido.
		
		btn_modificar = new JButton("MODIFICAR");
		btn_modificar.setBackground(
		        new Color(37,99,235)
		);

		btn_modificar.setForeground(Color.WHITE);
		btn_modificar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_modificar.setBounds(736, 70, 125, 65);
		panelContactos.add(btn_modificar);
		
		btn_eliminar = new JButton("ELIMINAR");
		btn_eliminar.setBackground(
		        new Color(220,38,38)
		);

		btn_eliminar.setForeground(Color.WHITE);
		btn_eliminar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_eliminar.setBounds(871, 69, 125, 65);
		panelContactos.add(btn_eliminar);
		btn_exportar = new JButton("EXPORTAR CSV");
		btn_exportar.setBackground(
		        new Color(37,99,235)
		);

		btn_exportar.setForeground(Color.WHITE);
		btn_exportar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_exportar.setBounds(601, 150, 395, 40);

		panelContactos.add(btn_exportar);
		barra = new JProgressBar();

		barra.setBounds(25, 610, 971, 25);

		barra.setMinimum(0);

		barra.setMaximum(100);

		panelContactos.add(barra);
		
		String columnas[] = {
			    "Nombre",
			    "Telefono",
			    "Email",
			    "Categoria",
			    "Favorito"
			};

			modeloTabla = new DefaultTableModel(columnas,0);

			tabla = new JTable(modeloTabla);
			tabla.setRowHeight(30);

			tabla.setFont(
			        new Font("Tahoma", Font.PLAIN, 14)
			);

			scrollTabla = new JScrollPane(tabla);

			scrollTabla.setBounds(25, 242, 971, 300);

			panelContactos.add(scrollTabla);
			JLabel lblStats = new JLabel("ESTADISTICAS DE CONTACTOS");
			lblStats.setFont(new Font("Tahoma", Font.BOLD, 25));
			lblStats.setBounds(200, 100, 500, 50);

			panelEstadisticas.add(lblStats);
			JLabel lblInfo = new JLabel("Total de contactos registrados");

			lblInfo.setFont(new Font("Tahoma", Font.PLAIN, 20));

			lblInfo.setBounds(200, 250, 500, 50);

			panelEstadisticas.add(lblInfo);
			
			cambiarIdioma("ES");
		//Instanciar el controlador para usar el delegado
		logica_ventana lv=new logica_ventana(this);
	}
	public void cambiarIdioma(String idioma) {

	    Locale locale;

	    switch(idioma) {

	        case "EN":

	            locale = new Locale("en","US");

	            break;

	        case "FR":

	            locale = new Locale("fr","FR");

	            break;

	        default:

	            locale = new Locale("es","ES");
	    }

	    mensajes = ResourceBundle.getBundle(
	            "idiomas.mensajes",
	            locale
	    );

	    setTitle(
	            mensajes.getString("titulo")
	    );

	    btn_add.setText(
	            mensajes.getString("agregar")
	    );

	    btn_modificar.setText(
	            mensajes.getString("modificar")
	    );

	    btn_eliminar.setText(
	            mensajes.getString("eliminar")
	    );
	    lbl_nombre.setText(
	            mensajes.getString("nombre")
	    );

	    lbl_telefono.setText(
	            mensajes.getString("telefono")
	    );

	    lbl_email.setText(
	            mensajes.getString("email")
	    );

	    lbl_buscar.setText(
	            mensajes.getString("buscarLabel")
	    );
	    tabla.getColumnModel().getColumn(0)
	    .setHeaderValue(
	            mensajes.getString("colNombre")
	    );

	    tabla.getColumnModel().getColumn(1)
	    .setHeaderValue(
	            mensajes.getString("colTelefono")
	    );

	    tabla.getColumnModel().getColumn(2)
	    .setHeaderValue(
	            mensajes.getString("colEmail")
	    );

	    tabla.getColumnModel().getColumn(3)
	    .setHeaderValue(
	            mensajes.getString("colCategoria")
	    );

	    tabla.getColumnModel().getColumn(4)
	    .setHeaderValue(
	            mensajes.getString("colFavorito")
	    );

	    tabla.getTableHeader().repaint();
	    chb_favorito.setText(
	            mensajes.getString("favoritoLabel")
	    );
	    cmb_categoria.removeAllItems();

	    cmb_categoria.addItem(
	            mensajes.getString("cat0")
	    );

	    cmb_categoria.addItem(
	            mensajes.getString("cat1")
	    );

	    cmb_categoria.addItem(
	            mensajes.getString("cat2")
	    );

	    cmb_categoria.addItem(
	            mensajes.getString("cat3")
	    );
	}
}
