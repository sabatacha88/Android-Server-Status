package ubu.inf.control.modelo;

import android.graphics.Color;
/**
 * Clase para guardar todos los datos relativos a un servidor.
 * @author  David Herrero de la Pe�a
 * @author  Jonatan Santos Barrios
 * @version  1.0
 */
public class Servidor {
	/**
	 * puerto por el que conectarse.
	 * @uml.property  name="puerto"
	 */
	private int puerto;
	/**
	 * IP del servidor.
	 * @uml.property  name="ip"
	 */
	private String ip;
	/**
	 * Descripci�n del servidor.
	 * @uml.property  name="descripcion"
	 */
	private String descripcion;
	/**
	 * Indica si se inicia al encender el tel�fono.
	 * @uml.property  name="inicio"
	 */
	private boolean inicio;
	/**
	 * ID del servidor.
	 * @uml.property  name="id"
	 */
	private int id;
	/**
	 * Color para identificar al servidor.
	 * @uml.property  name="color"
	 */
	private int color;
	/**
	 * Constructor de la clase Servidor.
	 * @param ip
	 * @param descripcion
	 * @param inicio
	 * @param id
	 * @param color
	 * @param puerto
	 */
	public Servidor(String ip, String descripcion, boolean inicio, int id,
			int color,int puerto) {
		super();
		this.ip = ip;
		this.descripcion = descripcion;
		this.inicio = inicio;
		this.id = id;
		this.color = color;
		this.puerto = puerto;
	}
	
	
	/**
	 * @return
	 * @uml.property  name="puerto"
	 */
	public int getPuerto() {
		return puerto;
	}


	/**
	 * @param puerto
	 * @uml.property  name="puerto"
	 */
	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}


	/**
	 * @return
	 * @uml.property  name="ip"
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip
	 * @uml.property  name="ip"
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * @return
	 * @uml.property  name="descripcion"
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion
	 * @uml.property  name="descripcion"
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * @return
	 * @uml.property  name="inicio"
	 */
	public boolean isInicio() {
		return inicio;
	}
	/**
	 * @param inicio
	 * @uml.property  name="inicio"
	 */
	public void setInicio(boolean inicio) {
		this.inicio = inicio;
	}
	/**
	 * @return
	 * @uml.property  name="id"
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id
	 * @uml.property  name="id"
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return
	 * @uml.property  name="color"
	 */
	public int getColor() {
		return color;
	}
	/**
	 * @param color
	 * @uml.property  name="color"
	 */
	public void setColor(int color) {
		this.color = color;
	}
	@Override
	/**
	 * Funci�n que dice si dos servidores son iguales comparandolos por su ID.
	 */
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		Servidor oaux = (Servidor) o;
		return oaux.getId()==id;
	}
	
	

}
