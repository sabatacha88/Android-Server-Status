package ubu.inf;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * Actividad falsa que se muestra cuando el usuario introduce una contrase�a de acceso incorrecta. Tiene enlaces a google y a google play.
 * @author   David Herrero de la Pe�a.
 * @author   Jonatan Santos Barrios.
 * @uml.dependency   supplier="ubu.inf.Preferencias"
 */
public class FalsaActividad extends Activity {

	/**
	 * Imagen con el logo de google.
	 */
	private ImageView google;
	/**
	 * Imagen con el logo de google play.
	 */
	private ImageView market;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.falsa);
		inicializa();
	}

	/**
	 * Funci�n para iniciar todos los componentes de la aplicaci�n.
	 */
	private void inicializa() {
		google = (ImageView) findViewById(R.id.iv_falsa_google);
		market = (ImageView) findViewById(R.id.iv_falsa_play);
		google.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent("android.intent.action.VIEW", Uri
						.parse("http://www.google.es"));
				startActivity(intent);

			}
		});
		market.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent("android.intent.action.VIEW", Uri
						.parse("market://search?q=AndroidServerStatus"));
				startActivity(intent);

			}
		});

		
	}

	/**
	 * @uml.property  name="preferencias"
	 * @uml.associationEnd  inverse="falsaActividad:ubu.inf.Preferencias"
	 * @uml.association  name="<call>"
	 */
	private Preferencias preferencias;

	/**
	 * Getter of the property <tt>preferencias</tt>
	 * @return  Returns the preferencias.
	 * @uml.property  name="preferencias"
	 */
	public Preferencias getPreferencias() {
		return preferencias;
	}

	/**
	 * Setter of the property <tt>preferencias</tt>
	 * @param preferencias  The preferencias to set.
	 * @uml.property  name="preferencias"
	 */
	public void setPreferencias(Preferencias preferencias) {
		this.preferencias = preferencias;
	}

		
		

}
