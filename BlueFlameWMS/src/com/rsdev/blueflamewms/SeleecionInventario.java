package com.rsdev.blueflamewms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rsdev.blueflamewms.app.AppController;
import com.rsdev.blueflamewms.sql.Articulos;
import com.rsdev.blueflamewms.sql.Cliente;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SeleecionInventario extends Activity {
	private String URL ="http://inventario.arodriguezp.com/api/v1/apis/inventario/"; 
	private String URLART ="http://inventario.arodriguezp.com/api/v1/apis/articulos/";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seleecione_inventario);
		
		final TextView  cliente = (TextView)findViewById(R.id.xClienteSI);
		final TextView  alerta = (TextView)findViewById(R.id.lAlertaSI);
		final TextView  inventario = (TextView)findViewById(R.id.xInventarioSI);
		Button Aceptar = (Button)findViewById(R.id.xAceptarSI);
		
		cliente.setText("Cliente: " +AppController.CLIENTE.getNombre());
		
		
		
		Aceptar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(inventario.getText().length()==0)
				{
					alerta.setText("Ingrese Numero de Inventario");
					alerta.setVisibility(View.VISIBLE);
					return;
				}
				alerta.setVisibility(View.GONE);
				
				
                String url2 = URL + AppController.CLIENTE.getId() + "/" + inventario.getText().toString();
				
				JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
		                url2, null,
		                new Response.Listener<JSONObject>() {
		 
		                    @Override
		                    public void onResponse(JSONObject response) {
		                        try {
									String errors = response.getString("error");
									int es =  Integer.parseInt(errors);
									if(es==1)
									{
									
										alerta.setText("El Numero de Inventario No Existe");
										alerta.setVisibility(View.VISIBLE);
										return ;
									}
									if(es==2)
									{
									
										alerta.setText("El Numero de Inventario No se Encuentra Activo");
										alerta.setVisibility(View.VISIBLE);
										return ;
									}
									} catch (JSONException e) {
									
									e.printStackTrace();
								}
		                        AppController.INVENTARIO = inventario.getText().toString();
		                        Avanzar();
		                        
		                       
                      
		                 }

							private void Avanzar() {
								// TODO Auto-generated method stub
								final ProgressDialog loading;
								loading = new ProgressDialog(SeleecionInventario.this);
								loading.setTitle("Espere un Momento");
						 	    loading.setMessage("Descargando SKU");
						 	    loading.show(); 
						 	   String url2 = URLART + AppController.CLIENTE.getId();
						 	   JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
						                url2, null,
						                new Response.Listener<JSONObject>() {
						 
						                    @Override
						                    public void onResponse(JSONObject response) {
						                    	
						                    	
						     						JSONArray cx;
													try {
														cx = response.getJSONArray("articulos");
														for (int i = 0; i < cx.length(); i++) {
							                             	JSONObject c = cx.getJSONObject(i);
							                             	Articulos art = new Articulos();
							                             }
													} catch (JSONException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
						     					
						                             
						                       
						                    	 loading.hide(); 
						                    	startActivity(new Intent(SeleecionInventario.this,menu.class));
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
								AppController.getInstance().addToRequestQueue(jsonObjReq, "jan");
								//
								 
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
				String tag_json_arry = "";
				AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_arry);
				
			}
		});
	}
}
