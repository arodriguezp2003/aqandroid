package com.rsdev.aquacontrol;

import static com.rsdev.aquacontrol.utils.CommonUtilities.SENDER_ID;
import static com.rsdev.aquacontrol.utils.CommonUtilities.displayMessage;

import java.io.IOException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.rsdev.aquacontrol.app.AppController;
import com.rsdev.aquacontrol.utils.ServerUtilities;

public class GCMIntentService extends GCMBaseIntentService {

	private static final String TAG = "GCMIntentService";

    public GCMIntentService() {
        super(SENDER_ID);
    }

    /**
     * Method called on device registered
     **/
    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
        displayMessage(context, "Your device registred with GCM");
       // Log.d("NAME", MainActivity.name);
        try {
			ServerUtilities.register(context, Login.name, Login.email, registrationId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * Method called on device un registred
     * */
    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
        displayMessage(context, getString(R.string.gcm_unregistered));
        try {
			ServerUtilities.unregister(context, registrationId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * Method called on Receiving a new message
     * */
    @Override
    protected void onMessage(Context context, Intent intent) {
        Log.i(TAG, "Received message");
        String message = intent.getExtras().getString("price");
        
        displayMessage(context, message);
        // notifies user
        generateNotification(context, message);
    }

    /**
     * Method called on receiving a deleted message
     * */
    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
        String message = getString(R.string.gcm_deleted, total);
        displayMessage(context, message);
        // notifies user
        generateNotification(context, message);
    }

    /**
     * Method called on Error
     * */
    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
        displayMessage(context, getString(R.string.gcm_error, errorId));
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        displayMessage(context, getString(R.string.gcm_recoverable_error,
                errorId));
        return super.onRecoverableError(context, errorId);
    }

    /**
     * Issues a notification to inform the user that server has sent a message.
     */
   
	private static void generateNotification(Context context, String message) {
    	NotificacionNueva(context,message);   	
    }
    private static void NotificacionNueva(Context context, String message) 
    {
    	 int MY_NOTIFICATION_ID=1;
    	 NotificationManager notificationManager;
    	 Notification myNotification;
    	 
    	 Intent myIntent =new Intent(context, MainActivity.class); 
        if(AppController.UserName.isEmpty())
        {
        	myIntent= new Intent(context, Login.class);
        }
        else
        {
        	myIntent= new Intent(context, MainActivity.class);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(
               context, 
               0, 
               myIntent, 
               Intent.FLAG_ACTIVITY_NEW_TASK);
        
        myNotification = new NotificationCompat.Builder(context)
          .setContentTitle("AquaControl.la!")
          .setContentText(message)
          .setTicker("AquaControl: " + message)
          .setWhen(System.currentTimeMillis())
          .setContentIntent(pendingIntent)
          .setDefaults(Notification.DEFAULT_SOUND)
          .setAutoCancel(true)
          .setSmallIcon(R.drawable.ic_launcher)
          .build();
        
        notificationManager = 
          (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(MY_NOTIFICATION_ID, myNotification);
    	
    }

}
