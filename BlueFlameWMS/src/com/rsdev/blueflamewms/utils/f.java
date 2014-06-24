package com.rsdev.blueflamewms.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.content.Context;
import android.util.Base64;
import android.util.Log;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rsdev.blueflamewms.app.AppController;
import com.rsdev.blueflamewms.sql.Cliente;
import com.rsdev.blueflamewms.sql.ClientesAdapter;
public class f 
{
	public  List<Cliente> list;
	public ClientesAdapter ClienteAdapter;
	
	private Context context;
	
	public Context getContext()
	{
	    return this.context;
	}

	public void setContext(Context context)
	{	  
	    this.context = context;
	}	
	
	public void ListarClientes()
	{
		Log.d("rs", "INITIAL");
		String Url = "http://inventario.arodriguezp.com/api/v1/apis/clientes/";
		String tag_json_arry ="json_array_req";
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
                Url, null,
                new Response.Listener<JSONObject>() {
 
                    @Override
                    public void onResponse(JSONObject response) 
                    {
	                   Log.d("rs", response.toString());
	                   try {
						JSONArray cx = response.getJSONArray("clientes");
						list = new ArrayList<Cliente>();
                        for (int i = 0; i < cx.length(); i++) {
                        	JSONObject c = cx.getJSONObject(i);
                        	Cliente cli = new Cliente();
                        	cli.setId(c.getInt("id"));
                        	cli.setNombre(c.getString("nombre"));
                        	cli.setRut(c.getString("rut"));
                        	cli.setFantasia(c.getString("fantasia"));
                        	
                        	list.add(cli);
                        }
                        AppController.CLIENTES = list;
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                   
                    }
                }, new Response.ErrorListener() {
 
                    @Override
                    public void onErrorResponse(VolleyError err) {
                     
                    }
                }){
			 @Override
			  public Map<String, String> getHeaders() throws AuthFailureError 
			   {
	                HashMap<String, String> headers = new HashMap<String, String>();
	                headers.put("Content-Type", "application/json");
	                headers.put("Authorization",  getB64Auth("superadmin@mail.com", "Ar9844609"));
	                return headers;
	            }
			  private String getB64Auth (String login, String pass) 
			  {
		    	   String source=login+":"+pass;
		    	   String ret="Basic "+Base64.encodeToString(source.getBytes(),Base64.URL_SAFE|Base64.NO_WRAP);
		    	   return ret;
		      }
			};
		AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_arry);
	}
}
