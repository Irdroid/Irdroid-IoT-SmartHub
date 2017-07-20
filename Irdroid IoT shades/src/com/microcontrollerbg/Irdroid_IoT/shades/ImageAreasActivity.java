package com.microcontrollerbg.Irdroid_IoT.shades;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.microcontrollerbg.Irdroid_IoT.shades.R;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.SystemClock;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.Html;
import android.text.InputType;
import android.text.format.Formatter;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnLongClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

/**
 * This activity displays an image on the screen. 
 * The image has three different regions that can be clicked / touched.
 * When a region is touched, the activity changes the view to show a different
 * image.
 *
 */

public class ImageAreasActivity extends Activity 
    implements View.OnTouchListener 
{

/**
 * Create the view for the activity.
 *
 */
	private Vibrator myVib;
	TcpSocketChannel schannel;
	private String LIRCIP;
	 private String type = "_irdroid._tcp.local.";
	    private JmDNS jmdns = null;
	    private ServiceListener listener = null;
	    private ServiceInfo serviceInfo;
	    private String LOGTAG = getClass().getSimpleName();
	LircClient client = null;
	String Remote = "Irdroid_IoT_Blinds";
	String Code;
	boolean runned;
	 private GestureDetector gestureDetector;
	 private String m_Text = "";
	 private Handler mHandler = new Handler();
	 boolean lastmode = false;
	// channel names
	 String channel1_name;
	 String channel2_name;
	 String channel3_name;
	 String channel4_name;
	 String channel5_name;
	 String channel6_name;
	 String channel7_name;
	 String channel8_name;
	 String channel9_name;
	 String channel10_name;
	 
	 String Blinds_up 		=		"00000000000005d0 00 BLINDS_UP";
	 String Blinds_down     = 		"0000000000000a90 00 BLINDS_DOWN";
	 String Blinds_stop     =		"0000000000000e90 00 BLINDS_STOP";
	 String Blinds_program  = 	    "00000000000001d0 00 BLINDS_PROG";
	 String Slide_up        = 	    "0000000000000a80 00 SLIDE_UP";
	 String Slide_down      = 	    "0000000000000a82 00 SLIDE_DOWN";
	 String Blinds_up4      = 	    "00000000000005c0 00 BLINDS_UP4";
	 String Blinds_down4    =       "00000000000005a0 00 BLINDS_DOWN4";
	 String BLINDS_CH1      = 		 "00000000000002a0 00 BLINDS_CH1";
	 String BLINDS_CH2      = 		 "00000000000002a1 00 BLINDS_CH2";
	 String BLINDS_CH3      = 		 "00000000000002a4 00 BLINDS_CH3";
	 String BLINDS_CH4      = 		 "00000000000002a5 00 BLINDS_CH4";
	 String BLINDS_CH5      = 		 "00000000000002a6 00 BLINDS_CH5";
	 String BLINDS_CH6      = 		 "00000000000002a7 00 BLINDS_CH6";
	 String BLINDS_CH7      = 		 "00000000000002a8 00 BLINDS_CH7";
	 String BLINDS_CH8      = 		 "00000000000002a9 00 BLINDS_CH8";
	 String BLINDS_CH9      = 		 "00000000000002aa 00 BLINDS_CH9";
	 String BLINDS_CH10      = 		 "00000000000002ab 00 BLINDS_CH10";
	
	 // The locally connected variable indicates whether we are connected to the Irdroid IoT module or not.
	 android.net.wifi.WifiManager.MulticastLock lock;
	    android.os.Handler handler = new android.os.Handler();
	 boolean locally_connected;
	 boolean flag = false;
	 TextView textView;
	 ArrayAdapter<String> arrayAdapter;
	 TextView textView2;
	 TextView program;
	 String ITEM_KEY = "key";
	 WifiInfo connection;
	 boolean flag2 = false;
	 int size = 0;
	 List<android.net.wifi.ScanResult> results;
	 boolean firstrun;
	 String networkSSID ;
	 String networkPass ;
	 boolean status;
	 int channel;
	 SharedPreferences prefs;
	 WifiConfiguration wifiConfig;
	 WifiManager wifiManager;
	 WifiManager wifi;

	 public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    runned = false;
    prefs = PreferenceManager.getDefaultSharedPreferences(this); 
   
    channel = prefs.getInt("channel", 1);
    status = prefs.getBoolean("status", true);
    networkSSID = prefs.getString("networkSSID", "Irdroid-Iot");
    networkPass = prefs.getString("networkSSID", "iosysdemo");
  
    firstrun = prefs.getBoolean("firstrun", false);
    
   
    wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);    

   
 
    
    
    gestureDetector = new GestureDetector(this, new GestureListener());
   
    connectWifi wifi1 = new connectWifi(this);
	   
    wifi1.execute(null,null,null);
    
    if(wifi.isWifiEnabled()){
	//  connection = wifi.getConnectionInfo();
	// if (connection.getSSID().toString().startsWith(String.format("\"%s", "Irdroid"))){
	//	    	toast("connected!!");
	//	    	locally_connected = true;
		    	
		//    }else if (firstrun) {
		//    	locally_connected = false;
		//    	toast("Not connected!!");
		   // 	 connectWifi wifi1 = new connectWifi(this);
		    	   
		    //    wifi1.execute(null,null,null);
		//    }
	
}else{
	locally_connected = false;
	toast("Wifi not enabled!!");
//	wifi.setWifiEnabled(true);
	startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
}
  
	
   
   
   
   textView = (TextView) findViewById(R.id.location_name);
   textView2 = (TextView) findViewById(R.id.status);
   program = (TextView) findViewById(R.id.program_1);
   program.setText(Html.fromHtml(" "+" &#9632;"));

 textView.setText(channel+"/10");
 if(status){
	 textView2.setText(Html.fromHtml(" "+"&#x25B2;"));
 }else{
	 textView2.setText(Html.fromHtml(" "+"&#x25BC;")); 
 }

