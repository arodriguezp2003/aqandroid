package com.rsdev.blueflamewms;


import com.rsdev.blueflamewms.app.AppController;
import com.rsdev.blueflamewms.sql.Cliente;
import com.rsdev.blueflamewms.sql.ClientesAdapter;


import android.app.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;


import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


public class SeleccionCliente extends Activity {

		
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(AppController.FINISH) SeleccionCliente.this.finish();
	}
	 @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 if (keyCode == KeyEvent.KEYCODE_BACK) {
		     //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
		     return true;
		     }
		     return super.onKeyDown(keyCode, event);    
	}
	@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.seleccioncliente);
			
		    
		    ListView view = (ListView)findViewById(R.id.sc_Clientes);
		    Resources res =getResources();
		    final ClientesAdapter adapter = new ClientesAdapter(this, AppController.CLIENTES,res);
			view.setAdapter(adapter);
			
			view.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Cliente cli = adapter.getItem(position);
					
					AppController.CLIENTE = cli;
					
					startActivity(new Intent(SeleccionCliente.this,SeleecionInventario.class));	
					
				}
			});
		   
		}
}

