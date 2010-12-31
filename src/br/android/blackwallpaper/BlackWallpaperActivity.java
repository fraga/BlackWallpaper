package br.android.blackwallpaper;

import java.io.IOException;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

public class BlackWallpaperActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	//we don't need to reload the saved instance
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        
        AlertDialog alert = new AlertDialog.Builder(this).create();
        
        alert.setTitle(R.string.app_name);
        alert.setMessage("This will change your current background to pure black background. Are You sure?");
        
        alert.setButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					changeBackground();
					createNotification("Black Wallpaper was set! Enjoy.");
					
				} catch (Exception e) {
					
				}
				
			}
		});
        
        alert.setButton2("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				createNotification("There was a problem setting your wallpaper.");
				getParent().getParent().finish();
			}
		});
        alert.show();
        
    }
    
    public void changeBackground() throws IOException {
    	WallpaperManager wm = (WallpaperManager)getSystemService(WALLPAPER_SERVICE);
    	
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        
        Bitmap backBitmap = Bitmap.createBitmap(metrics.widthPixels, metrics.heightPixels, Bitmap.Config.ARGB_4444);
   
        wm.setBitmap(backBitmap);
    }
    
    public void createNotification(String message)
    {
    	Intent notificationIntent = new Intent(this, BlackWallpaperActivity.class);
    	
    	
    	NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    	Notification mNotification = new Notification(R.drawable.icon, "BlackBackground", System.currentTimeMillis());
    	
    	mNotification.setLatestEventInfo(	this, 
    										"Black Wallpaper", 
    										message, 
    										PendingIntent.getActivity(	this, 
    																	0, 
    																	notificationIntent, 
    																	PendingIntent.FLAG_CANCEL_CURRENT)
    										);
    	
    	nm.notify(0, mNotification);
    	
    }
    
}