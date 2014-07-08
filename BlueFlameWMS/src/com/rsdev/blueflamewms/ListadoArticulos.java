package com.rsdev.blueflamewms;


import com.rsdev.blueflamewms.sql.ArticulosAdapter;
import com.rsdev.blueflamewms.sql.BaseHandler;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
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
	    final ArticulosAdapter adapter = new ArticulosAdapter(this, base.ArticulosList(""),res);
		view.setAdapter(adapter);
    }
}
