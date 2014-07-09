package com.rsdev.blueflamewms.sql;



import static android.provider.BaseColumns._ID;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BaseHandler extends SQLiteOpenHelper  {
	private Articulos articulo = new Articulos();
	
	public BaseHandler(Context context) {
		super(context, "BaseWMS", null, 1);
	
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		this.DropArticulos();
		db.execSQL(articulo.CreateDB());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	public List<Articulos> ArticulosList(String where)
	{
		List<Articulos> art =  new ArrayList<Articulos>();;
		String[] w = {""};
		SQLiteDatabase db = this.getWritableDatabase();
		if(where.length()!=0)
		{
			w[0] = " where " + where; 
		}
		Cursor c = null;
		
		if(where.length()==0)
		{
			String[] Columnas = {"id","idcliente","codcor","sku","descripcion","idunidad","activo"};
			c= this.getReadableDatabase().query("ARTICULOS", Columnas, null, null, null,null,null);
		}
		else
		{
			c= db.rawQuery("SELECT * FROM articulos ? ", w);
		}
		if (c.moveToFirst()) {
		  
		     do {
		          Articulos a = new Articulos();
		          a.setId(c.getString(0));
		          a.setIdcliente(c.getString(1));
		          a.setCodcor(c.getString(2));
		          a.setSku(c.getString(3));
		          a.setDescripcion(c.getString(4));
		          a.setIdunidad(c.getString(5));
		          a.setActivo(c.getString(6));
		          
		          art.add(a);
		     } while(c.moveToNext());
		}
		return art;
	}
	
	
	public void CreateArticulos(int id, int cliente, String codcor, String sku, String descripcion,int unidad, int activo )
	{
		ContentValues valores = new ContentValues();
		valores.put("id", id);
		valores.put("idcliente", cliente);
		valores.put("codcor", codcor);
		valores.put("sku", sku);
		valores.put("descripcion", descripcion);
		valores.put("idunidad", unidad);
		valores.put("activo", activo);
		this.getWritableDatabase().insert("articulos",null, valores);
	}
	public void DropArticulos()
	{
		try {
				String sql = "DROP TABLE IF EXISTS ARTICULOS";
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(sql);
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("RS", e.toString());
		}
	
	}
	public void EliminarTodo()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DELETE FROM ARTICULOS");
	}	

}
