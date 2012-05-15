package ubu.inf.control.logica;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Stack;

import ubu.inf.control.R;
import ubu.inf.control.R.id;
import ubu.inf.control.modelo.ComparatorFecha;
import ubu.inf.control.modelo.ComparatorID;
import ubu.inf.control.modelo.ComparatorTipo;
import ubu.inf.control.modelo.ComparatorUrgencia;
import ubu.inf.control.modelo.Notificacion;
import ubu.inf.control.modelo.Servidor;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Clase que implementa la funcionalidad de la pestana de notificaciones.
 * 
 * @author David Herrero
 * @author Jonatan Santos
 * 
 * @version 1.0
 * 
 */
public class PestanaMainNotificaciones extends Activity {
	/**
	 * id del dispositivo
	 */

	String id_dispositivo ;
	private static final int RESULT_FILTRO = 1;
	private ArrayList<Notificacion> datos;
	private Stack<ArrayList<Notificacion>> pila;
	private ListView lista;
	private ArrayAdapterNot adapter3;

	private Spinner ordenTipo;
	private Spinner ordenAsc;

	private int criterio = 0;
	private int orden = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.pestanamainnot);
		id_dispositivo= Secure.getString(getBaseContext()
				.getContentResolver(), Secure.ANDROID_ID);
		inicializa();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		// aqui se podr�a hacer la carga de las notificaciones, ya que si se
		// hace en
		// onResume se har�a demasiadas veces
		if(getIntent().getExtras()!=null){
			Bundle b = getIntent().getExtras();
		
			Log.i("control", "han llamado a la pesta�a desde una notificacion");
			actualizar();
		}
		Log.i("control", "onStart pestana not");
		super.onStart();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menunot, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.filtro:
			// showDialog(DIALOG_FILTRO);
			Intent i = new Intent(this, Filtro.class);
			startActivityForResult(i, RESULT_FILTRO);
			break;
		case R.id.re_filtro:
			// showDialog(DIALOG_FILTRO);
			quitarFiltro();
			break;
		case R.id.ajustes_not:
			// showDialog(DIALOG_FILTRO);
			preferencias();
			break;
		case R.id.actualizar_not:
			// showDialog(DIALOG_FILTRO);
			actualizar();
			break;

		default:
			break;
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case RESULT_FILTRO:
			if (resultCode == RESULT_OK) {
				filtrar(data);

			}
			break;

		default:
			break;
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * Funci�n para obtener la referencia a todos los componentes y a�adir los
	 * datos iniciales.
	 */
	private void inicializa() {
		// creamos el array y la pila.
		datos = new ArrayList<Notificacion>();
		pila = new Stack<ArrayList<Notificacion>>();
		// ejemplo
		Servidor s = new Servidor("10.170.1.1", "escripcion", false, 1,
				Color.RED);
		datos.add(new Notificacion("hola", new Date(), 0, 0, s));
		datos.add(new Notificacion("hola1", new Date(), 1, 1, s));
		datos.add(new Notificacion("hola2", new Date(), 1, 2, s));
		datos.add(new Notificacion("hola3", new Date(), 1, 0, s));
		datos.add(new Notificacion("hola4", new Date(), 0, 1, s));
		datos.add(new Notificacion("hola5", new Date(), 0, 2, s));

		// metiendo los datos en los spiner
		ordenTipo = (Spinner) findViewById(R.id.sp_pestananot_criterio);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.tipo, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ordenTipo.setAdapter(adapter);
		ordenAsc = (Spinner) findViewById(R.id.sp_pestananot_orden);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
				this, R.array.asc, android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ordenAsc.setAdapter(adapter2);

		// boton ok
		Button ok = (Button) findViewById(R.id.bt_pestanamainnot_ok);
		// lista
		lista = (ListView) findViewById(R.id.lv_pestanamainnot_lista);
		lista.setOnItemClickListener(new ListenerListView());
		adapter3 = new ArrayAdapterNot(this, datos);
		lista.setAdapter(adapter3);
		// a�adimos los Listener
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ordena();
				adapter3.notifyDataSetChanged();

			}
		});
		ordenTipo.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItem, int position, long id) {

				//
				criterio = position;

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				//

			}
		});
		ordenAsc.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItem, int position, long id) {

				orden = position;

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

	}

	/**
	 * Funci�n para filtrar los datos, los datos anteriores se guardan en una
	 * pila para poder volver hacia atr�s.
	 * 
	 * @param d
	 *            Intent con los datos que ha enviado Filtro.java
	 * @see Filtro
	 */
	private void filtrar(Intent d) {
		Intent data = d;
		pila.push(datos);
		ArrayList<Notificacion> aux = new ArrayList<Notificacion>(datos);
		Bundle bundle = data.getExtras();

		int id = bundle.getInt("id");// filtro de ID
		if (id != -1) {
			Log.i("control", "filtro id");
			for (int i = 0; i < aux.size(); ++i) {
				if (aux.get(i).getServ().getId() != id) {

					aux.remove(i);
					--i;
				}
			}
		}
		int tipo = bundle.getInt("tipo");// filtro de tipo , 0 ninguna
		--tipo;
		if (tipo != -1) {

			for (int i = 0; i < aux.size(); ++i) {
				if (aux.get(i).getTipo() != tipo) {

					aux.remove(i);
					--i;

				}

			}
		}
		Date dia = new Date(bundle.getLong("desde"));// filtro dia desde
		if (dia.getTime() != -1L) {// si hay fecha desde
			Log.i("control", "filtro desde");
			for (int i = 0; i < aux.size(); ++i) {
				if (aux.get(i).getFecha().before(dia)) {
					aux.remove(i);
					--i;
				}
			}
		}
		dia = new Date(bundle.getLong("hasta"));// filtro dia hasta

		if (dia.getTime() != -1L) {// si hay fecha hasta
			Log.i("control", "filtro hasta");
			for (int i = 0; i < aux.size(); ++i) {
				if (aux.get(i).getFecha().after(dia)) {
					aux.remove(i);
					--i;
				}
			}
		}
		int urgencia = bundle.getInt("urgencia");// filtro de urgencia
		--urgencia;
		if (urgencia != -1) {
			Log.i("control", "filtro urgencia");
			for (int i = 0; i < aux.size(); ++i) {
				if (aux.get(i).getUrgencia() != urgencia) {
					aux.remove(i);
					--i;
				}
			}
		}

		datos = aux;

		adapter3 = null;
		adapter3 = new ArrayAdapterNot(this, datos);
		lista.setAdapter(adapter3);
	}

	/**
	 * Funci�n para quitar el filtro y colocar los datos en un estado anterior.
	 */
	private void quitarFiltro() {
		if (!pila.isEmpty()) {
			datos = pila.pop();
			adapter3 = null;
			adapter3 = new ArrayAdapterNot(this, datos);
			lista.setAdapter(adapter3);

		}
	}

	/**
	 * Funci�n para ordenar las Notificaciones.
	 */
	private void ordena() {
		switch (criterio) {
		case 1:// ID
			if (orden == 0) {// asc
				Collections.sort(datos, new ComparatorID());
			} else {
				Collections.sort(datos,
						Collections.reverseOrder(new ComparatorID()));
			}
			break;
		case 2:// TIPO
			if (orden == 0) {// asc
				Collections.sort(datos, new ComparatorTipo());

			} else {
				Collections.sort(datos,
						Collections.reverseOrder(new ComparatorTipo()));
			}
			break;
		case 3:// FECHA
			if (orden == 0) {// asc
				Collections.sort(datos, new ComparatorFecha());
			} else {
				Collections.sort(datos,
						Collections.reverseOrder(new ComparatorFecha()));
			}
			break;
		case 4:// URGENCIA
			if (orden == 0) {// asc
				Collections.sort(datos, new ComparatorUrgencia());
			} else {
				Collections.sort(datos,
						Collections.reverseOrder(new ComparatorUrgencia()));
			}
			break;

		default:
			break;
		}
		adapter3.notifyDataSetChanged();
	}

	/**
	 * Funci�n que llama a la ventana para elegir las preferencias de la
	 * aplicaci�n.
	 */
	private void preferencias() {
		Intent i = new Intent(this, Preferencias.class);
		startActivity(i);
	}

	/**
	 * Funci�n que actualiza las notificaciones por si hay alguna nueva.
	 */
	private void actualizar() {
		// TODO
		
		//un nuevo thread para descargar todas las notificaciones, y al final cambiar el adapter.
	}

	/**
	 * Clase que implementa el ArrayAdapter para las lista de notificaciones.
	 * 
	 * @author David Herrero de la Pe�a
	 * @author Jonatan Santos Barrio
	 * 
	 */
	private class ArrayAdapterNot extends ArrayAdapter<Notificacion> {
		private Activity context;
		private ArrayList<Notificacion> datos;

		public ArrayAdapterNot(Activity context, ArrayList<Notificacion> array) {
			super(context, R.layout.list_servers, array);

			this.context = context;
			datos = array;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			LayoutInflater inflater = context.getLayoutInflater();
			View item = inflater.inflate(R.layout.list_notificaciones, null);

			TextView ip = (TextView) item.findViewById(R.id.tv_listnot_ip);
			TextView id = (TextView) item.findViewById(R.id.tv_listnot_id);
			TextView fecha = (TextView) item
					.findViewById(R.id.tv_listnot_fecha);
			TextView hora = (TextView) item.findViewById(R.id.tv_listnot_hora);
			TextView urgencia = (TextView) item
					.findViewById(R.id.tv_listnot_urgencia);
			ImageView tipo = (ImageView) item.findViewById(R.id.imageView1);
			// ponemos la IP
			ip.setText(datos.get(position).getServ().getIp());
			// ponemos el ID
			id.setText("" + datos.get(position).getServ().getId());
			// ponemos el color identificativo
			id.setBackgroundColor(datos.get(position).getServ().getColor());
			// ponemos el icono del ssh o del email
			if (datos.get(position).getTipo() == 0) {
				tipo.setBackgroundResource(R.drawable.ic_ssh);
			} else {
				tipo.setBackgroundResource(R.drawable.ic_email);
			}
			// construimos la cadena con la fecha
			String stringfecha = datos.get(position).getFecha().getDay() + "/"
					+ (datos.get(position).getFecha().getMonth() + 1) + "/"
					+ (datos.get(position).getFecha().getYear() + 1900);
			fecha.setText(stringfecha);
			// construimos la cadena con la hora
			String stringhora = datos.get(position).getFecha().getHours() + ":"
					+ datos.get(position).getFecha().getMinutes();
			hora.setText(stringhora);
			// ponemos la urgencia
			switch (datos.get(position).getUrgencia()) {
			case 0:
				urgencia.setText("Grave");
				urgencia.setTextColor(Color.RED);
				break;
			case 1:
				urgencia.setText("Medio");
				urgencia.setTextColor(Color.YELLOW);
				break;
			case 2:
				urgencia.setText("Info");
				urgencia.setTextColor(Color.GREEN);
				break;

			default:
				break;
			}

			return (item);

		}
	}

	/**
	 * Clase que implementa el Listener del ListView.
	 * 
	 * @author David Herrero de la Pe�a
	 * @author Jonatan Santos Barrio
	 * 
	 */
	private class ListenerListView implements OnItemClickListener {

		public void onItemClick(AdapterView<?> a, View v, int position, long id) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					PestanaMainNotificaciones.this);

			builder.setTitle("Informacion");
			builder.setMessage(datos.get(position).getTexto());
			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			builder.create();
			builder.show();

		}
	}

}
