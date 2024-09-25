package model;

import java.sql.Blob;
import java.sql.Date;

public class pelicula {
	String titulo, sinopsis;
	Float puntuacion;
	int duracion;
	Date fechaEstreno;
	Blob portada; 

	public pelicula() {
	}

	public pelicula(String titulo, String sinopsis, Float puntuacion, int duracion, Date fechaEstreno, Blob portada) {
		this.titulo = titulo;
		this.sinopsis = sinopsis;
		this.puntuacion = puntuacion;
		this.duracion = duracion;
		this.fechaEstreno = fechaEstreno;
		this.portada = portada;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getSinopsis() {
		return sinopsis;
	}

	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}

	public Float getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(Float puntuacion) {
		this.puntuacion = puntuacion;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public Date getFechaEstreno() {
		return fechaEstreno;
	}

	public void setFechaEstreno(Date fechaEstreno) {
		this.fechaEstreno = fechaEstreno;
	}

	public Blob getPortada() {
		return portada;
	}

	public void setPortada(Blob portada) {
		this.portada = portada;
	}

	@Override
	public String toString() {
		return titulo;
	}

}
