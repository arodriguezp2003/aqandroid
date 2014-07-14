package com.rsdev.prueba;


import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                                WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.mainactivity);

		
/*
		String uriPath = "android.resource://com.rsdev.prueba/"+R.raw.vid;
		VideoView mVideoView  = (VideoView)findViewById(R.id.videoView1);
		mVideoView.setMediaController(new MediaController(this));       
		mVideoView.setVideoPath(uriPath);
		mVideoView.requestFocus();
		mVideoView.start();
		
		mVideoView.setOnPreparedListener (new OnPreparedListener() {                    
		    @Override
		    public void onPrepared(MediaPlayer mp) {
		        mp.setLooping(true);
		    }
		});
		
		*/
		
		
		final EditText barra = (EditText)findViewById(R.id.xBarra);
		final TextView leyenda = (TextView)findViewById(R.id.lBarra);
		leyenda.setText("");
		
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(barra.getWindowToken(), 0);
		
		barra.setFocusableInTouchMode(true);
		barra.requestFocus();
		barra.setOnKeyListener(new View.OnKeyListener() {
		    public boolean onKey(View v, int keyCode, KeyEvent event) {
		        // If the event is a key-down event on the "enter" button
		        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
		            (keyCode == KeyEvent.KEYCODE_ENTER)) {
		          // Perform action on key press
		        	leyenda.setText(barra.getText());
		        	
		        	Accion(barra.getText().toString());
		                
		        	//barra.setText("");
		          return true;
		        }
		        return false;
		    }

			private void Accion(String txt) {
				// TODO Auto-generated method stub
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
		});
	}
}
