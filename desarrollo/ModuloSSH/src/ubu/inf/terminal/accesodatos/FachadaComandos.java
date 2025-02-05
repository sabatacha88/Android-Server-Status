package ubu.inf.terminal.accesodatos;

import java.util.ArrayList;


import ubu.inf.terminal.modelo.Script;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
/**
 * Clase que utiliza la aplicaci�n para acceder a la BD de forma simplificada, contiene m�todos para modificar la BD de forma transparente. Usa patron de dise�o Fachada y Singleton.
 * @author   David Herrero de la Pe�a
 * @author   Jonatan Santos Barrios
 * @version   1.0
 * @see  ServidoresSQLiteHelper
 */
public class FachadaComandos {
	Context context;
	/**
	 * @uml.property  name="helpercomandos"
	 * @uml.associationEnd  
	 */
	ComandosSQLiteHelper Helpercomandos;
	SQLiteDatabase DBcomandos;
	/**
	 * @uml.property  name="myFachada"
	 * @uml.associationEnd  
	 */
	private static FachadaComandos myFachada;

	/**
	 * Contructor privado de la clase.
	 * @param context
	 */
	private FachadaComandos(Context context) {
		this.context = context;
		Log.i("mssh",
				"ya tenemos la fachada de comandos, ahora a por el helper");
		Helpercomandos = new ComandosSQLiteHelper(context, "DBcomandos", null,
				1);
		Log.i("mssh", "helper comandos creado correctamente");

	}

	/**
	 * M�todo est�tico para obtener la �nica instacia de la fachada.
	 * @param context
	 * @return referencia a la fachada �nica.
	 */
	public static FachadaComandos getInstance(Context context) {

		Log.i("mssh", "entramos en getInstance");
		if (myFachada == null) {
			Log.i("mssh", "my fachada vale null");
			myFachada = new FachadaComandos(context);
		}
		return myFachada;
	}

	/**
	 * Funci�n para cerrar la fachada y la base de datos.
	 */
	public void closeFachada() {
		Log.i("mssh", "closeFachada,cerramos el helper");
		Helpercomandos.close();
		myFachada = null;
	}

	/**
	 * Funci�n para obtener todos los Comandos guardados en la base de datos.
	 * @return ArrayList con todos los scripts de la base de datos.
	 */
	public ArrayList<Script> loadComandos() {
		Log.i("mssh", "loadComandos,creamos el comando");
		ArrayList<Script> resultado = new ArrayList<Script>();

		Script comando = null;
		Log.i("mssh", "pedimos al helper la base de datos para leer");
		DBcomandos = Helpercomandos.getReadableDatabase();
		Cursor c2;
		Cursor c = DBcomandos.rawQuery("SELECT * FROM scripts", null);
		if (c.moveToFirst()) {
			Log.i("mssh", "loadComandos,hay datos");
			do {
				int id = c.getInt(0);
				String nombre = c.getString(1);
				int cantidad = c.getInt(2);
				comando = new Script(nombre, cantidad, id);

				c2 = DBcomandos.rawQuery(
						"SELECT comando FROM comandos WHERE idScript=" + id,
						null);
				if (c2.moveToFirst()) {
					do {
						comando.addComando(c2.getString(0));
					} while (c2.moveToNext());
				}
				c2.close();

				resultado.add(comando);
			} while (c.moveToNext());

		} else {
			Log.i("mssh", "loadComandos,no hay datos en la base de datos");
		}

		Log.i("mssh", "cerramos la base de datos");
		DBcomandos.close();
		return resultado;
	}

	/**
	 * Funci�n para insertar un nuevo comando en la base de datos.
	 * @param nombre nombre del script.
	 * @param cantidad cantidad de comandos que contiene.
	 * @param comandos ArrayList con todos los comandos a introducir.
	 */
	public void insertComandos(String nombre, int cantidad,
			ArrayList<String> comandos) {
		int id = 0;

		Log.i("mssh", "fachada,insert,conseguimos la bd en forma W");
		DBcomandos = Helpercomandos.getWritableDatabase();
		if (DBcomandos != null) {
			Log.i("mssh", "hemos conseguido la base de datos para a�adir");
			try {
				Log.i("mssh", "ejecutamos el insert");
				DBcomandos
						.execSQL("INSERT INTO scripts(idScript,nombre,cantidad) VALUES (NULL,'"
								+ nombre + "'," + cantidad + ")");
				Cursor c = DBcomandos.rawQuery("SELECT last_insert_rowid();",
						null);
				if (c.moveToFirst()) {
					id = c.getInt(0);
				}
				for (int i = 0; i < cantidad; ++i) {
					DBcomandos
							.execSQL("INSERT INTO comandos(idComando,idScript,comando) VALUES (NULL,"
									+ id + ",'" + comandos.get(i) + "')");
				}
			} catch (SQLException e) {
				Log.e("mssh", "error al introducir,foreign key? id = " + id);
			}
			Log.i("mssh", "derramos la base de datos");
			DBcomandos.close();

		} else {
			Log.i("mssh", "no hemos conseguido la base, retornamos null");
		}

	}

	/**
	 * Borra un comando de la base de datos.
	 * @param id identificador del script a borrar.
	 */
	public void deleteComando(int id) {
		Log.i("mssh", "vamos a borrar");
		String sql = "DELETE FROM scripts WHERE idScript=" + id;
		DBcomandos = Helpercomandos.getWritableDatabase();
		if (DBcomandos != null) {
			Log.i("mssh", "hemos conseguido la base de datos");
			try {
				DBcomandos.execSQL(sql);

//				for (int i = 0; i < ant.size(); ++i) {
//					if (ant.get(i).getId() == id) {
//						ant.remove(i);
//						break;
//					}
//				}

			} catch (SQLException e) {
				Log.e("mssh", "error al borrar,clave incorrecta? id = " + id);
			}
			Log.i("mssh", "cerramos la base de datos");
			DBcomandos.close();
		} else {
			Log.i("mssh", "no hemos conseguido la base, retornamos null");
		}
	}

	

	/**
	 * Funci�n para borrar las tablas y volver a crearlas vac�as.
	 */
	public void borraTabla() {
		String sql = "DROP TABLE IF EXISTS comandos";
		String sql2 = "DROP TABLE IF EXISTS scripts";
		
		String sqlCreate1 = "CREATE TABLE scripts (idScript INTEGER PRIMARY KEY,nombre TEXT,cantidad INTEGER)";
		String sqlCreate2 = "CREATE TABLE comandos (idComando INTEGER PRIMARY KEY, idScript INTEGER,comando TEXT,"
				+ "FOREIGN KEY (idScript) REFERENCES scripts ON UPDATE CASCADE ON DELETE CASCADE)";
		DBcomandos = Helpercomandos.getWritableDatabase();
		if (DBcomandos != null) {
			Log.i("mssh", "hemos conseguido la base de datos");
			try {
				DBcomandos.execSQL(sql);
				DBcomandos.execSQL(sql2);
				Log.i("mssh", "hemos borrado, ahora a crearla");
				DBcomandos.execSQL(sqlCreate1);
				DBcomandos.execSQL(sqlCreate2);
				Log.i("mssh", "se ha creado de nuevo la tabla");

			} catch (SQLException e) {
				Log.e("mssh", "error al borrar la tabla");
			}
			Log.i("mssh", "cerramos la base de datos");
			DBcomandos.close();
		} else {
			Log.i("mssh", "no hemos conseguido la base, retornamos null");
		}
		myFachada = null;
		Helpercomandos = new ComandosSQLiteHelper(context, "DBcomandos", null,
				1);
	}
}