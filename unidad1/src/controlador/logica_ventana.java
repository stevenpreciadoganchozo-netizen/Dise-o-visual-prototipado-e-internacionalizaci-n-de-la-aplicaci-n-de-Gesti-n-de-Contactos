package controlador;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import vista.ventana;
import modelo.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.io.FileWriter;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//Definición de la clase logica_ventana que implementa tres interfaces para manejar eventos.
public class logica_ventana implements ActionListener, ItemListener {
	private ventana delegado; // Referencia a la ventana principal que contiene la GUI.
	private String nombres, email, telefono, categoria=""; // Variables para almacenar datos del contacto.
	private persona persona; // Objeto de tipo persona, que representa un contacto.
	private List<persona> contactos; // Lista de objetos persona que representa todos los contactos.
	private boolean favorito = false; // Booleano que indica si un contacto es favorito.

	// Constructor que inicializa la clase y configura los escuchadores de eventos para los componentes de la GUI.
	public logica_ventana(ventana delegado) {
		  // Asigna la ventana recibida como parámetro a la variable de instancia delegado.
	    this.delegado = delegado;
	    // Carga los contactos almacenados al inicializar.
	    cargarContactosRegistrados(); 
	    // Registra los ActionListener para los botones de la GUI.
	    this.delegado.btn_add.addActionListener(this);
	    this.delegado.btn_eliminar.addActionListener(this);
	    this.delegado.btn_modificar.addActionListener(this);
	    this.delegado.btn_exportar.addActionListener(this);
	    // Registra los ListSelectionListener para la lista de contactos.
	    
	    // Registra los ItemListener para el JComboBox de categoría y el JCheckBox de favoritos.
	    this.delegado.cmb_categoria.addItemListener(this);
	    this.delegado.chb_favorito.addItemListener(this);
	    TableRowSorter<DefaultTableModel> sorter =
	            new TableRowSorter<>(delegado.modeloTabla);

	    delegado.tabla.setRowSorter(sorter);

	    delegado.txt_buscar.addKeyListener(new KeyAdapter() {

	        public void keyReleased(KeyEvent e) {

	            sorter.setRowFilter(
	                    RowFilter.regexFilter(
	                            delegado.txt_buscar.getText()
	                    )
	            );
	        }
	    });
	    delegado.tabla.getSelectionModel()
	    .addListSelectionListener(e -> {

	        int fila = delegado.tabla.getSelectedRow();

	        if(fila != -1) {

	            cargarContacto(fila);
	        }
	    });
	    delegado.txt_nombres.addKeyListener(new KeyAdapter() {

	        public void keyPressed(KeyEvent e) {

	            if(e.getKeyCode() == KeyEvent.VK_ENTER) {

	                delegado.btn_add.doClick();
	            }
	        }
	    });
	    JPopupMenu menu = new JPopupMenu();

	    JMenuItem eliminar = new JMenuItem("Eliminar");

	    menu.add(eliminar);

	    delegado.tabla.addMouseListener(new MouseAdapter() {

	        public void mousePressed(MouseEvent e) {

	            if(e.isPopupTrigger()) {

	                menu.show(
	                        e.getComponent(),
	                        e.getX(),
	                        e.getY()
	                );
	            }
	        }

	        public void mouseReleased(MouseEvent e) {

	            if(e.isPopupTrigger()) {

	                menu.show(
	                        e.getComponent(),
	                        e.getX(),
	                        e.getY()
	                );
	            }
	        }
	    });
	}

	// Método privado para inicializar las variables con los valores ingresados en la GUI.
	private void incializacionCampos() {
		// Obtiene el texto ingresado en los campos de nombres, email y teléfono de la GUI.
		nombres = delegado.txt_nombres.getText();
		email = delegado.txt_email.getText();
		telefono = delegado.txt_telefono.getText();
	}

	// Método privado para cargar los contactos almacenados desde un archivo.
	private void cargarContactosRegistrados() {

	    try {

	        contactos = new personaDAO(new persona()).leerArchivo();

	        delegado.modeloTabla.setRowCount(0);

	        delegado.barra.setValue(0);

	        for (int i = 0; i < contactos.size(); i++) {

	            persona contacto = contactos.get(i);

	            delegado.modeloTabla.addRow(new Object[]{
	                    contacto.getNombre(),
	                    contacto.getTelefono(),
	                    contacto.getEmail(),
	                    contacto.getCategoria(),
	                    contacto.isFavorito()
	            });

	            int progreso = (i + 1) * 100 / contactos.size();

	            delegado.barra.setValue(progreso);
	        }

	    } catch (IOException e) {

	        JOptionPane.showMessageDialog(
	                delegado,
	                "Existen problemas al cargar los contactos"
	        );
	    }
	}

