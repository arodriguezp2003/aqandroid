package com.rsdev.blueflamewms;



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
import com.rsdev.blueflamewms.sql.DatabaseHandler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;


public class MainActivity extends Activity {
	 private static String tag_json_arry = "json_array_req";
	 private static  String URL = "http://inventario.arodriguezp.com/api/v1/apis/autentifica/";
	 private DatabaseHandler datas;
	 EditText user,pass;
	 TextView error;
	 Button login ;
	 ProgressDialog loading;
	 
	 @Override
	protected void onResume() {
		super.onResume();
	   if(datas.Estado()==1) startActivity(new Intent(MainActivity.this,menu.class));
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
   
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_main);
    	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
 	    StrictMode.setThreadPolicy(policy); 
 		 
 	    datas = new DatabaseHandler(this);
 	    datas.drop();
 	    if(datas.Estado()==1) startActivity(new Intent(MainActivity.this,menu.class));
 	    
 	    user  = (EditText)findViewById(R.id.xUsuario);
 	    pass  = (EditText)findViewById(R.id.xPassword);
 	    login = (Button)findViewById(R.id.xAceptar);
 	    error = (TextView)findViewById(R.id.lAlerta);
 	    loading = new ProgressDialog(MainActivity.this);
 	    loading.setMessage("Espere un momento");
 	    
 	    login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(user.getText().toString().isEmpty())
				{
					error.setText("Ingrese Datos");
					error.setVisibility(View.VISIBLE);
					return;
				}
				if(pass.getText().toString().isEmpty())
				{
					error.setText("Ingrese Datos");
					error.setVisibility(View.VISIBLE);
					return;
				}
				
				loading.show();
				String url2 = URL + user.getText().toString() + "/" + pass.getText().toString();
				
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
									
										error.setText("El Usuario o la Contraseña es Invalida");
										error.setVisibility(View.VISIBLE);
								
									    loading.hide();
										return ;
									}
									} catch (JSONException e) {
									
									e.printStackTrace();
								}
		                        try {
									JSONArray cx = response.getJSONArray("usuario");
									JSONObject c = cx.getJSONObject(0);
									if(c.getString("avatar").toString()!=null)
									{
										AppController.AVATAR  =AppController.RutaImgAvatar  +   c.getString("avatar").toString();
									}
									else
									{
										AppController.AVATAR =null;
									}
									String data = c.getString("first_name").toString() + " " + c.getString("last_name").toString();
									AppController.USERNAME = data;
									AppController.LASTLOGIN = c.getString("last_login").toString();
									AppController.FINISH = false;
									datas.GuardarEstado(AppController.USERNAME, AppController.AVATAR,AppController.LASTLOGIN  );
								} catch (JSONException e) {
						
								}
		                        
		                        loading.hide();
		                      //  startActivity(new Intent(MainActivity.this,menu.class));		                        
		                        startActivity(new Intent(MainActivity.this,SeleccionCliente.class));	
		                        
		                       		                      
		                 }
		                }, new Response.ErrorListener() {
		 
		                    @Override
		                    public void onErrorResponse(VolleyError err) {
		                    	error.setText("Es necesario una conexion a internet");
		                    	error.setVisibility(View.VISIBLE);
		                        loading.hide();
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
		});
    }
}
