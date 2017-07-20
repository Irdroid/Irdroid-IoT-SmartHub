package com.microcontrollerbg.Irdroid_IoT.shades;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * This is the Splash activity which is loaded when the application is invoked
 */
public class SplashActivity extends Activity
{
	// Set the display time, in milliseconds (or extract it out as a configurable parameter)
	private final int SPLASH_DISPLAY_LENGTH = 2600;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
	//	WifiManager wifiManager = (WifiManager)getSystemService(WIFI_SERVICE);
		
	//	wifiManager.setWifiEnabled(true);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		ImageView view = (ImageView)findViewById(R.id.imageview1);
		 Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		  view.startAnimation(myFadeInAnimation); //Set animation to your ImageView
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		// Obtain the sharedPreference, default to true if not available
		boolean isSplashEnabled = sp.getBoolean("isSplashEnabled", true);

		if (isSplashEnabled)
		{
			new Handler().postDelayed(new Runnable()
			{
				public void run()
				{
					// Finish the splash activity so it can't be returned to.

					SplashActivity.this.finish();
					// Create an Intent that will start the main activity.
					Intent mainIntent = new Intent(SplashActivity.this, ImageAreasActivity.class);
					SplashActivity.this.startActivity(mainIntent);
					overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				}
			}, SPLASH_DISPLAY_LENGTH);
		}
		else
		{
			// if the splash is not enabled, then finish the activity immediately and go to main.
			finish();
			Intent mainIntent = new Intent(SplashActivity.this, ImageAreasActivity.class);
			SplashActivity.this.startActivity(mainIntent);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		}
	}
}