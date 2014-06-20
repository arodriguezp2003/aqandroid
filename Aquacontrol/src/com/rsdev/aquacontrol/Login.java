package com.rsdev.aquacontrol;




import static com.rsdev.aquacontrol.utils.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.rsdev.aquacontrol.utils.CommonUtilities.EXTRA_MESSAGE;
import static com.rsdev.aquacontrol.utils.CommonUtilities.SENDER_ID;
import static com.rsdev.aquacontrol.utils.CommonUtilities.SERVER_URL;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rsdev.aquacontrol.utils.ServerUtilities;
import com.rsdev.aquacontrol.utils.WakeLocker;
import com.google.android.gcm.GCMRegistrar;
import com.rsdev.aquacontrol.utils.AlertDialogManager;
import com.rsdev.aquacontrol.utils.ConnectionDetector;
import com.rsdev.aquacontrol.app.AppController;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login  extends Activity{
	public static String name;
	public static String email;
	ConnectionDetector cd;
	AlertDialogManager alert = new AlertDialogManager();
	AsyncTask<Void, Void, Void> mRegisterTask;
	
	 private static String tag_json_arry = "json_array_req";
	 private static  String url = "http://aquacontrol.la/api/v1/apis/autentifica/";
	 private String TAG = "JAN";
	 private boolean gmc = true;
	 TextView errores ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
	    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy); 
		
		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(Login.this,
					"Internet Connection Error",
					"Conectese a una red de internet", false);
			// stop executing code by return
			return;
		}
		try{
		GCMRegistrar.checkDevice(this);
		  } catch (UnsupportedOperationException e) {
              Toast.makeText(getBaseContext(),"Tu Dispositivo No Acepta Notificaciones", Toast.LENGTH_SHORT).show();
              gmc = false;
		  }
		registerReceiver(mHandleMessageReceiver, new IntentFilter(
				DISPLAY_MESSAGE_ACTION));
		
	
	    
	    Button btn = (Button)findViewById(R.id.xAceptar);
	    final EditText user = (EditText)findViewById(R.id.xUsuario);
	    final EditText pass = (EditText)findViewById(R.id.xPassword);
		final ProgressDialog pDialog = new ProgressDialog(Login.this);
		errores = (TextView)findViewById(R.id.lAlerta);
		
		 btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					final String _user = user.getText().toString().trim();
					String _pass = pass.getText().toString().trim();
					if(_user.isEmpty())
					{
						errores.setText("Ingrese Datos");
						errores.setVisibility(View.VISIBLE);
						return;
					}
					if(_pass.isEmpty())
					{
						errores.setText("Ingrese Datos");
						errores.setVisibility(View.VISIBLE);
						return;
					
					}
					String url2 = url + _user.toString() + "/" + _pass.toString();
					pDialog.setMessage("Cargando...");
					pDialog.show();  
					
					JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
			                url2, null,
			                new Response.Listener<JSONObject>() {
			 
			                    @Override
			                    public void onResponse(JSONObject response) {
			                        Log.d(TAG, response.toString());
			                        try {
										String error = response.getString("error");
										int es =  Integer.parseInt(error);
										if(es==1)
										{
											
											errores.setText("El Usuario o la Contraseņa es Invalida");
											errores.setVisibility(View.VISIBLE);
									
										    pDialog.hide();
											return ;
										}
										} catch (JSONException e) {
										
										e.printStackTrace();
									}
			                       			                        
			                        pDialog.hide();
			                        name = _user;
			                        AppController.UserName = _user;
			                      
			                        	if(gmc)
			                        	{

					                        if(registrar())
					                        {
					                        	errores.setVisibility(View.GONE);
					                        	startActivity(new Intent(Login.this,MainActivity.class));
					                        }
			                        	}
			                        	else
			                        	{
			                        		startActivity(new Intent(Login.this,MainActivity.class));
			                        	}
			                    }
			                }, new Response.ErrorListener() {
			 
			                    @Override
			                    public void onErrorResponse(VolleyError error) {
			                    	errores.setText("Es necesario una conexion a internet");
									errores.setVisibility(View.VISIBLE);
			                        pDialog.hide();
			                    }
			                }){
						 @Override
						   public Map<String, String> getHeaders() throws AuthFailureError 
						   {
				                HashMap<String, String> headers = new HashMap<String, String>();
				                headers.put("Content-Type", "application/json");
				                headers.put("Authorization", getB64Auth("android", "1"));
				                return headers;
				            }
						  private String getB64Auth (String login, String pass) 
						  {
					    	   String source=login+":"+pass;
					    	   String ret="Basic "+Base64.encodeToString(source.getBytes(),Base64.URL_SAFE|Base64.NO_WRAP);
					    	   return ret;
					      }
					};

			// Adding request to request queue
			AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_arry);
				}
			});
		
	}
	
	private boolean registrar()
	{
		
		GCMRegistrar.checkManifest(this);
		
		// Get GCM registration id
		final String regId = GCMRegistrar.getRegistrationId(this);
		
		// Check if regid already presents
		if (regId.equals("")) {
			// Registration is not present, register now with GCM			
			GCMRegistrar.register(this, SENDER_ID);
			return false;
		} else {
			// Device is already registered on GCM
			String serverUrl = SERVER_URL + "/" +  name + "/" + regId;
			ServerUtilities.GETS(serverUrl);
			
			if (GCMRegistrar.isRegisteredOnServer(this)) {
				// Skips registration.				
		//		Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();
			} else {
				// Try to register again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(),
				// hence the use of AsyncTask instead of a raw thread.
				final Context context = this;
				mRegisterTask = new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						// Register on our server
						// On server creates a new user
						try {
							ServerUtilities.register(context, name, email, regId);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						startActivity(new Intent(Login.this,MainActivity.class));
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						mRegisterTask = null;
					}

				};
				mRegisterTask.execute(null, null, null);
			}
		}
		return true;
	}
	
	
	/**
	 * Receiving push messages
	 * */
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());
			
			/**
			 * Take appropriate action on this message
			 * depending upon your app requirement
			 * For now i am just displaying it on the screen
			 * */
			
			// Showing received message
			//lblMessage.append(newMessage + "\n");			
			//Toast.makeText(getApplicationContext(), "New Message: " + newMessage, Toast.LENGTH_LONG).show();
			AppController.Mensaje= newMessage;
			// Releasing wake lock
			WakeLocker.release();
		}
	};
	
	@Override
	protected void onDestroy() {
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		try {
			unregisterReceiver(mHandleMessageReceiver);
			GCMRegistrar.onDestroy(this);
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}
}