// toast( String.valueOf(channel));
 
    if (android.os.Build.VERSION.SDK_INT > 9) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}
    ImageView iv = (ImageView) findViewById (R.id.image);
    if (iv != null) {
       iv.setOnTouchListener (this);
     
    }
    
    
 //  wifiConfig = new WifiConfiguration();
 //   wifiConfig.SSID = String.format("\"%s\"", networkSSID);
//    wifiConfig.preSharedKey = String.format("\"%s\"", networkPass);

//    wifiManager = (WifiManager)getSystemService(WIFI_SERVICE);
   
 //   if (!wifiManager.isWifiEnabled()){
 //   	wifiManager.setWifiEnabled(true);
 //   	}
    //remember id
 
  //  int netId = wifiManager.addNetwork(wifiConfig);
  //  wifiManager.disconnect();
  //  wifiManager.enableNetwork(netId, true);
  //  wifiManager.reconnect();
    
//    toast ("Touch the screen to discover where the regions are.");
 
    myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
 //   if (wifi.connected){ 
  
    if (!firstrun){
    	AlertDialog.Builder builder = new AlertDialog.Builder(ImageAreasActivity.this);
    	builder.setTitle("Connect to Irdroid");
    	builder.setMessage("You will be redirected to the wifi settings page to connect to Irdroid");

    	       builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	    //    	   wifi.setWifiEnabled(true);
    	          	 startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
    	           }
    	       });

    	      

    	     
    	AlertDialog alert = builder.create();
    	alert.show();
    	
    	 prefs.edit().putBoolean("firstrun", true).commit();
    	 prefs.edit().putString("channel1_name", getResources().getString(R.string.channel1)).commit();
    	 prefs.edit().putString("channel2_name", getResources().getString(R.string.channel2)).commit();
    	 prefs.edit().putString("channel3_name", getResources().getString(R.string.channel3)).commit();
    	 prefs.edit().putString("channel4_name", getResources().getString(R.string.channel4)).commit();
    	 prefs.edit().putString("channel5_name", getResources().getString(R.string.channel5)).commit();
    	 prefs.edit().putString("channel6_name", getResources().getString(R.string.channel6)).commit();
    	 prefs.edit().putString("channel7_name", getResources().getString(R.string.channel7)).commit();
    	 prefs.edit().putString("channel8_name", getResources().getString(R.string.channel8)).commit();
    	 prefs.edit().putString("channel9_name", getResources().getString(R.string.channel9)).commit();
    	 prefs.edit().putString("channel10_name", getResources().getString(R.string.channel10)).commit();
    	 
    	 channel1_name = getResources().getString(R.string.channel1);
    	 channel2_name = getResources().getString(R.string.channel2);
    	 channel3_name = getResources().getString(R.string.channel3);
    	 channel4_name = getResources().getString(R.string.channel4);
    	 channel5_name = getResources().getString(R.string.channel5);
    	 channel6_name = getResources().getString(R.string.channel6);
    	 channel7_name = getResources().getString(R.string.channel7);
    	 channel8_name = getResources().getString(R.string.channel8);
    	 channel9_name = getResources().getString(R.string.channel9);
    	 channel10_name = getResources().getString(R.string.channel10);
    
    }else{
    	
    	 channel1_name = prefs.getString("channel1_name", getResources().getString(R.string.channel1));
    	 channel2_name = prefs.getString("channel2_name", getResources().getString(R.string.channel2));
    	 channel3_name = prefs.getString("channel3_name", getResources().getString(R.string.channel3));
    	 channel4_name = prefs.getString("channel4_name", getResources().getString(R.string.channel4));
    	 channel5_name = prefs.getString("channel5_name", getResources().getString(R.string.channel5));
    	 channel6_name = prefs.getString("channel6_name", getResources().getString(R.string.channel6));
    	 channel7_name = prefs.getString("channel7_name", getResources().getString(R.string.channel7));
    	 channel8_name = prefs.getString("channel8_name", getResources().getString(R.string.channel8));
    	 channel9_name = prefs.getString("channel9_name", getResources().getString(R.string.channel9));
    	 channel10_name = prefs.getString("channel10_name", getResources().getString(R.string.channel10));
    }
   
    
}
	 
	 private void setUp() {
		 
	        new Thread(new Runnable() {
	 
	            @Override
	            public void run() {
	                android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) getSystemService(android.content.Context.WIFI_SERVICE);
	                 
	                /*Allows an application to receive 
	                Wifi Multicast packets. Normally the Wifi stack 
	                filters out packets not explicitly addressed to 
	                this device. Acquiring a MulticastLock will cause 
	                the stack to receive packets addressed to multicast
	                addresses. Processing these extra packets can 
	                cause a noticable battery drain and should be 
	                disabled when not needed. */
	                lock = wifi.createMulticastLock(getClass().getSimpleName());
	                 
	                /*Controls whether this is a reference-counted or 
	                non-reference- counted MulticastLock. 
	                Reference-counted MulticastLocks keep track of the 
	                number of calls to acquire() and release(), and 
	                only stop the reception of multicast packets when 
	                every call to acquire() has been balanced with a 
	                call to release(). Non-reference- counted 
	                MulticastLocks allow the reception of multicast 
	                packets whenever acquire() is called and stop 
	                accepting multicast packets whenever release() is 
	                called.*/
	                lock.setReferenceCounted(false);
	                 
	                try {
	                    InetAddress addr = getLocalIpAddress();
	                    String hostname = addr.getHostName();
	                    lock.acquire();
	                    Log.d(LOGTAG, "Addr : " + addr);
	                    Log.d(LOGTAG, "Hostname : " + hostname);
	                    jmdns = JmDNS.create(addr, hostname);
	                    listener = new ServiceListener() {
	 
	                        /*
	                         * Note:This event is only the service added event. The
	                         * service info associated with this event does not
	                         * include resolution information.
	                         */
	                        @Override
	                        public void serviceAdded(ServiceEvent event) {
	                            /*
	                             * Request service information.
	                                                         * The information about the service is requested and the
	                             * ServiceListener.resolveService method is called
	                             * as soon as it is available.
	                             */
	                            jmdns.requestServiceInfo(event.getType(),
	                            event.getName(), 1000);
	                        }
	 
	                        /*
	                         * A service has been resolved. Its details are now
	                         * available in the ServiceInfo record.
	                         */
	                        @SuppressWarnings("deprecation")
							@Override
	                        public void serviceResolved(ServiceEvent ev) {
	                            Log.d(LOGTAG, "Service resolved: " + ev.getInfo().getQualifiedName() + " port:" + ev.getInfo().getPort()  + " IP:" + ev.getInfo().getInetAddress());
	                            LIRCIP = ev.getInfo().getInet4Address().toString().substring(1);
	                            System.out.println(LIRCIP);
	                         //   toast(LIRCIP);
	                            try {
	                        		client = new LircClient(LIRCIP,8765, true, 3000);
	                        		
	                        	} catch (Exception e) {
	                        		// TODO Auto-generated catch block
	                        		
	                        		e.printStackTrace();
	                        	}
	                            Log.d(LOGTAG, "Service Type : " + ev.getInfo().getType());
	                        }
	 
	                        @Override
	                        public void serviceRemoved(ServiceEvent ev) {
	                            Log.d(LOGTAG, "Service removed: " + ev.getName());
	                        }
	 
	                    };
	                    jmdns.addServiceListener(type, listener);
	 
	                    /*
	                     * Advertising a JmDNS Service 
	 
	                                         * Construct a service description for registering with JmDNS. 
	                     * Parameters: 
	                     * type : fully qualified service type name, such as _dynamix._tcp.local
	                     * name : unqualified service instance name, such as DynamixInstance 
	                     * port : the local port on which the service runs text string describing the service
	                     * text : text describing the service
	                     */
	                 //   serviceInfo = ServiceInfo.create(type,
	                  //      "DynamixInstance", 7433,
	                  //      "Service Advertisement for Ambient Dynamix");
	 
	                    /*A Key value map that can be advertised with the service*/
	               //     serviceInfo.setText(getDeviceDetailsMap());
	                //    jmdns.registerService(serviceInfo);
	                //    Log.d(LOGTAG, "Service Type : " + serviceInfo.getType());
	                //    Log.d(LOGTAG, "Service Registration thread complete");
	                } catch (IOException e) {
	                    e.printStackTrace();
	                    return;
	                }
	            }
	        }).start();
	 
	    }
	 
	 private Map <String, String> getDeviceDetailsMap() {
	        Map <String, String> info = new HashMap <String, String> ();
	        info.put("device_name", "my_device_name");
	        return info;
	    }
	 
	 private InetAddress getLocalIpAddress() {
	        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
	        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
	        int ipAddress = wifiInfo.getIpAddress();
	        InetAddress address = null;
	        try {
	            address = InetAddress.getByName(String.format(Locale.ENGLISH,
	                "%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff)));
	        } catch (UnknownHostException e) {
	            e.printStackTrace();
	        }
	        return address;
	    }
	 
public boolean onPrepareOptionsMenu(Menu menu) {
    super.onPrepareOptionsMenu(menu);

    for (int i=1;i<11;i++){
    	
    	switch (i) {
    	case 1:
    	  	 
    	  	  menu.findItem(R.id.selecta).setTitle(channel1_name);
    	  	   menu.findItem(R.id.selecta).setChecked(true);
    	  	
    	  	
    	  	break;
    	  case 2:
    	  	 
    	  	   menu.findItem(R.id.selectb).setTitle(channel2_name);
    	  	   menu.findItem(R.id.selectb).setChecked(true);
    	  	  
    	  	break;
    	  case 3:
    	  	 
    	  	   menu.findItem(R.id.selectc).setTitle(channel3_name);
    	  	   menu.findItem(R.id.selectc).setChecked(true);
    	  	   
    	  	   break; 
    	  case 4:
    		
    		  menu.findItem(R.id.selectd).setTitle(channel4_name);
    		   menu.findItem(R.id.selectd).setChecked(true);
    		
    		break;
    	case 5:
    		 
    		  menu.findItem(R.id.selecte).setTitle(channel5_name);
    		   menu.findItem(R.id.selecte).setChecked(true);
    		  
    		break;
    	case 6:
    		
    		  menu.findItem(R.id.selectf).setTitle(channel6_name);
    		   menu.findItem(R.id.selectf).setChecked(true);
    		   
    		   
    	case 7:
    		 
    		  menu.findItem(R.id.selectg).setTitle(channel7_name);
    		   menu.findItem(R.id.selectg).setChecked(true);
    		
    		break;
    	case 8:
    		
    		   menu.findItem(R.id.selecth).setTitle(channel8_name);
    		   menu.findItem(R.id.selecth).setChecked(true);
    		  
    		break;
    	case 9:
    		 
    		   menu.findItem(R.id.selecti).setTitle(channel9_name);
    		   menu.findItem(R.id.selecti).setChecked(true);
    	  	  
    	  break;
    	  
    	  
    	case 10:
    		 
    		   menu.findItem(R.id.selectj).setTitle(channel10_name);
    		   menu.findItem(R.id.selectj).setChecked(true);
    		  
    	break;
    	}
    
    }
    switch (channel){
    
    case 1:
    	  Code = BLINDS_CH1;
    	  Transmit();
    	  menu.findItem(R.id.selecta).setTitle(channel1_name);
    	   menu.findItem(R.id.selecta).setChecked(true);
    	
    	
    	break;
    case 2:
    	  Code = BLINDS_CH2;
    	   Transmit();
    	   menu.findItem(R.id.selectb).setTitle(channel2_name);
    	   menu.findItem(R.id.selectb).setChecked(true);
    	  
    	break;
    case 3:
    	  Code = BLINDS_CH3;
    	   Transmit();
    	   menu.findItem(R.id.selectc).setTitle(channel3_name);
    	   menu.findItem(R.id.selectc).setChecked(true);
    	   
    	   break; 
    case 4:
  	  Code = BLINDS_CH4;
  	  Transmit();
  	  menu.findItem(R.id.selectd).setTitle(channel4_name);
  	   menu.findItem(R.id.selectd).setChecked(true);
  	
  	break;
  case 5:
  	   Code = BLINDS_CH5;
  	   Transmit();
  	  menu.findItem(R.id.selecte).setTitle(channel5_name);
  	   menu.findItem(R.id.selecte).setChecked(true);
  	  
  	break;
  case 6:
  	  Code = BLINDS_CH6;
  	   Transmit();
  	  menu.findItem(R.id.selectf).setTitle(channel6_name);
  	   menu.findItem(R.id.selectf).setChecked(true);
  	   
  	   
  case 7:
	  Code = BLINDS_CH7;
	  Transmit();
	  menu.findItem(R.id.selectg).setTitle(channel7_name);
	   menu.findItem(R.id.selectg).setChecked(true);
	
	break;
case 8:
	  Code = BLINDS_CH8;
	   Transmit();
	   menu.findItem(R.id.selecth).setTitle(channel8_name);
	   menu.findItem(R.id.selecth).setChecked(true);
	  
	break;
case 9:
	  Code = BLINDS_CH9;
	   Transmit();
	   menu.findItem(R.id.selecti).setTitle(channel9_name);
	   menu.findItem(R.id.selecti).setChecked(true);
    	  
    break;
    
    
case 10:
	  Code = BLINDS_CH10;
	   Transmit();
	   menu.findItem(R.id.selectj).setTitle(channel10_name);
	   menu.findItem(R.id.selectj).setChecked(true);
  	  
  break;
  
case 11:
	//  Code = BLINDS_CH3;
	//   Transmit();
	
	  
break;
    }
    //Also you can do this for sub menu
   
    return true;
}
public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_main, menu);
 
    
 
    
  switch (channel){
    
    case 1:
    	//  Code = BLINDS_CH1;
    	//  Transmit();
    	  menu.findItem(R.id.selecta).setTitle(channel1_name);
    	   menu.findItem(R.id.selecta).setChecked(true);
    	 
    	
    	break;
    case 2:
    	//  Code = BLINDS_CH2;
    	 //  Transmit();
    	  menu.findItem(R.id.selectb).setTitle(channel2_name);
    	   menu.findItem(R.id.selectb).setChecked(true);
    	  
    	break;
    case 3:
    	//  Code = BLINDS_CH3;
    	//   Transmit();
    	  menu.findItem(R.id.selectc).setTitle(channel3_name);
    	   menu.findItem(R.id.selectc).setChecked(true);
    	  
    break;
    
    case 4:
    	//  Code = BLINDS_CH1;
    	//  Transmit();
    	  menu.findItem(R.id.selectd).setTitle(channel4_name);
    	   menu.findItem(R.id.selectd).setChecked(true);
    	
    	break;
    case 5:
    	//  Code = BLINDS_CH2;
    	 //  Transmit();
    	  menu.findItem(R.id.selecte).setTitle(channel5_name);
    	   menu.findItem(R.id.selecte).setChecked(true);
    	  
    	break;
    case 6:
    	//  Code = BLINDS_CH3;
    	//   Transmit();
    	  menu.findItem(R.id.selectf).setTitle(channel6_name);
    	   menu.findItem(R.id.selectf).setChecked(true);
    	  
    break;
    
    case 7:
    	//  Code = BLINDS_CH1;
    	//  Transmit();
    	  menu.findItem(R.id.selectg).setTitle(channel7_name);
    	   menu.findItem(R.id.selectg).setChecked(true);
    	
    	break;
    case 8:
    	//  Code = BLINDS_CH2;
    	 //  Transmit();
    	  menu.findItem(R.id.selecth).setTitle(channel8_name);
    	   menu.findItem(R.id.selecth).setChecked(true);
    	  
    	break;
    case 9:
    	//  Code = BLINDS_CH3;
    	//   Transmit();
    	  menu.findItem(R.id.selecti).setTitle(channel9_name);
    	   menu.findItem(R.id.selecti).setChecked(true);
    	  
    break;
    
    case 10:
    	//  Code = BLINDS_CH3;
    	//   Transmit();
    	  menu.findItem(R.id.selectj).setTitle(channel10_name);
    	   menu.findItem(R.id.selectj).setChecked(true);
    	  
    break;
    case 11:
    	//  Code = BLINDS_CH3;
    	//   Transmit();
    
    	  
    	  
    break;
    }
  
    return true;
}

