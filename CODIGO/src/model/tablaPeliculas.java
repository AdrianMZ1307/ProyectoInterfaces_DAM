package model;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class tablaPeliculas extends AbstractTableModel {

	private ArrayList<pelicula> listaPeliculas;
	private String[] columnas = { "Titulo", "Puntuacion", "Fecha de Estreno", "Duracion" };

	public tablaPeliculas(ArrayList<pelicula> lPeliculas) {
		super();
		listaPeliculas = lPeliculas;
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public String getColumnName(int col) {
		return columnas[col];
	}

	@Override
	public int getRowCount() {
		return listaPeliculas.size();
	}

	@Override
	public int getColumnCount() {
		return columnas.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex != -1 && listaPeliculas.size() > rowIndex) {
			pelicula p = listaPeliculas.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return p.getTitulo();
			case 1:
				return p.getPuntuacion();
			case 2:
				String fehca = DateFormat.getDateTimeInstance().format(p.getFechaEstreno());
				String formatoFecha = String.format("%s", fehca);
				return formatoFecha;
			case 3:
				float time = (float) p.getDuracion() / (float) 60;
				int hours = (int) time;
				int minutes = (int) (60 * (time - hours));
				return hours + "h " + minutes + "m";
			default:
				return null;
			}
		} else {
			return null;
		}
	}

	public void traducirTabla() {
		ResourceBundle rb = ResourceBundle.getBundle("data.language_file");
		String valor = rb.getString("colTitulo");
		columnas[0] = valor;
		valor = rb.getString("colPunt");
		columnas[1] = valor;
		valor = rb.getString("colFecha");
		columnas[2] = valor;
		valor = rb.getString("colDuration");
		columnas[3] = valor;
	}

}
