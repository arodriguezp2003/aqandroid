package com.rsdev.blueflamewms;

import com.rsdev.blueflamewms.app.AppController;
import com.rsdev.blueflamewms.sql.Articulos;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class detalle_articulo extends Activity {
	private TextView sku ;
	private TextView codcor ;
	private TextView nombre ;
	private TextView um ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detalle_articulo);
		
		sku = (TextView)findViewById(R.id.daSKU);
		codcor = (TextView)findViewById(R.id.daCodCor);
		nombre = (TextView)findViewById(R.id.daNombre);
		um = (TextView)findViewById(R.id.daUm);
		
		Articulos art =  AppController.ARTICULO;
		
		sku.setText(art.getSku());
		codcor.setText(art.getCodcor());
		nombre.setText(art.getDescripcion());
		um.setText(art.getIdunidad());
	}
}
