package com.rsdev.blueflamewms;

import com.android.volley.toolbox.ImageLoader;
import com.rsdev.blueflamewms.app.AppController;
import com.rsdev.blueflamewms.sql.DatabaseHandler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class menu extends Activity {
	 ProgressDialog loading;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lmenu);
		
		 final ImageView img = (ImageView)findViewById(R.id.xImage);
		 
		 if(AppController.AVATAR==null) return;
		 
		 TextView Username = (TextView)findViewById(R.id.xUsername);
		 Username.setText(AppController.USERNAME);
		 TextView last = (TextView)findViewById(R.id.xLastLog);
		 last.setText(AppController.LASTLOGIN);
		 
		 TextView cliente = (TextView)findViewById(R.id.xcodCliente);
		 TextView Inventario = (TextView)findViewById(R.id.xNint);
		 
		 
		 cliente.setText(AppController.CLIENTE.getNombre());
		 Inventario.setText(AppController.INVENTARIO);
		 ImageLoader imageLoader = AppController.getInstance().getImageLoader();
		 imageLoader.get(AppController.AVATAR, ImageLoader.getImageListener(
	                img, R.drawable.ico_loading, R.drawable.ico_error));
		 
		 
		 
		 Button Logout = (Button)findViewById(R.id.Logout);
		 Logout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DatabaseHandler data = new DatabaseHandler(menu.this);
				data.EliminarTodo();
				menu.this.finish();
				AppController.FINISH =true;
				
			}
		});
		 Button Articulos = (Button)findViewById(R.id.btnArticulos);
		 
		 Articulos.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(menu.this, ListadoArticulos.class));
			}
		});
	}
	 @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 if (keyCode == KeyEvent.KEYCODE_BACK) {
		     //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
		     return true;
		     }
		     return super.onKeyDown(keyCode, event);    
	}
}
