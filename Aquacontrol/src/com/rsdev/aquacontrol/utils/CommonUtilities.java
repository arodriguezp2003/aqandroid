package com.rsdev.aquacontrol.utils;

import android.content.Context;
import android.content.Intent;

public final class CommonUtilities {
	
	// give your server registration url here
    public static final String SERVER_URL = "http://www.aquacontrol.la/api/v1/apis/registrar"; 

    // Google project id
    public static  String SENDER_ID = "1090733239494"; 

    /**
     * Tag used on log messages.
     */
    static final String TAG = "AndroidHive GCM";

    public static  String DISPLAY_MESSAGE_ACTION =
            "com.rsdev.aquacontrol.utils.DISPLAY_MESSAGE";

    public static  String EXTRA_MESSAGE = "message";

    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    public static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}