private void dialogue (){
AlertDialog.Builder builder = new AlertDialog.Builder(this);
builder.setTitle("Rename channel");

// Set up the input
final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
input.setInputType(InputType.TYPE_CLASS_TEXT);
builder.setView(input);

// Set up the buttons
builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
    @Override
    public void onClick(DialogInterface dialog, int which) {
        m_Text = input.getText().toString();
        
        switch (channel) {
        
        case 1:
        	channel1_name = m_Text;
        	prefs.edit().putString("channel1_name", channel1_name).commit();
        	break;
        	
        case 2:
        	channel2_name = m_Text;
        	prefs.edit().putString("channel2_name", channel2_name).commit();
        	break;
        	
        case 3:
        	channel3_name = m_Text;
        	prefs.edit().putString("channel3_name", channel3_name).commit();
        	break;
        	
        case 4:
        	channel4_name = m_Text;
        	prefs.edit().putString("channel4_name", channel4_name).commit();
        	break;
        	
        case 5:
        	channel5_name = m_Text;
        	prefs.edit().putString("channel5_name", channel5_name).commit();
        	break;
        	
        case 6:
        	channel6_name = m_Text;
        	prefs.edit().putString("channel6_name", channel6_name).commit();
        	break;
        	
        case 7:
        	channel7_name = m_Text;
        	prefs.edit().putString("channel7_name", channel7_name).commit();
        	break;
        	
        case 8:
        	channel8_name = m_Text;
        	prefs.edit().putString("channel8_name", channel8_name).commit();
        	break;
        	
        case 9:
        	channel9_name = m_Text;
        	prefs.edit().putString("channel9_name", channel9_name).commit();
        	break;
        	
        case 10:
        	channel10_name = m_Text;
        	prefs.edit().putString("channel10_name", channel10_name).commit();
        	break;
        }
       
     
    }
});
builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }
});

