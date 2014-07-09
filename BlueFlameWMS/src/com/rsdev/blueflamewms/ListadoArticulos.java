package com.rsdev.blueflamewms;


import java.util.ArrayList;
import java.util.List;

import com.rsdev.blueflamewms.app.AppController;
import com.rsdev.blueflamewms.sql.Articulos;
import com.rsdev.blueflamewms.sql.ArticulosAdapter;
import com.rsdev.blueflamewms.sql.BaseHandler;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ListadoArticulos extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.listado_articulos);
    	BaseHandler base = new BaseHandler(ListadoArticulos.this);
    	
    	ListView view = (ListView)findViewById(R.id.list_Articulos);
    	Resources res =getResources();
    	List<Articulos> art = new ArrayList<Articulos>();
    	art =  base.ArticulosList("");
	    final ArticulosAdapter adapter = new ArticulosAdapter(this,art,res);
		view.setAdapter(adapter);
		
		view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Articulos art = (Articulos) adapter.getItem(position);
				AppController.ARTICULO =art;
				
				startActivity(new Intent(ListadoArticulos.this,detalle_articulo.class));
				
				
			}
			
		});
    }
}
