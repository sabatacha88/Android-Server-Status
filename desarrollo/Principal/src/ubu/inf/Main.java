package ubu.inf;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Actividad pinripal , desde la que se lanzar�n el resto de m�dulos de la aplicaci�n.
 * @author     David Herrerod de la Pe�a
 * @author     Jonatan Santos Barrios
 * @uml.dependency   supplier="ubu.inf.Preferencias"
 */
public class Main extends Activity {
	/**
	 * Boton para acceder al terminal ssh.
	 */
	private Button terminal;
	/**
	 * Boton para acceder al localizador GPS.
	 */
	private Button gps;
	/**
	 * Boton para acceder al control de accesos.
	 */
	private Button control;
	/**
	 * Texto para indicar el estado del servicio de localizaci�n gps.
	 */
	private TextView estadogps;
	/**
	 * Texto para indicar el estado del servicio de control ssh.
	 */
	private TextView estadossh;
	/**
	 * Texto para indicar el estado del servicio de control de emails.
	 */
	private TextView estadoemail;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		inicializa();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menuprincipal, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.preferencias:
			preferencias();
			break;
		
		default:
			break;
		}

		return true;
	}
	
	/**
	 * Funci�n que llama a la ventana para establecer las preferencias de la
	 * aplicaci�n.
	 */
	private void preferencias() {
		Intent i = new Intent(this, Preferencias.class);
		startActivity(i);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		compruebaServicios();
		super.onResume();
		
	}


	/**
	 * Funci�n para iniciar todos los componentes de la actividad.
	 */
	private void inicializa() {
		// encuentro los textview
		estadogps = (TextView) findViewById(R.id.tv_estado_gps);
		estadossh = (TextView) findViewById(R.id.tv_estado_ssh);
		estadoemail = (TextView) findViewById(R.id.tv_estado_email);
		// encuentro los botones
		terminal = (Button) findViewById(R.id.bt_menu_terminal);
		gps = (Button) findViewById(R.id.bt_menu_gps);
		control = (Button) findViewById(R.id.bt_menu_servidor);
		// a�adimos listeners
		control.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					Intent intent = new Intent(Intent.ACTION_MAIN);
					intent.setComponent(new ComponentName("ubu.inf.control",
							"ubu.inf.control.logica.Main"));
					startActivity(intent);
				} catch (ActivityNotFoundException e) {
					e.printStackTrace();
					Toast.makeText(Main.this,
							"Cliente control de accesos no instalado",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		gps.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					Intent intent = new Intent(Intent.ACTION_MAIN);
					intent.setComponent(new ComponentName("ubu.inf.gps",
							"ubu.inf.gps.logica.LocalizadorGPSActivity"));
					startActivity(intent);
				} catch (ActivityNotFoundException e) {
					e.printStackTrace();
					Toast.makeText(Main.this, "Localizados GPS no instalado",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		terminal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					Intent intent = new Intent(Intent.ACTION_MAIN);
					intent.setComponent(new ComponentName("ubu.inf.terminal",
							"ubu.inf.terminal.logica.MainSSH"));
					startActivity(intent);
				} catch (ActivityNotFoundException e) {
					e.printStackTrace();
					Toast.makeText(Main.this, "Terminal no instalado",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	/**
	 * Funci�n para saber si el ServicioGPS est� funcionando.
	 * 
	 * @return true si est� funcionando, false si no lo est� haciendo.
	 */
	private boolean isMyServiceRunningGPS() {
		ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			
			if ("ubu.inf.gps.logica.ServicioGPS".equals(service.service
					.getClassName())) {
				
				return true;
			}
		}
		return false;
	}

	/**
	 * Funci�n para saber si el ServicioEmail est� funcionando.
	 * 
	 * @return true si est� funcionando, false si no lo est� haciendo.
	 */
	private boolean isMyServiceRunningEmail() {
		ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if ("ubu.inf.control.logica.ServicioEmail".equals(service.service
					.getClassName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Funci�n para saber si el ServicioSSH est� funcionando.
	 * 
	 * @return true si est� funcionando, false si no lo est� haciendo.
	 */
	private boolean isMyServiceRunningSSH() {
		ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if ("ubu.inf.control.logica.ServicioSSH".equals(service.service
					.getClassName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Funci�n para comprobar el estado de los 3 servicios y cambiar el texto acorde 
	 * a los resultados.
	 */
	private void compruebaServicios() {
		if (isMyServiceRunningEmail()) {
			
			estadoemail.setText("Corriendo");
			estadoemail.setTextColor(Color.GREEN);
		} else {
			
			estadoemail.setText("Detenido");
			estadoemail.setTextColor(Color.RED);
		}
		if (isMyServiceRunningSSH()) {
			
			estadossh.setText("Corriendo");
			estadossh.setTextColor(Color.GREEN);
		} else {
			
			estadossh.setText("Detenido");
			estadossh.setTextColor(Color.RED);
		}
		if (isMyServiceRunningGPS()) {
			
			estadogps.setText("Corriendo");
			estadogps.setTextColor(Color.GREEN);
		} else {
			
			estadogps.setText("Detenido");
			estadogps.setTextColor(Color.RED);
		}
	}
}