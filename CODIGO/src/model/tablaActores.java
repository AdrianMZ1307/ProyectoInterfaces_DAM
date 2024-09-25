package model;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class tablaActores extends AbstractTableModel {

	private ArrayList<actor> listaActores;
	private String[] columnas = { "Nombre Completo", "Fecha de Nacimiento" };

	public tablaActores(ArrayList<actor> lActores) {
		super();
		listaActores = lActores;
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public String getColumnName(int col) {
		return columnas[col];
	}

	@Override
	public int getRowCount() {
		return listaActores.size();
	}

	@Override
	public int getColumnCount() {
		return columnas.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex != -1 && listaActores.size() > rowIndex) {
			actor a = listaActores.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return a.getNombre();
			case 1:
				return a.getFechaNacimiento();
			default:
				return null;
			}
		} else {
			return null;
		}
	}

	public void traducirTabla() {
		ResourceBundle rb = ResourceBundle.getBundle("data.language_file");
		String valor = rb.getString("colNombre");
		columnas[0] = valor;
		valor = rb.getString("colFechaN");
		columnas[1] = valor;
	}

}
