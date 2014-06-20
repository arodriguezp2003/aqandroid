package com.rsdev.blueflamewms;



import com.rsdev.blueflamewms.sql.DatabaseHandler;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.database.Cursor;

public class MainActivity extends Activity {
	 private static String tag_json_arry = "json_array_req";
	 private static  String URL = "http://aquacontrol.la/api/v1/apis/autentifica/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_main);
    	 StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
 	     StrictMode.setThreadPolicy(policy); 
 		 
 	     DatabaseHandler data = new DatabaseHandler(this);
 	  
 	     data.EliminarTodo();
 	     data.GuardarEstado("Alejandro", "1.jpg");
 	     int dt = data.Estado();
 	   
 	
    }
}