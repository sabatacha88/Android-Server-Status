package ubu.inf.control.modelo;

import java.util.ArrayList;

import android.util.Log;

/**
 * Clase para guardar todos los datos relativos al servicioSSH.
 * 
 * @author David Herrero de la Pe�a
 * @author Jonatan Santos Barrios
 * @version 1.0
 * 
 * 
 */
public class SingletonServicios {
	private static SingletonServicios MyConexion;
	/**
	 * array con todos los servidores a los que consultar.
	 */
	private ArrayList<Servidor> hosts;
	private int cantidad =0;

	public int getCantidad() {
		return cantidad;
	}

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

	public ArrayList<Servidor> getHosts() {

		return hosts;
	}

	public void setHosts(ArrayList<Servidor> hosts) {
		this.hosts = hosts;
	}

}
