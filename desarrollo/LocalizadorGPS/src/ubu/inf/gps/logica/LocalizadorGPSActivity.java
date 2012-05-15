package ubu.inf.gps.logica;

import ubu.inf.gps.R;
import ubu.inf.gps.R.layout;
import ubu.inf.gps.accesodatos.FachadaCoordenadas;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LocalizadorGPSActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		inicializar();
	}

	private void inicializar() {
		final TextView con = (TextView) findViewById(R.id.textView1);
		Button ini = (Button) findViewById(R.id.button1);
		ini.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setAction("ServicioGPS");
				startService(i);

			}
		});
		Button stop = (Button) findViewById(R.id.button3);
		stop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setAction("ServicioGPS");
				stopService(i);

			}
		});
		Button act = (Button) findViewById(R.id.button2);
		act.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				con.setText("registros: "+ FachadaCoordenadas.getInstance(LocalizadorGPSActivity.this).getCantidad());

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menugps, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.ajustes:
			preferencias();
			break;
		case R.id.clear:
			clear();
			break;
		case R.id.sms:
			clear();
			break;
		case R.id.email:
			clear();
			break;

		default:
			break;
		}

		return true;
	}

	/**
	 * Funci�n para borrar la base de datos de coordenadas.
	 */
	private void clear() {
		FachadaCoordenadas.getInstance(this).borraTabla();

	}

	/**
	 * Funci�n que llama a la ventana para elegir las preferencias de la
	 * aplicaci�n.
	 */
	private void preferencias() {
		Intent i = new Intent(this, Preferencias.class);
		startActivity(i);
	}

}