builder.show();
}
public boolean onOptionsItemSelected(MenuItem item) {
	  switch (item.getItemId()) {
	        case R.id.selecta:
	        if (lastmode){
	        	channel=1;
	        	dialogue();
	        
	        	 
	        	 
	        	
	        	 
		         
	        	return true;
	        	
	        }else{
	        	
	        
	         item.setChecked(true);
	         item.setTitle(channel1_name);
	         channel=1;
	         Toast.makeText(getApplicationContext(), 
	           "Channel 1 selected", 
	           Toast.LENGTH_LONG).show();
	           Code = BLINDS_CH1;
         	   Transmit();
         	
         	  textView.setText(channel+"/10");
         	 prefs.edit().putInt("channel", 1).commit();
	         return true;
	        }
	        case R.id.selectb:
	        	
	        	 if (lastmode){
	 	        	channel=2;
	 	        	dialogue();
	 	        
	 	        	 
	 	        	 
	 	        	
	 	        	 
	 		         
	 	        	return true;
	 	        	
	 	        }else{	
	         item.setChecked(true);
	         channel=2;
	         Toast.makeText(getApplicationContext(), 
	           "Channel 2 selected", 
	           Toast.LENGTH_LONG).show();
	         Code = BLINDS_CH2;
       	   Transmit();
       	prefs.edit().putInt("channel", 2).commit();
        textView.setText(channel+"/10");
	         return true;
	 	        }
	        case R.id.selectc:
	        	 if (lastmode){
	 	        	channel=3;
	 	        	dialogue();
	 	        
	 	        	 
	 	        	 
	 	        	
	 	        	 
	 		         
	 	        	return true;
	 	        	
	 	        }else{
	         item.setChecked(true);
	         channel = 3;
	         Toast.makeText(getApplicationContext(), 
	           "Channel 3 selected", 
	           Toast.LENGTH_LONG).show();
	         Code = BLINDS_CH3;
	         Transmit();
	         prefs.edit().putInt("channel", 3).commit();
	         textView.setText(channel+"/10");
	         return true;
	 	        }
	        case R.id.selectd:
	        	 if (lastmode){
	 	        	channel=4;
	 	        	dialogue();
	 	        
	 	        	 
	 	        	 
	 	        	
	 	        	 
	 		         
	 	        	return true;
	 	        	
	 	        }else{
		         item.setChecked(true);
		         channel = 4;
		         Toast.makeText(getApplicationContext(), 
		           "Channel 4 selected", 
		           Toast.LENGTH_LONG).show();
		         Code = BLINDS_CH4;
		         Transmit();
		         prefs.edit().putInt("channel", 4).commit();
		         textView.setText(channel+"/10");
		         return true;
	 	        }
	        case R.id.selecte:
	        	 if (lastmode){
	 	        	channel=5;
	 	        	dialogue();
	 	        
	 	        	 
	 	        	 
	 	        	
	 	        	 
	 		         
	 	        	return true;
	 	        	
	 	        }else{
		         item.setChecked(true);
		         channel = 5;
		         Toast.makeText(getApplicationContext(), 
		           "Channel 5 selected", 
		           Toast.LENGTH_LONG).show();
		         Code = BLINDS_CH5;
		         Transmit();
		         prefs.edit().putInt("channel", 5).commit();
		         textView.setText(channel+"/10");
		         return true;
	 	        }
	        case R.id.selectf:
	        	 if (lastmode){
	 	        	channel=6;
	 	        	dialogue();
	 	        
	 	        	 
	 	        	 
	 	        	
	 	        	 
	 		         
	 	        	return true;
	 	        	
	 	        }else{
		         item.setChecked(true);
		         channel = 6;
		         Toast.makeText(getApplicationContext(), 
		           "Channel 6 selected", 
		           Toast.LENGTH_LONG).show();
		         Code = BLINDS_CH6;
		         Transmit();
		         prefs.edit().putInt("channel", 6).commit();
		         textView.setText(channel+"/10");
		         return true;
		         
	 	        }
	        case R.id.selectg:
	        	 if (lastmode){
	 	        	channel=7;
	 	        	dialogue();
	 	        
	 	        	 
	 	        	 
	 	        	
	 	        	 
	 		         
	 	        	return true;
	 	        	
	 	        }else{
		         item.setChecked(true);
		         channel = 7;
		         Toast.makeText(getApplicationContext(), 
		           "Channel 7 selected", 
		           Toast.LENGTH_LONG).show();
		         Code = BLINDS_CH7;
		         Transmit();
		         prefs.edit().putInt("channel", 7).commit();
		         textView.setText(channel+"/10");
		         return true;
	 	        }
		         
	        case R.id.selecth:
	        	 if (lastmode){
	 	        	channel=8;
	 	        	dialogue();
	 	        
	 	        	 
	 	        	 
	 	        	
	 	        	 
	 		         
	 	        	return true;
	 	        	
	 	        }else{
		         item.setChecked(true);
		         channel = 8;
		         Toast.makeText(getApplicationContext(), 
		           "Channel 8 selected", 
		           Toast.LENGTH_LONG).show();
		         Code = BLINDS_CH8;
		         Transmit();
		         prefs.edit().putInt("channel", 8).commit();
		         textView.setText(channel+"/10");
		         return true;
	 	        }
	        case R.id.selecti:
	        	 if (lastmode){
	 	        	channel=9;
	 	        	dialogue();
	 	        
	 	        	 
	 	        	 
	 	        	
	 	        	 
	 		         
	 	        	return true;
	 	        	
	 	        }else{
		         item.setChecked(true);
		         channel = 9;
		         Toast.makeText(getApplicationContext(), 
		           "Channel 9 selected", 
		           Toast.LENGTH_LONG).show();
		         Code = BLINDS_CH9;
		         Transmit();
		         prefs.edit().putInt("channel", 9).commit();
		         textView.setText(channel+"/10");
		         return true;
	 	        }
	        case R.id.selectj:
	        	 if (lastmode){
	 	        	channel=10;
	 	        	dialogue();
	 	        
	 	        	 
	 	        	 
	 	        	
	 	        	 
	 		         
	 	        	return true;
	 	        	
	 	        }else{
		         item.setChecked(true);
		         channel = 10;
		         Toast.makeText(getApplicationContext(), 
		           "Channel 10 selected", 
		           Toast.LENGTH_LONG).show();
		         Code = BLINDS_CH10;
		         Transmit();
		         prefs.edit().putInt("channel", 10).commit();
		         textView.setText(channel+"/10");
		         return true;
	 	        }
		         
	        case R.id.menu_settings:
	        	if (item.isChecked()){
	        		
	        		item.setChecked(false);
	        		Toast.makeText(getApplicationContext(), 
	 	 		           "Edit mode disabled!", 
	 	 		           Toast.LENGTH_LONG).show();
	        		lastmode = false;
	        	}else{
	        		item.setChecked(true);
	        		Toast.makeText(getApplicationContext(), 
	 	 		           "Edit mode active!", 
	 	 		           Toast.LENGTH_LONG).show();
	        		lastmode = true;
	        	}
	        
	        	
	        
	        
	        
	        	 
	        	 return false;
	        default:
	            return super.onOptionsItemSelected(item);    
	  }
	 }
