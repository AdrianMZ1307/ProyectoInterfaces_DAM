package model;

import java.sql.Date;

import java.sql.Blob;

public class actor {
	String nombre;
	Date fechaNacimiento;
	Blob foto;

	public actor(String nombre, Date fechaNacimiento, Blob foto) {
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.foto = foto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Blob getFoto() {
		return foto;
	}

	public void setFoto(Blob foto) {
		this.foto = foto;
	}

	@Override
	public String toString() {
		return nombre;
	}

}
