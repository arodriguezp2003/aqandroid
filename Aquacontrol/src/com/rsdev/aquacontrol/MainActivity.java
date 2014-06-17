package com.rsdev.aquacontrol;

import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.rsdev.aquacontrol.app.AppController;

import io.socket.*;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.os.Handler;

public class MainActivity extends Activity {
	 private SocketIO socket;
	 private TextView txt;
	 private TextView txt2;
	 TextView errores ;
	 private Handler handler = new Handler();
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	 	if(!AppController.Mensaje.isEmpty())
    	{
    		
    		errores.setText(AppController.Mensaje);
    		errores.setVisibility(View.VISIBLE);
    		AppController.Mensaje="";
    	}
    	else
    	{
    		errores.setVisibility(View.GONE);
    	}
	} 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_main);
    	txt = (TextView)findViewById(R.id.xTexto);
    	txt2 = (TextView)findViewById(R.id.xTexto2);  
    	errores = (TextView)findViewById(R.id.lMensaje);
    	
   
    
		try {
			socket = new SocketIO("http://realtime.aquacontrol.la/");
		
         socket.connect(new IOCallback() {
             @Override
             public void onMessage(JSONObject json, IOAcknowledge ack) {
                 try {
                     System.out.println("Server said:" + json.toString(2));
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
             }

             @Override
             public void onMessage(String data, IOAcknowledge ack) {
                 System.out.println("Server said: " + data);
             }

             @Override
             public void onError(SocketIOException socketIOException) {
                 System.out.println("an Error occured");
                 socketIOException.printStackTrace();
             }

             @Override
             public void onDisconnect() {
                 System.out.println("Connection terminated.");
             }

             @Override
             public void onConnect() {
                 System.out.println("Connection established");
             }

             @SuppressLint("CutPasteId") @Override
             public void on(String event, IOAcknowledge ack, Object... args) {
                 System.out.println( event );
                
                 if ("sensor".equals(event) && args.length > 0) {
                	 try {
						JSONObject ob = new JSONObject(args[0].toString());
						final  String name = ob.getString("sensor");
						final  String value =  ob.getString("valor");
							new Thread(new Runnable() {
								public void run() {
								
									handler.post(new Runnable() {
										public void run() 
										{
											if(name != null) 
											{
												if(name.trim().contains("T1"))
												{
													txt.setText(value.substring(0,5) + " C�");
												}
												if(name.trim().contains("OX1"))
												{ 
													txt2.setText(value + " mg/L");
												}
												
												
											}
										}
								});
								}
							}).start();
						 
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                	
                	
                    
                 
                 }
				
			
             }
         });
         socket.send("Hello Server!");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

         // This line is cached until the connection is establisched.
       
    }

	public void setText(String arg)
	{
		txt.setText(arg);
	}
}