/**
 * Respond to the user touching the screen.
 * Change images to make things appear and disappear from the screen.
 *
 */    
public boolean onKeyDown(int keyCode, KeyEvent event) {
    if( keyCode == KeyEvent.KEYCODE_VOLUME_UP || 
        keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_POWER )
    {
        event.startTracking();
        return true;
    }
    return super.onKeyDown(keyCode, event);
}

@Override
public boolean onKeyLongPress(int keyCode, KeyEvent event)
{
    if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
    	toast ("long press volup");
    	mHandler.postAtTime(volup,  
				   SystemClock.uptimeMillis() + 400);
        return true;
    }
    else if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
    	mHandler.postAtTime(voldown,  
				   SystemClock.uptimeMillis() + 400);
    	toast ("long press vol down");
        return true;
    }
    
    return super.onKeyLongPress(keyCode, event);
}

@Override
public boolean onKeyUp(int keyCode, KeyEvent event)
{
	  mHandler.removeCallbacks(volup);	
	  mHandler.removeCallbacks(voldown);	
	if((event.getFlags() & KeyEvent.FLAG_CANCELED_LONG_PRESS) == 0){
        if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
        	 myVib.vibrate(20);  
        	Code = Slide_up;
           	   Transmit();
            return true;
        }
        else if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
        	  myVib.vibrate(20);
        	  
           	 
         	   Code = Slide_down;
         	   Transmit();
            return true;
        }
    }
    return super.onKeyUp(keyCode, event);
}


