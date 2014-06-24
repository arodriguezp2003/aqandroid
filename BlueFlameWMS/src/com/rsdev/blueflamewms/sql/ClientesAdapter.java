package com.rsdev.blueflamewms.sql;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

public class ClientesAdapter extends ArrayAdapter<Cliente>
{

	public ClientesAdapter(Context context, int resource, List<Cliente> objects) {
		super(context, resource, objects);
	}
	
	
	
}
