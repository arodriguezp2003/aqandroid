package cl.tkpass.validador;




import java.text.ParseException;


import cl.tkpass.validador.app.AppController;
import cl.tkpass.validador.app.Servicio;
import cl.tkpass.validador.sql.BaseHandler;
import cl.tkpass.validador.util.verifica;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


public class MainActivity extends Activity {
	private EditText Barra;

  @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		//inicia el servicio 
		
		startService(new Intent(this, Servicio.class));
		
		BaseHandler rt = new BaseHandler(this);
		rt.EliminarTodo();		
		if(AppController.DEV)
		{
			setContentView(R.layout.debug_layout);
			Barra = (EditText)findViewById(R.id.dxBarra);
		}
		else
		{
			setContentView(R.layout.main_activity);
		}

		String uriPath ="android.resource://cl.tkpass.validador/"+R.raw.turistik3;
		final VideoView mVideoView  = (VideoView)findViewById(R.id.videoView1);
		mVideoView.setMediaController(new MediaController(this));       
		mVideoView.setVideoPath(uriPath);
		mVideoView.requestFocus();
		mVideoView.start();
		mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

		    @Override
		    public void onCompletion(MediaPlayer mp) {

		    	mVideoView.start();  

		    }
		});
		
	   // Barra.setText("395466-11072014-006-986532-MAANAAA M ANAAA");
		Barra.setFocusableInTouchMode(true);
		Barra.requestFocus();
		
		InputMethodManager imm = (InputMethodManager)getSystemService(
				Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(Barra.getWindowToken(), 0);
		
		
		
		Barra.setOnKeyListener(new View.OnKeyListener() {
		    public boolean onKey(View v, int keyCode, KeyEvent event) {
		        // If the event is a key-down event on the "enter" button
		        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
		            (keyCode == KeyEvent.KEYCODE_ENTER)) {
		          // Perform action on key press
		        	
		        	
		        	
		            
		            try {
		            	verifica vr = new  verifica(MainActivity.this);
		            	boolean y = vr.Verificar_Brazalete(Barra.getText().toString());
						Accion(vr.Mensaje,y);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Accion("El Brazalete al Paracer es Invalido",false);
					}
		         	Barra.setText("");
		        
		          return true;
		        }
		        return false;
		    }

			private void Accion(String txt, boolean bien) {
		
				if(bien){
				Toast toast3 = new Toast(getApplicationContext());
				 
		        LayoutInflater inflater = getLayoutInflater();
		        View layout = inflater.inflate(R.layout.alerta_exitosa,
		                        (ViewGroup) findViewById(R.id.alerta_exitosa));
		        TextView txtMsg2 = (TextView)layout.findViewById(R.id.ae_welcome);
		        TextView txtMsg = (TextView)layout.findViewById(R.id.aeName);
		        txtMsg2.setText("Bienvenido | Welcome :");
		        txtMsg.setText(txt);
		 
		        toast3.setDuration(Toast.LENGTH_LONG);
		        toast3.setView(layout);
		        toast3.show();
				}
				else
				{
					Toast toast3 = new Toast(getApplicationContext());
					 
			        LayoutInflater inflater = getLayoutInflater();
			        View layout = inflater.inflate(R.layout.alerta_error,
			                        (ViewGroup) findViewById(R.id.alerta_error));
			        TextView txtMsg2 = (TextView)layout.findViewById(R.id.aer_welcome);
			        TextView txtMsg = (TextView)layout.findViewById(R.id.aerName);
			        txtMsg2.setText("Alerta: ");
			        txtMsg.setText(txt);
			 
			        toast3.setDuration(Toast.LENGTH_LONG);
			        toast3.setView(layout);
			        toast3.show();
				}
				
			}
		});
		
  }

}