private String getIP() {
	 try {
	   WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
	   WifiInfo wifiInfo = wifiManager.getConnectionInfo();
	   int ipAddress = wifiInfo.getIpAddress();
	   return String.format(Locale.getDefault(), "%d.%d.%d.%d",
	   (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
	   (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
	 } catch (Exception ex) {
	
	   return null;
	 }
	}

private String get_apname() {
	 try {
	   WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
	   WifiInfo wifiInfo = wifiManager.getConnectionInfo();
	   String Apname = wifiInfo.getSSID();
	   return Apname;
	 } catch (Exception ex) {
	
	   return null;
	 }
	}
public boolean onTouch (View v, MotionEvent ev) 
{
    boolean handledHere = false;
   
 
    final int action = ev.getAction();

    final int evX = (int) ev.getX();
    final int evY = (int) ev.getY();
    int nextImage = -1;			// resource id of the next image to display

    // If we cannot find the imageView, return.
    ImageView imageView = (ImageView) v.findViewById (R.id.image);
    if (imageView == null) return false;

    // When the action is Down, see if we should show the "pressed" image for the default image.
    // We do this when the default image is showing. That condition is detectable by looking at the
    // tag of the view. If it is null or contains the resource number of the default image, display the pressed image.
    Integer tagNum = (Integer) imageView.getTag ();
    int currentResource = (tagNum == null) ? R.drawable.p2_ship_default : tagNum.intValue ();

    // Now that we know the current resource being displayed we can handle the DOWN and UP events.

    switch (action) {
    case MotionEvent.ACTION_DOWN :
       if (currentResource == R.drawable.p2_ship_default) {
   //      nextImage = R.drawable.p2_ship_pressed;
    //	   toast ("Touch the screen to discover where the regions are.");

          handledHere = true;
       /*
       } else if (currentResource != R.drawable.p2_ship_default) {
         nextImage = R.drawable.p2_ship_default;
         handledHere = true;
       */
       } 
       
       int touchColor = getHotspotColor (R.id.image_areas, evX, evY);
       
       // Compare the touchColor to the expected values. Switch to a different image, depending on what color was touched.
       // Note that we use a Color Tool object to test whether the observed color is close enough to the real color to
       // count as a match. We do this because colors on the screen do not match the map exactly because of scaling and
       // varying pixel density.
       ColorTool ct = new ColorTool ();
       int tolerance = 25;
       nextImage = R.drawable.p2_ship_default;
       handledHere = true;
       if (ct.closeMatch (Color.RED, touchColor, tolerance)) 
    	   {
    	//   nextImage = R.drawable.tesy_timer_pushed;
    	   Code = Blinds_down;
    	   Transmit();
    	   myVib.vibrate(20);
    	   nextImage = R.drawable.p2_ship_down;
//toast("Down");
    	 
    	   textView2.setText(Html.fromHtml(" "+"&#x25BC;"));
    	   prefs.edit().putBoolean("status", false).commit();
    	     	   
    	   
    	   }
       else if (ct.closeMatch (Color.BLUE, touchColor, tolerance))
    	   {
    	//   nextImage = R.drawable.tesy_temp_pushed;  
    	   Code = Blinds_up;
    	   Transmit();
    	   myVib.vibrate(20);
    	//   nextImage = R.drawable.p2_ship_powered;
    	  // toast("Up");
    	   nextImage = R.drawable.p2_ship_up;
    	  
    	   textView2.setText(Html.fromHtml(" "+"&#x25B2;"));
    	   prefs.edit().putBoolean("status", true).commit();
    	   
    	   }
       else if (ct.closeMatch (Color.YELLOW, touchColor, tolerance)) {
    	//   nextImage = R.drawable.tesy_on_off_pushed;   
    	   Code = Blinds_stop;
    	   Transmit();
    	   myVib.vibrate(20);
    	  
    	//   toast("Stop");
    	   nextImage = R.drawable.p2_ship_stop;
    	//   nextImage = R.drawable.p2_ship_powered;
    	 
       }
       else if (ct.closeMatch (Color.WHITE, touchColor, tolerance)) {
    	   nextImage = R.drawable.p2_ship_default; //  myVib.vibrate(50);
       }
       
      
     
       else if (ct.closeMatch (Color.LTGRAY, touchColor, tolerance)) {
       	//   nextImage = R.drawable.tesy_plus_pushed;  
       	   myVib.vibrate(20);
       	 mHandler.postAtTime(prog_me,  
				   SystemClock.uptimeMillis() + 3000);
       	  
       	  
          }
       else if (ct.closeMatch (Color.BLACK, touchColor, tolerance)) {
          	//   nextImage = R.drawable.tesy_plus_pushed;  
          //   myVib.vibrate(50);
          //	   Code = Blinds_stop;
          //	   Transmit();
    	   
          	  
             }
     
       // If the next image is the same as the last image, go back to the default.
       // toast ("Current image: " + currentResource + " next: " + nextImage);
       if (currentResource == nextImage) {
        
    	   nextImage = R.drawable.p2_ship_default;
       } 
       handledHere = true; 
       break;

    case MotionEvent.ACTION_UP :
       // On the UP, we do the click action.
       // The hidden image (image_areas) has three different hotspots on it.
       // The colors are red, blue, and yellow.
       // Use image_areas to determine which region the user touched.
    	int touchColor1 = getHotspotColor (R.id.image_areas, evX, evY);
    	   int tolerance1 = 25;
    	   ColorTool ct1 = new ColorTool ();
    	  nextImage = R.drawable.p2_ship_default;
          handledHere = true;
          if (ct1.closeMatch (Color.BLACK, touchColor1, tolerance1)) 
     	   {
          	  
          	  mHandler.removeCallbacks(volup);	  
          	  
     	   }  
       break;

    default:
       handledHere = false;
    } // end switch

    if (handledHere) {
 
       if (nextImage > 0) {
          imageView.setImageResource (nextImage);
          imageView.setTag (nextImage);
          
       }
    }
    return gestureDetector.onTouchEvent(ev);
  //  return handledHere;
}   

/**
 * Resume the activity.
 */

@Override protected void onResume() {
    super.onResume();
if (firstrun){
	 WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
     WifiInfo conn = wifi.getConnectionInfo();
     if(conn.getSSID().toString().startsWith(String.format("\"%s", "Irdroid")))
     {
    	 toast("Connected!");
     }
   
}

if(client!=null){
	connectWifi wifi1 = new connectWifi(this);

	wifi1.execute(null,null,null);
}

 
}

/**
 * Handle a click on the Wglxy views at the bottom.
 *
 */    

public void onClickWglxy (View v) {
    Intent viewIntent = new Intent ("android.intent.action.VIEW", 
                                    Uri.parse("http://double-star.appspot.com/blahti/ds-download.html"));
    startActivity(viewIntent);
    
}


/**
 */
// More methods

/**
 * Get the color from the hotspot image at point x-y.
 * 
 */

public int getHotspotColor (int hotspotId, int x, int y) {
    ImageView img = (ImageView) findViewById (hotspotId);
    if (img == null) {
       Log.d ("ImageAreasActivity", "Hot spot image not found");
       return 0;
    } else {
      img.setDrawingCacheEnabled(true); 
      Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache()); 
      if (hotspots == null) {
         Log.d ("ImageAreasActivity", "Hot spot bitmap was not created");
         return 0;
      } else {
        img.setDrawingCacheEnabled(false);
        return hotspots.getPixel(x, y);
      }
    }
}

/**
 * Show a string on the screen via Toast.
 * 
 * @param msg String
 * @return void
 */
private void Transmit() {

	Thread thread = new Thread() {
		@Override
		public void run() {

			

			
				try {
					client.sendIr1Command(Remote,
							Code, 1);
					Thread.sleep(1000);
				} catch (Exception e) {
					// TODO Auto-generated catch block
			//		toast("No connection!");
					e.printStackTrace();
				}
				
				} 
				

			

		
	};

	thread.start();

}

private final class GestureListener extends SimpleOnGestureListener {

    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        boolean result = false;
        try {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                    result = true;
                }
            }
            else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    onSwipeBottom();
                } else {
                    onSwipeTop();
                }
                result = true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }
}

