package ubu.itig;

public class Servidor {
	private int id;
	private String ip;
	private String usuario;
	private String contraseña;
	private String puerto;
	private String descripcion;
	
	

	public Servidor(int id, String ip, String usuario, String contraseña,
			String puerto, String descripcion) {
		super();
		this.id = id;
		this.ip = ip;
		this.usuario = usuario;
		this.contraseña = contraseña;
		this.puerto = puerto;
		this.descripcion = descripcion;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getContraseña() {
		return contraseña;
	}
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	public String getPuerto() {
		return puerto;
	}
	public void setPuerto(String puerto) {
		this.puerto = puerto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	

}
