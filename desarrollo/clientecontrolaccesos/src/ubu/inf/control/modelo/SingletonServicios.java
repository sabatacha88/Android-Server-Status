package ubu.inf.control.modelo;

import java.util.ArrayList;

import android.util.Log;

/**
 * Clase para guardar todos los datos relativos al servicioSSH.
 * @author  David Herrero de la Pe�a
 * @author  Jonatan Santos Barrios
 * @version  1.0
 */
public class SingletonServicios {
	/**
	 * @uml.property  name="myConexion"
	 * @uml.associationEnd  
	 */
	private static SingletonServicios MyConexion;
	/**
	 * array con todos los servidores a los que consultar.
	 * @uml.property  name="hosts"
	 */
	private ArrayList<Servidor> hosts;
	/**
	 * @uml.property  name="cantidad"
	 */
	private int cantidad =0;

	/**
	 * @return
	 * @uml.property  name="cantidad"
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad
	 * @uml.property  name="cantidad"
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	private SingletonServicios() {
		hosts = new ArrayList<Servidor>();

	}

	public static SingletonServicios getConexion() {
		if (MyConexion == null) {
			MyConexion = new SingletonServicios();
		}
		return MyConexion;
	}

	/**
	 * @return
	 * @uml.property  name="hosts"
	 */
	public ArrayList<Servidor> getHosts() {

		return hosts;
	}

	/**
	 * @param hosts
	 * @uml.property  name="hosts"
	 */
	public void setHosts(ArrayList<Servidor> hosts) {
		this.hosts = hosts;
	}

}