public void onSwipeRight() {
	if(channel>1 && channel <= 3){
		channel = channel - 1;
	}
	
	textView.setText(channel+"/10");
	 switch (channel){
	    
	    case 1:
	    	  Code = BLINDS_CH1;
	    	  Transmit();
	    		 prefs.edit().putInt("channel", 1).commit();
	    	//   menu.findItem(R.id.selecta).setChecked(true);
	    	
	    	break;
	    case 2:
	    	  Code = BLINDS_CH2;
	    	   Transmit();
	    		 prefs.edit().putInt("channel", 2).commit();
	    	//   menu.findItem(R.id.selectb).setChecked(true);
	    	  
	    	break;
	    case 3:
	    	  Code = BLINDS_CH3;
	    	   Transmit();
	    		 prefs.edit().putInt("channel", 3).commit();
	    	//   menu.findItem(R.id.selectc).setChecked(true);
	    	  
	    break;
	    }
}

public void onSwipeLeft() {

	 
	 if(channel>=1 && channel < 3){
			channel = channel + 1;
		}
		
		textView.setText(channel+"/10");
		 switch (channel){
		    
		    case 1:
		    	  Code = BLINDS_CH1;
		    	  Transmit();
		    		 prefs.edit().putInt("channel", 1).commit();
		    	//   menu.findItem(R.id.selecta).setChecked(true);
		    	
		    	break;
		    case 2:
		    	  Code = BLINDS_CH2;
		    	   Transmit();
		    		 prefs.edit().putInt("channel", 2).commit();
		    	//   menu.findItem(R.id.selectb).setChecked(true);
		    	  
		    	break;
		    case 3:
		    	  Code = BLINDS_CH3;
		    	   Transmit();
		    		 prefs.edit().putInt("channel", 3).commit();
		    	//   menu.findItem(R.id.selectc).setChecked(true);
		    	  
		    break;
		    }
}

