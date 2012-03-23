package ubu.itig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class Consola  extends Activity{
	private EditText comando;
	private EditText result;
	private ImageButton run;
	private Session session;
	private JSch jsch;
	private ChannelExec channelExec;
	private InputStream in;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.consola);
		inicializa();
		
	}
	
	public void inicializa(){
		comando = (EditText) findViewById(R.id.et_consola_comando);
		result = (EditText) findViewById(R.id.et_consola_resultado);
		run = (ImageButton) findViewById(R.id.ib_consola_run);
	
		session= Conexion.getConexion().getSesion();
		jsch = Conexion.getConexion().getJsch();
		run.setOnClickListener(new listenerComando());
	}
	
	private class listenerComando implements View.OnClickListener{

		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			if(jsch!=null && session != null){
				
				try{
				
			
				channelExec = (ChannelExec) session
						.openChannel("exec");

				 in = channelExec.getInputStream();

				channelExec.setCommand(comando.getText().toString());
				channelExec.connect();
			
				
				result.setText(result.getText().toString() + session.getUserName()+"@"+session.getHost()+"  "+ comando.getText().toString() + '\n'+'\n');
				result.setTextColor(Color.GREEN); 
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			        String linea = null;
			  
			 
			        while ((linea = reader.readLine()) != null) {
			        	result.setText(result.getText().toString() + linea +'\n');
			        
			        }
			        result.setText(result.getText().toString() + '\n'+'\n');
			    
				}catch (JSchException e) {
					// TODO: handle exception
				}catch (IOException e) {
					// TODO: handle exception
				}
			}
		}
		
	}
}
