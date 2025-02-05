package ubu.inf;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
/**
 * Actividad en la que introducir la contrase�a para poder entrar en la aplicaci�n.
 * @author     David Herrero de la Pe�a
 * @author     Jonatan Santos Barrios
 * @uml.dependency   supplier="ubu.inf.FalsaActividad"
 * @uml.dependency   supplier="ubu.inf.Main"
 * @uml.dependency   supplier="ubu.inf.Preferencias"
 */
public class Login extends Activity{
	/**
	 * Campo donde introducir la contrase�a.
	 */
	private EditText pass;
	/**
	 * Boton para aceptar.
	 */
	private Button ok;
	/**
	 * Preferencias de la aplicaci�n,donde est� guardada la contrase�a.
	 */
	private SharedPreferences pref;
	/**
	 * Contrase�a guardada.
	 */
	private String passguardada;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		inicializa();
		
	}

	/**
	 * Funci�n para iniciar todos los componentes de la pantalla.
	 */
	private void inicializa(){
		//obtenemos la contrase�a guardada
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		passguardada=pref.getString("pass", "");
		
		pass= (EditText) findViewById(R.id.et_login_pass);
		ok = (Button) findViewById(R.id.bt_login_ok);
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String aux = pass.getText().toString();
				if(aux.equals(passguardada)){//contrase�a correcta
					Intent i = new Intent(Login.this, Main.class);
					startActivity(i);
				}else{
					Intent i = new Intent(Login.this, FalsaActividad.class);
					startActivity(i);
				}
				
			}
		});
	}
}
