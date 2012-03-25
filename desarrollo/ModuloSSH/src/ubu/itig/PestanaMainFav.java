package ubu.itig;

import java.util.ArrayList;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PestanaMainFav extends Activity {
	public static final int REQUEST_TEXT = 0;
	public static final int REQUEST_TEXT2 = 1;
	private ArrayList<Servidor> datos;
	private FachadaServidores fachada;
	 private ArrayAdapter<Servidor> adapter;
	 private ListView list;
	 private ImageButton add;
	 private JSch jsch=null;
		private Session session=null;
		private int idedit=0;
		
	

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pestanamainfav);
        inicializa();
       
     

    }
    
private void inicializa(){
	 datos = new ArrayList<Servidor>();
     list = (ListView) findViewById(R.id.lv_main_fav_servidores);
     add = (ImageButton) findViewById(R.id.ib_main_fav_add);
     add.setOnClickListener(new ListenerAdd());
     
     list.setOnItemClickListener(new ListenerListView());
     registerForContextMenu(list);
     
     fachada=FachadaServidores.getInstance(this);
     datos=fachada.loadServidores();
     adapter = new ArrayAdapterServidor(this, datos);
     list.setAdapter(adapter);
}


@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
	 Log.i("mssh", "MainFav, on destroy , cerramos la fachada");
		fachada.closeFachada();
		super.onDestroy();
	}

@Override
public void onCreateContextMenu(ContextMenu menu, View v,
                                ContextMenuInfo menuInfo)
{
    super.onCreateContextMenu(menu, v, menuInfo);
 
        MenuInflater inflater = getMenuInflater();
 
   
        //AdapterView.AdapterContextMenuInfo info =(AdapterView.AdapterContextMenuInfo)menuInfo;
 
        menu.setHeaderTitle("Opciones");
 
        Log.i("mssh", "antes de inflar");
        inflater.inflate(R.menu.menu_fav, menu);
        Log.i("mssh", "despues");
    
}

@Override
public boolean onContextItemSelected(MenuItem item) {
 
    AdapterContextMenuInfo info =
        (AdapterContextMenuInfo) item.getMenuInfo();
 
    switch (item.getItemId()) {
       case R.id.CtxLstFavBorrar:
    	   fachada.deleteServidor(datos, datos.get(info.position).getId());
           adapter.notifyDataSetChanged();
            return true;
        case R.id.CtxLstFavEdit:
        	Intent i = new Intent(PestanaMainFav.this,Formulario.class);
        	idedit=datos.get(info.position).getId();
   	     	PestanaMainFav.this.startActivityForResult(i, REQUEST_TEXT2);
            return true;
        default:
            return super.onContextItemSelected(item);
    }
}


private class ListenerListView implements OnItemClickListener{

	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
		
		Servidor serv = datos.get(position);
		if (jsch == null || session == null) {
			try {
				int p = Integer.parseInt(serv.getPuerto());
				
				jsch = new JSch();
				
				session = jsch.getSession(serv.getUsuario(),serv.getIp(), p);
				
				UserInfo ui = new SUserInfo(serv.getContraseña(), null);
				
				session.setUserInfo(ui);
				
				session.setPassword(serv.getContraseña());
				
				session.connect();
				
				
				enviaIntent();
				
			
			} catch (JSchException e) {
				Toast.makeText(PestanaMainFav.this, "ERROR,compruebe los datos", Toast.LENGTH_LONG).show();
			}
				
		}else{
			session.disconnect();
			jsch = null;
			session = null;
			Toast.makeText(PestanaMainFav.this, "ya había una conexión abierta,se ha cerrado", Toast.LENGTH_LONG).show();
		}
		
	
		
	}
	
}

private class ListenerAdd implements View.OnClickListener{

	public void onClick(View arg0) {
		 Intent i = new Intent(PestanaMainFav.this,Formulario.class);
	     PestanaMainFav.this.startActivityForResult(i, REQUEST_TEXT);
		
	}
	
}

protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if ( requestCode == REQUEST_TEXT ){
         if ( resultCode == Activity.RESULT_OK ){
            Bundle bundle = data.getExtras();
            Servidor serv = new Servidor(0, bundle.getString("host"), bundle.getString("user"), bundle.getString("pass"), bundle.getString("port"), bundle.getString("desc"));
            fachada.insertServidor(datos, serv);
            adapter.notifyDataSetChanged();
         }
    }else{
    	if(requestCode == REQUEST_TEXT2){
    		if ( resultCode == Activity.RESULT_OK ){
    		Bundle bundle = data.getExtras();
            Servidor serv = new Servidor(idedit, bundle.getString("host"), bundle.getString("user"), bundle.getString("pass"), bundle.getString("port"), bundle.getString("desc"));
            fachada.editServidor(datos, serv);
            adapter.notifyDataSetChanged();
    		}
    	}
    }
}

private class ArrayAdapterServidor extends ArrayAdapter<Servidor>{
	private Activity context;
	private ArrayList<Servidor> datos;

	public ArrayAdapterServidor(Activity context,ArrayList<Servidor> array) {
		super(context, R.layout.list_servers,array);
		// TODO Auto-generated constructor stub
		this.context=context;
		datos = array;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = context.getLayoutInflater();
        View item = inflater.inflate(R.layout.list_servers, null);
        
        final TextView ID = (TextView)item.findViewById(R.id.tv_id);
        Integer id = datos.get(position).getId();
        ID.setText(id.toString());
 
        TextView lblIP = (TextView)item.findViewById(R.id.tv_IP2);
        lblIP.setText(datos.get(position).getIp());
 
        TextView lblusuario = (TextView)item.findViewById(R.id.tv_usuario2);
        lblusuario.setText(datos.get(position).getUsuario());
 
        TextView descripcion = (TextView)item.findViewById(R.id.tv_descripcion2);
        descripcion.setText(datos.get(position).getDescripcion());
        
        
        
        
        return(item);
		// TODO Auto-generated method stub
		//return super.getView(position, convertView, parent);
	}
	

}
private void enviaIntent(){
	Intent intent = new Intent(PestanaMainFav.this,Consola.class);
	
	SingletonConexion.getConexion().setSesion(session);
	SingletonConexion.getConexion().setJsch(jsch);
	
	startActivity(intent);
}
}