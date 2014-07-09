package com.rsdev.blueflamewms.sql;

import java.util.Collections;
import java.util.List;

import com.android.volley.BuildConfig;
import com.rsdev.blueflamewms.R;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ArticulosAdapter extends BaseAdapter {
	
	private Activity activity;
	public Resources res;
	private List<Articulos> articulos = Collections.emptyList();
	private static LayoutInflater inflater=null;
	
	public ArticulosAdapter(Activity a, List<Articulos> d,Resources resLocal) {
        activity = a;
        articulos=d;
        res = resLocal;
        inflater = ( LayoutInflater )activity.
                                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);   
}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return articulos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return articulos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	public void updateClientes(List<Articulos> articulos) {
		// TODO Auto-generated method stub
		ThreadPreconditions.checkOnMainThread();
		   this.articulos = articulos;
		   notifyDataSetChanged();
	}
	 public static class ThreadPreconditions {
		   public static void checkOnMainThread() {
		       if (BuildConfig.DEBUG) {
		           if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
		               throw new IllegalStateException("Este metodo deberia ser llamado por el hilo principal");
		           }
		       }
		   }
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;
        ViewHolder holder;
        Articulos tempValues;
        if(convertView==null)
        {
       	    vi = inflater.inflate(R.layout.item_articulo, null);
       	    holder = new ViewHolder();
            holder.sku = (TextView) vi.findViewById(R.id.arSku);
            holder.nombre=(TextView)vi.findViewById(R.id.arNombre);
            
            vi.setTag( holder );
        }
        else 
            holder=(ViewHolder)vi.getTag();
        if(articulos.size()<=0)
        {
            holder.sku.setText("No Data");

        }
        else
        {
			tempValues=null;
			tempValues = articulos.get(position);
			holder.sku.setText(tempValues.getSku());
			holder.nombre.setText(tempValues.getDescripcion());
        }
        return vi;
	}
	   public static class ViewHolder{
	        
	        public TextView nombre;
	        public TextView sku;

	 
	    }
}