public void onSwipeTop() {
	 Code = Slide_up;
 	   Transmit();
 	  myVib.vibrate(20);
}

public void onSwipeBottom() {
	 Code = Slide_down;
 	   Transmit();
 	  myVib.vibrate(20);
}
private Runnable voldown= new Runnable()
{
    public void run()
    {
    	
    	
   
        myVib.vibrate(20);		
	       
        
        Code = Slide_down;
        	   Transmit();
        
      
      	try {
	
      		   	   
         	} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
						e.printStackTrace();
					}		
        mHandler.postAtTime(this, SystemClock.uptimeMillis() +
300);
        
    }
};
private Runnable volup = new Runnable()
{
    public void run()
    {
    	
    	
   
        myVib.vibrate(20);		
	       
        
        Code = Slide_up;
        	   Transmit();
        
      
      	try {
	
      		   	   
         	} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
						e.printStackTrace();
					}		
        mHandler.postAtTime(this, SystemClock.uptimeMillis() +
300);
        
    }
};

private Runnable prog_me = new Runnable()
{
    public void run()
    {
    	
    	

	   
	   
	   AlertDialog.Builder builder = new AlertDialog.Builder(ImageAreasActivity.this);
   	builder.setTitle(getResources().getString(R.string.prog_mode_label));
   	builder.setMessage(getResources().getString(R.string.prog_mode_message));

   	       builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
   	           public void onClick(DialogInterface dialog, int id) {
   	    //    	   wifi.setWifiEnabled(true);
   	        	
   	        
   	      
   	      	try {
   	      	 Code = Blinds_program;
 	    	   Transmit();
   	      		   	   
   	         	} catch (IllegalStateException e) {
   						// TODO Auto-generated catch block
   							e.printStackTrace();
   						}		
   	       
   	           }
   	       });
   	       
   	    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	    //    	   wifi.setWifiEnabled(true);
	          	dialog.dismiss();
	           }
	       });

   	      

   	     
   	AlertDialog alert = builder.create();
   	alert.show(); 
	   
	   
   
       
    }
};
public class connectWifi extends AsyncTask<Void, Void, Void> {
    private ProgressDialog dialog;
    boolean connected;
    public connectWifi(ImageAreasActivity activity) {
        dialog = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(getResources().getString(R.string.connect_message));
        dialog.show();
      
       
        
    }

    @Override
    protected void onPostExecute(Void result) {
       connected = true;
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo conn = wifi.getConnectionInfo();
        Context context = getApplicationContext();
        CharSequence wifi_on = getResources().getString(R.string.connected_to) + conn.getSSID();
        int duration = Toast.LENGTH_LONG;
        
        Toast responseToast = Toast.makeText(context, wifi_on, duration);
        responseToast.show();

        if(conn.getSSID().toString().startsWith(String.format("\"%s", "Irdroid")))
        {
       	LIRCIP = "192.168.2.1";
        try {
    		client = new LircClient(LIRCIP,8765, true, 3000);
    		
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		
    		e.printStackTrace();
    	}
        
        }  
      
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    protected Void doInBackground(Void... voids) {
    	
    	
    	
    	
    	wifiManager = (WifiManager)getSystemService(WIFI_SERVICE);
    	//System.out.println(wifiManager.getConnectionInfo().getSSID().toString());
    	//if (wifiManager.getConnectionInfo().getSSID() == networkSSID ){
    	//	System.out.println("Match!");
    		
    	//}
    	   
    	//   wifiConfig = new WifiConfiguration();
    	//   wifiConfig.SSID = String.format("\"%s\"", networkSSID);
    	//   wifiConfig.preSharedKey = String.format("\"%s\"",networkPass );
    	// startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
    	
    	   
    	    //remember id
    	 
    	 //   int netId = wifiManager.addNetwork(wifiConfig);
    	 //   wifiManager.disconnect();
    	 //   wifiManager.enableNetwork(netId, true);
    	 //   wifiManager.reconnect();
   
    	  //  WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
          //wifi.setWifiEnabled(true);
    	   	    try {
        	 if (!wifiManager.isWifiEnabled()){
        		  Thread.sleep(20000);
     	    	}else{
     	    		 Thread.sleep(2000);
     	    	}
          
        	  setUp();
        } catch (Exception e) {

        }
        return null;
    }
}

public void toast (String msg)
{
    Toast.makeText (getApplicationContext(), msg, Toast.LENGTH_SHORT).show ();
} // end toast



} // end class