	// Método privado para limpiar los campos de entrada en la GUI y reiniciar variables.
	private void limpiarCampos() {
		// Limpia los campos de nombres, email y teléfono en la GUI.
	    delegado.txt_nombres.setText("");
	    delegado.txt_telefono.setText("");
	    delegado.txt_email.setText("");
	    // Reinicia las variables de categoría y favorito.
	    categoria = "";
	    favorito = false;
	    // Desmarca la casilla de favorito y establece la categoría por defecto.
	    delegado.chb_favorito.setSelected(favorito);
	    delegado.cmb_categoria.setSelectedIndex(0);
	    // Reinicia las variables con los valores actuales de la GUI.
	    incializacionCampos();
	    // Recarga los contactos en la lista de contactos de la GUI.
	    cargarContactosRegistrados();
	}

	// Método que maneja los eventos de acción (clic) en los botones.
	@Override
	public void actionPerformed(ActionEvent e) {
		incializacionCampos(); // Inicializa las variables con los valores actuales de la GUI.

	    // Verifica si el evento proviene del botón "Agregar".
	    if (e.getSource() == delegado.btn_add) {
	        // Verifica si los campos de nombres, teléfono y email no están vacíos.
	        if ((!nombres.equals("")) && (!telefono.equals("")) && (!email.equals(""))) {
	            // Verifica si se ha seleccionado una categoría válida.
	            if ((!categoria.equals("Elija una Categoria")) && (!categoria.equals(""))) {
	                // Crea un nuevo objeto persona con los datos ingresados y lo guarda.
	                persona = new persona(nombres, telefono, email, categoria, favorito);
	                new personaDAO(persona).escribirArchivo();
	                // Limpia los campos después de agregar el contacto.
	                limpiarCampos();
	                // Muestra un mensaje de éxito.
	                JOptionPane.showMessageDialog(delegado, "Contacto Registrado!!!");
	            } else {
	                // Muestra un mensaje de advertencia si no se ha seleccionado una categoría válida.
	                JOptionPane.showMessageDialog(delegado, "Elija una Categoria!!!");
	            }
	        } else {
	            // Muestra un mensaje de advertencia si algún campo está vacío.
	            JOptionPane.showMessageDialog(delegado, "Todos los campos deben ser llenados!!!");
	        }
	    } else if (e.getSource() == delegado.btn_eliminar) {

	        JOptionPane.showMessageDialog(
	                delegado,
	                "Funcionalidad eliminar en proceso"
	        );
	    } else if (e.getSource() == delegado.btn_modificar) {

	        JOptionPane.showMessageDialog(
	                delegado,
	                "Funcionalidad modificar en proceso"
	        );
	    }else if (e.getSource() == delegado.btn_exportar) {

	        exportarCSV();
	    }
	}

	// Método que maneja los eventos de selección en la lista de contactos.

	// Método privado para cargar los datos del contacto seleccionado en los campos de la GUI.
	private void cargarContacto(int index) {

	    delegado.txt_nombres.setText(
	            contactos.get(index).getNombre());

	    delegado.txt_telefono.setText(
	            contactos.get(index).getTelefono());

	    delegado.txt_email.setText(
	            contactos.get(index).getEmail());

	    delegado.chb_favorito.setSelected(
	            contactos.get(index).isFavorito());

	    delegado.cmb_categoria.setSelectedItem(
	            contactos.get(index).getCategoria());
	}

	// Método que maneja los eventos de cambio de estado en los componentes cmb_categoria y chb_favorito.
	@Override
	public void itemStateChanged(ItemEvent e) {
		// Verifica si el evento proviene del JComboBox de categoría.
	    if (e.getSource() == delegado.cmb_categoria) {
	        // Obtiene el elemento seleccionado en el JComboBox y lo convierte en una cadena.
	    	if(delegado.cmb_categoria.getSelectedItem() != null) {

	    	    categoria = delegado.cmb_categoria
	    	            .getSelectedItem()
	    	            .toString();
	    	}
	        // Actualiza la categoría seleccionada en la variable "categoria".
	    } else if (e.getSource() == delegado.chb_favorito) {
	        // Verifica si el evento proviene del JCheckBox de favorito.
	        favorito = delegado.chb_favorito.isSelected();
	        // Obtiene el estado seleccionado del JCheckBox y actualiza el estado de favorito en la variable "favorito".
	    }
	}
	private void exportarCSV() {

	    try {

	        FileWriter writer =
	                new FileWriter("contactos.csv");

	        for (int i = 0;
	             i < delegado.tabla.getRowCount();
	             i++) {

	            writer.write(
	                    delegado.tabla.getValueAt(i,0).toString()+","+
	                    delegado.tabla.getValueAt(i,1).toString()+","+
	                    delegado.tabla.getValueAt(i,2).toString()+","+
	                    delegado.tabla.getValueAt(i,3).toString()
	            );

	            writer.write("\n");
	        }

	        writer.close();

	        JOptionPane.showMessageDialog(
	                delegado,
	                "CSV exportado correctamente"
	        );

	    } catch (Exception e) {

	        e.printStackTrace();
	    }
	}
}