/*******************************************************************************
 * Copyright (c) 1999, 2014 IBM Corp.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution. 
 *
 * The Eclipse Public License is available at 
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 *   http://www.eclipse.org/org/documents/edl-v10.php.
 */
package com.microcontrollerbg.Irdroid_IoT.shades;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;


import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import com.microcontrollerbg.Irdroid_IoT.shades.ColorTool;
import com.microcontrollerbg.Irdroid_IoT.shades.ActionListener.Action;
import com.microcontrollerbg.Irdroid_IoT.shades.Connection.ConnectionStatus;

import android.app.ActionBar;
import android.app.AlertDialog;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;
import com.microcontrollerbg.Irdroid_IoT.shades.R;
/**
 * The connection details activity operates the fragments that make up the
 * connection details screen.
 * <p>
 * The fragments which this FragmentActivity uses are
 * <ul>
 * <li>{@link HistoryFragment}
 * <li>{@link PublishFragment}
 * <li>{@link SubscribeFragment}
 * </ul>
 * 
 */
public class ConnectionDetails extends FragmentActivity implements
      View.OnTouchListener, MqttCallback {
	  public Vibrator myVib;
	  private Context context = null;
  /**
   * {@link SectionsPagerAdapter} that is used to get pages to display
   */
  SectionsPagerAdapter sectionsPagerAdapter;
  /**
   * {@link ViewPager} object allows pages to be flipped left and right
   */
  
 
  ViewBinder viewPager;
  int number;
  /** The currently selected tab **/
  private int selected = 0;
  Handler handlerTimer = null; 
  /**
   * The handle to the {@link Connection} which holds the data for the client
   * selected
   **/
  private String clientHandle = null;
  private String location = null;
  private Handler mHandler = new Handler();
  /** This instance of <code>ConnectionDetails</code> **/
  private final ConnectionDetails connectionDetails = this;
  Builder builder;
  AlertDialog asDialog;
  /**
   * The instance of that the <code>clientHandle</code>
   * represents
   **/
  private com.microcontrollerbg.Irdroid_IoT.shades.Connection connection = null;

  /**
   * The {@link ChangeListener} this object is using for the connection
   * updates
   **/
  private ChangeListener changeListener = null;

  /**
   * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
   */
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //Show action bar back button
    // create a client handle
    String clientHandle = location;


 //   Connection connection = new Connection(clientHandle, "WIRF1200","blinds", "hwgroup-bg.com","1", 1800,
   //     this, 1, );
  //  arrayAdapter.add(connection);

 //   connection.registerChangeListener(changeListener);
    // connect client

  //  String[] actionArgs = new String[1];
  //  actionArgs[0] = clientId;
   // connection.changeConnectionStatus(ConnectionStatus.CONNECTING);
  //  ActionBar actionBar = getActionBar();
  //  actionBar.setDisplayHomeAsUpEnabled(true);

    number =0;
    setContentView(R.layout.main);
   
    myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
    connection = Connections.getInstance(this).getConnection(clientHandle);
    changeListener = new ChangeListener();
    connection.registerChangeListener(changeListener);
    Connection c = Connections.getInstance(context).getConnection(clientHandle);
    TextView textView = (TextView) findViewById(R.id.location_name);
    textView.setText(location);
    try {
      c.getClient().connect(c.getConnectionOptions(), null, new ActionListener(context, Action.CONNECT, clientHandle, null));
    }
    catch (MqttSecurityException e) {
      Log.e(this.getClass().getCanonicalName(), "Failed to reconnect the client with the handle " + clientHandle, e);
      c.addAction("Client failed to connect");
    }
    catch (MqttException e) {
      Log.e(this.getClass().getCanonicalName(), "Failed to reconnect the client with the handle " + clientHandle, e);
      c.addAction("Client failed to connect");
    }
    
  
    
    
    
    ImageView iv = (ImageView) findViewById (R.id.image);
    if (iv != null) {
       iv.setOnTouchListener (this);
    }
  }
  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; goto parent activity.
	            this.finish();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
  @Override
  protected void onDestroy() {
    connection.removeChangeListener(null);
   
    super.onDestroy();
  }
  
  
  protected void onResume(){
	  super.onResume();
	
	
		  Connection c = Connections.getInstance(context).getConnection(clientHandle);

		    try {
		      c.getClient().connect(c.getConnectionOptions(), null, new ActionListener(context, Action.CONNECT, clientHandle, null));
		    }
		    catch (MqttSecurityException e) {
		      Log.e(this.getClass().getCanonicalName(), "Failed to reconnect the client with the handle " + clientHandle, e);
		      c.addAction("Client failed to connect");
		    }
		    catch (MqttException e) {
		      Log.e(this.getClass().getCanonicalName(), "Failed to reconnect the client with the handle " + clientHandle, e);
		      c.addAction("Client failed to connect");
		    }
	  
	  
  }

  /**
   * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    int menuID;
 
    boolean connected = Connections.getInstance(this)
        .getConnection(clientHandle).isConnected();
   
   
  
    // Select the correct action bar menu to display based on the
    // connectionStatus and which tab is selected
    if (connected) {
    	 
    	      Connections.getInstance(context).getConnection(clientHandle).getClient().setCallback(this);
    	      try {
				Connections.getInstance(context).getConnection(clientHandle).getClient().subscribe(connection.getId(),2);
			} catch (MqttSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MqttException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	       
    	  
    	   
      switch (selected) {
        case 0 : // history view
       // 	 menuID = R.menu.activity_publish;
       // 	 button = R.id.publish;
          break;
       
        default :
     //   	menuID = R.menu.activity_publish;
      //	 button = R.id.publish;
          break;
      }
    }
    else {
      switch (selected) {
        case 0 : // history view
      //  	 menuID = R.menu.activity_publish_disconnected;
          //   button = R.id.publish;
             break;
      
        default :
      //  	 menuID = R.menu.activity_publish_disconnected;
          //   button = R.id.publish;
             break;
      }
    }
    // inflate the menu selected
  //  getMenuInflater().inflate(menuID, menu);
    Listener listener = new Listener(this, clientHandle);
    // add listeners
  //  if (button != null) {
      // add listeners
    //  menu.findItem(button).setOnMenuItemClickListener(listener);
      //if (!Connections.getInstance(this).getConnection(clientHandle)
        //  .isConnected()) {
      //  menu.findItem(button).setEnabled(false);
     // }
   // }
    // add the listener to the disconnect or connect menu option
    if (connected) {
  //    menu.findItem(R.id.disconnect).setOnMenuItemClickListener(listener);
    }
    else {
   //   menu.findItem(R.id.connectMenuOption).setOnMenuItemClickListener(
    //      listener);
     
    }

    return true;
  }

  

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


	public void toast (String msg)
	{
	    Toast.makeText (getApplicationContext(), msg, Toast.LENGTH_LONG).show ();
	} // end toast


  public boolean onTouch (View v, MotionEvent ev) 
  {
      boolean handledHere = false;
      boolean connected = Connections.getInstance(this)
    	        .getConnection(clientHandle).isConnected();
number=0;
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
      	  // nextImage = R.drawable.mqtt_transmit;
      	   myVib.vibrate(20);
      	 String[] args = new String[2];
         args[0] = "ON";
         args[1] = "switch@/12"+";qos:"+0+";retained:"+0;
      	 if (!connected){
      		Connection c = Connections.getInstance(context).getConnection(clientHandle);
      	    try {
      	      c.getClient().connect(c.getConnectionOptions(), null, new ActionListener(context, Action.CONNECT, clientHandle, null));
      	    }
      	    catch (MqttSecurityException e) {
      	      Log.e(this.getClass().getCanonicalName(), "Failed to reconnect the client with the handle " + clientHandle, e);
      	      c.addAction("Client failed to connect");
      	    }
      	    catch (MqttException e) {
      	      Log.e(this.getClass().getCanonicalName(), "Failed to reconnect the client with the handle " + clientHandle, e);
      	      c.addAction("Client failed to connect");
      	    } 
      		 
      	 }
      	 try {
      	      Connections.getInstance(context).getConnection(clientHandle).getClient()
      	          .publish(connection.getId()+"@/"+connection.getchannel()+"2", "0".getBytes(), 2, false );
      	    }
      	    catch (MqttSecurityException e) {
      	      Log.e(this.getClass().getCanonicalName(), "Failed to publish a messged from the client with the handle " + clientHandle, e);
      	    }
      	    catch (MqttException e) {
      	      Log.e(this.getClass().getCanonicalName(), "Failed to publish a messged from the client with the handle " + clientHandle, e);
      	    }
      	  
      	 
      	//   Code = "timer";
      	//   Transmit();
     // 	   mCIR.transmit(38000, time);     	   
      	   
      	   }
         else if (ct.closeMatch (Color.BLUE, touchColor, tolerance))
      	   {
      	 //  nextImage = R.drawable.mqtt_transmit;  
      	   myVib.vibrate(20);
      	 String[] args = new String[2];
         args[0] = "ON";
         args[1] = "switch@/12"+";qos:"+0+";retained:"+0;
         if (!connected){
       		Connection c = Connections.getInstance(context).getConnection(clientHandle);
       	    try {
       	      c.getClient().connect(c.getConnectionOptions(), null, new ActionListener(context, Action.CONNECT, clientHandle, null));
       	    }
       	    catch (MqttSecurityException e) {
       	      Log.e(this.getClass().getCanonicalName(), "Failed to reconnect the client with the handle " + clientHandle, e);
       	      c.addAction("Client failed to connect");
       	    }
       	    catch (MqttException e) {
       	      Log.e(this.getClass().getCanonicalName(), "Failed to reconnect the client with the handle " + clientHandle, e);
       	      c.addAction("Client failed to connect");
       	    } 
       		 
       	 } 
      	 try {
      	      Connections.getInstance(context).getConnection(clientHandle).getClient()
      	          .publish(connection.getId()+"@/"+connection.getchannel()+"1", "0".getBytes(), 2, false );
      	    }
      	    catch (MqttSecurityException e) {
      	      Log.e(this.getClass().getCanonicalName(), "Failed to publish a messged from the client with the handle " + clientHandle, e);
      	    }
      	    catch (MqttException e) {
      	      Log.e(this.getClass().getCanonicalName(), "Failed to publish a messged from the client with the handle " + clientHandle, e);
      	    }
      	  
      	 //  Code = "temp";
      	//   Transmit();
      //	   mCIR.transmit(38000, temp);      
      	   }
         else if (ct.closeMatch (Color.YELLOW, touchColor, tolerance)) {
      	 //  nextImage = R.drawable.mqtt_transmit; 
      	   myVib.vibrate(20);
      	 //  Code = "power";
      	 //  Transmit();
      //	   mCIR.transmit(38000, power);   
      	 String[] args = new String[2];
         args[0] = "ON";
         args[1] = "switch@/12"+";qos:"+0+";retained:"+0;
         if (!connected){
       		Connection c = Connections.getInstance(context).getConnection(clientHandle);
       	    try {
       	      c.getClient().connect(c.getConnectionOptions(), null, new ActionListener(context, Action.CONNECT, clientHandle, null));
       	    }
       	    catch (MqttSecurityException e) {
       	      Log.e(this.getClass().getCanonicalName(), "Failed to reconnect the client with the handle " + clientHandle, e);
       	      c.addAction("Client failed to connect");
       	    }
       	    catch (MqttException e) {
       	      Log.e(this.getClass().getCanonicalName(), "Failed to reconnect the client with the handle " + clientHandle, e);
       	      c.addAction("Client failed to connect");
       	    } 
       		 
       	 } 
      	 try {
      	      Connections.getInstance(context).getConnection(clientHandle).getClient()
      	          .publish(connection.getId()+"@/"+connection.getchannel()+"1", "1".getBytes(), 2, false );
      	    }
      	    catch (MqttSecurityException e) {
      	      Log.e(this.getClass().getCanonicalName(), "Failed to publish a messged from the client with the handle " + clientHandle, e);
      	    }
      	    catch (MqttException e) {
      	      Log.e(this.getClass().getCanonicalName(), "Failed to publish a messged from the client with the handle " + clientHandle, e);
      	    }
      	  
         }
         else if (ct.closeMatch (Color.WHITE, touchColor, tolerance)) {
      	//   nextImage = R.drawable.mqtt_default;   
      	   myVib.vibrate(20);
      	   toast(location);
         }
         
         else if (ct.closeMatch (Color.CYAN, touchColor, tolerance)) {
      	//   nextImage = R.drawable.mqtt_transmit;  
      	   myVib.vibrate(20);
      	 String[] args = new String[2];
         args[0] = "ON";
         args[1] = "switch@/12"+";qos:"+0+";retained:"+0;
         if (!connected){
       		Connection c = Connections.getInstance(context).getConnection(clientHandle);
       	    try {
       	      c.getClient().connect(c.getConnectionOptions(), null, new ActionListener(context, Action.CONNECT, clientHandle, null));
       	    }
       	    catch (MqttSecurityException e) {
       	      Log.e(this.getClass().getCanonicalName(), "Failed to reconnect the client with the handle " + clientHandle, e);
       	      c.addAction("Client failed to connect");
       	    }
       	    catch (MqttException e) {
       	      Log.e(this.getClass().getCanonicalName(), "Failed to reconnect the client with the handle " + clientHandle, e);
       	      c.addAction("Client failed to connect");
       	    } 
       		 
       	 }  
      	 try {
      	          Connections.getInstance(context).getConnection(clientHandle).getClient()
      	          .publish(connection.getId()+"@/"+connection.getchannel()+"3", "1".getBytes(), 2, false );
      	    }
      	    catch (MqttSecurityException e) {
      	      Log.e(this.getClass().getCanonicalName(), "Failed to publish a messged from the client with the handle " + clientHandle, e);
      	    }
      	    catch (MqttException e) {
      	      Log.e(this.getClass().getCanonicalName(), "Failed to publish a messged from the client with the handle " + clientHandle, e);
      	    }
      	  
      	  // Code = "minus";
      	//   Transmit();
      //	   mCIR.transmit(38000, minus);     
         }
         
         else if (ct.closeMatch (Color.MAGENTA, touchColor, tolerance)) {
      	 //  nextImage = R.drawable.mqtt_transmit; 
      	   myVib.vibrate(20);
      	//   Code = "plus";
      	//   Transmit();
     // 	   mCIR.transmit(38000, plus);     
      	 String[] args = new String[2];
         args[0] = "ON";
         args[1] = "switch@/12"+";qos:"+0+";retained:"+0;
         if (!connected){
       		Connection c = Connections.getInstance(context).getConnection(clientHandle);
       	    try {
       	      c.getClient().connect(c.getConnectionOptions(), null, new ActionListener(context, Action.CONNECT, clientHandle, null));
       	    }
       	    catch (MqttSecurityException e) {
       	      Log.e(this.getClass().getCanonicalName(), "Failed to reconnect the client with the handle " + clientHandle, e);
       	      c.addAction("Client failed to connect");
       	    }
       	    catch (MqttException e) {
       	      Log.e(this.getClass().getCanonicalName(), "Failed to reconnect the client with the handle " + clientHandle, e);
       	      c.addAction("Client failed to connect");
       	    } 
       		 
       	 } 
      	 try {
      	      Connections.getInstance(context).getConnection(clientHandle).getClient()
      	          .publish(connection.getId()+"@/"+connection.getchannel()+"2", "1".getBytes(), 2, false );
      	    }
      	    catch (MqttSecurityException e) {
      	      Log.e(this.getClass().getCanonicalName(), "Failed to publish a messged from the client with the handle " + clientHandle, e);
      	    }
      	    catch (MqttException e) {
      	      Log.e(this.getClass().getCanonicalName(), "Failed to publish a messged from the client with the handle " + clientHandle, e);
      	    }
      	  
      	  
         }
         
         else if (ct.closeMatch (Color.GREEN, touchColor, tolerance)) {
        	 //  nextImage = R.drawable.mqtt_transmit;  
        	   myVib.vibrate(20);
        	//   Code = "plus";
        	//   Transmit();
       // 	   mCIR.transmit(38000, plus);     
        	 String[] args = new String[2];
           args[0] = "ON";
           args[1] = "switch@/12"+";qos:"+0+";retained:"+0;
           if (!connected){
         		Connection c = Connections.getInstance(context).getConnection(clientHandle);
         	    try {
         	      c.getClient().connect(c.getConnectionOptions(), null, new ActionListener(context, Action.CONNECT, clientHandle, null));
         	    }
         	    catch (MqttSecurityException e) {
         	      Log.e(this.getClass().getCanonicalName(), "Failed to reconnect the client with the handle " + clientHandle, e);
         	      c.addAction("Client failed to connect");
         	    }
         	    catch (MqttException e) {
         	      Log.e(this.getClass().getCanonicalName(), "Failed to reconnect the client with the handle " + clientHandle, e);
         	      c.addAction("Client failed to connect");
         	    } 
         		 
         	 }   
        	 try {
        	      Connections.getInstance(context).getConnection(clientHandle).getClient()
        	          .publish(connection.getId()+"@/"+connection.getchannel()+"3", "0".getBytes(), 2, false );
        	    }
        	    catch (MqttSecurityException e) {
        	      Log.e(this.getClass().getCanonicalName(), "Failed to publish a messged from the client with the handle " + clientHandle, e);
        	    }
        	    catch (MqttException e) {
        	      Log.e(this.getClass().getCanonicalName(), "Failed to publish a messged from the client with the handle " + clientHandle, e);
        	    }
        	  
        	  
           }
         
         else if (ct.closeMatch (Color.LTGRAY, touchColor, tolerance)) {
      	 //  nextImage = R.drawable.mqtt_transmit; 
      	   myVib.vibrate(20);
      	//   Code = "plus";
      	//   Transmit();
     // 	   mCIR.transmit(38000, plus);     
      	 String[] args = new String[2];
         args[0] = "ON";
         args[1] = "switch@/12"+";qos:"+0+";retained:"+0;
         if (!connected){
       		Connection c = Connections.getInstance(context).getConnection(clientHandle);
       	    try {
       	      c.getClient().connect(c.getConnectionOptions(), null, new ActionListener(context, Action.CONNECT, clientHandle, null));
       	    }
       	    catch (MqttSecurityException e) {
       	      Log.e(this.getClass().getCanonicalName(), "Failed to reconnect the client with the handle " + clientHandle, e);
       	      c.addAction("Client failed to connect");
       	    }
       	    catch (MqttException e) {
       	      Log.e(this.getClass().getCanonicalName(), "Failed to reconnect the client with the handle " + clientHandle, e);
       	      c.addAction("Client failed to connect");
       	    } 
       		 
       	 }  
      	 try {
      	      Connections.getInstance(context).getConnection(clientHandle).getClient()
      	          .publish(connection.getId()+"@/"+connection.getchannel()+"4", "1".getBytes(), 2, false );
      	    }
      	    catch (MqttSecurityException e) {
      	      Log.e(this.getClass().getCanonicalName(), "Failed to publish a messged from the client with the handle " + clientHandle, e);
      	    }
      	    catch (MqttException e) {
      	      Log.e(this.getClass().getCanonicalName(), "Failed to publish a messged from the client with the handle " + clientHandle, e);
      	    }
      	  
      	  
         }
         
         else if (ct.closeMatch (Color.BLACK, touchColor, tolerance)) {
      	 //  nextImage = R.drawable.mqtt_transmit;  
myVib.vibrate(20);
      	//   Code = "plus";
      	//   Transmit();
     // 	   mCIR.transmit(38000, plus);     
      	 String[] args = new String[2];
         args[0] = "ON";
         args[1] = "switch@/12"+";qos:"+0+";retained:"+0;
         if (!connected){
       		Connection c = Connections.getInstance(context).getConnection(clientHandle);
       	    try {
       	      c.getClient().connect(c.getConnectionOptions(), null, new ActionListener(context, Action.CONNECT, clientHandle, null));
       	    }
       	    catch (MqttSecurityException e) {
       	      Log.e(this.getClass().getCanonicalName(), "Failed to reconnect the client with the handle " + clientHandle, e);
       	      c.addAction("Client failed to connect");
       	    }
       	    catch (MqttException e) {
       	      Log.e(this.getClass().getCanonicalName(), "Failed to reconnect the client with the handle " + clientHandle, e);
       	      c.addAction("Client failed to connect");
       	    } 
       		 
       	 }  
      	 try {
      	      Connections.getInstance(context).getConnection(clientHandle).getClient()
      	          .publish(connection.getId()+"@/"+connection.getchannel()+"4", "0".getBytes(), 2, false );
      	    }
      	    catch (MqttSecurityException e) {
      	      Log.e(this.getClass().getCanonicalName(), "Failed to publish a messged from the client with the handle " + clientHandle, e);
      	    }
      	    catch (MqttException e) {
      	      Log.e(this.getClass().getCanonicalName(), "Failed to publish a messged from the client with the handle " + clientHandle, e);
      	    }
      	  
      	  
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
      	  nextImage = R.drawable.p2_ship_default;
            handledHere = true;
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
      return handledHere;
  }   
  /**
   * Provides the Activity with the pages to display for each tab
   * 
   */
  public class SectionsPagerAdapter extends FragmentPagerAdapter {

    // Stores the instances of the pages
    private ArrayList<Fragment> fragments = null;

    /**
     * Only Constructor, requires a the activity's fragment managers
     * 
     * @param fragmentManager
     */
    public SectionsPagerAdapter(FragmentManager fragmentManager) {
      super(fragmentManager);
     

    }

    /**
     * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
     */
    @Override
    public Fragment getItem(int position) {
      return fragments.get(position);
    }

    /**
     * @see android.support.v4.view.PagerAdapter#getCount()
     */
    @Override
    public int getCount() {
      return fragments.size();
    }

    /**
     * 
     * @see FragmentPagerAdapter#getPageTitle(int)
     */
    @Override
    public CharSequence getPageTitle(int position) {
      switch (position) {
        case 0 :
                   return getString(R.string.publish).toUpperCase();
      }
      // return null if there is no title matching the position
      return null;
    }

  }

  /**
   * <code>ChangeListener</code> updates the UI when the {@link Connection}
   * object it is associated with updates
   * 
   */
  private class ChangeListener implements PropertyChangeListener {

    /**
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    @Override
    public void propertyChange(PropertyChangeEvent event) {
      // connection object has change refresh the UI

      connectionDetails.runOnUiThread(new Runnable() {

        @Override
        public void run() {
          connectionDetails.invalidateOptionsMenu();
         // ((PublishFragment) connectionDetails.sectionsPagerAdapter
        //      .getItem(0)).refresh();

        }
      });

    }
  }

@Override
public void connectionLost(Throwable arg0) {
	// TODO Auto-generated method stub



//if(Connection.ConnectionStatus.MANUAL_DISCONNECT != null){
//	
//}else{
	Connection c = Connections.getInstance(context).getConnection(clientHandle);
   try {
    
    c.getClient().connect(c.getConnectionOptions(), null, new ActionListener(context, Action.CONNECT, clientHandle, null));
 }
  catch (MqttSecurityException e) {
    Log.e(this.getClass().getCanonicalName(), "Failed to reconnect the client with the handle " + clientHandle, e);
    c.addAction("Client failed to connect");
 }
  catch (MqttException e) {
   Log.e(this.getClass().getCanonicalName(), "Failed to reconnect the client with the handle " + clientHandle, e);    c.addAction("Client failed to connect");
  }
	
//}
	

}

@Override
public void deliveryComplete(IMqttDeliveryToken arg0) {
	// TODO Auto-generated method stub
	
}
private Runnable heartbeat = new Runnable()
{
    public void run()
    {
    	
  //  	 ImageView iv = (ImageView) findViewById (R.id.image);
  // //	     iv.setImageResource (R.drawable.mqtt_default);	
      
        
    }
};
public static boolean isInteger(String s) {
    return isInteger(s,10);
}

public static boolean isInteger(String s, int radix) {
    if(s.isEmpty()) return false;
    for(int i = 0; i < s.length(); i++) {
        if(i == 0 && s.charAt(i) == '-') {
            if(s.length() == 1) return false;
            else continue;
        }
        if(Character.digit(s.charAt(i),radix) < 0) return false;
    }
    return true;
}

public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
	  ImageView iv = (ImageView) findViewById (R.id.image);
//		number=number + 1;
	  System.out.println(arg1.toString());
	
	 

	  
	  if (arg1.toString().equals("ok")){
		//	if (number==1){
			   
		
		
				
				builder = new Builder(this);
				builder.setTitle("Message from device");
				builder.setMessage("Status: " + arg1.toString());
				
				builder.setNegativeButton("Dismiss",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});

				
			   	asDialog = builder.create();
			

			if (asDialog != null && !asDialog.isShowing()) {
		        //then show your dialog..else not 
		  
				
			  	asDialog.show();  
			  
			  //	return;
			
			//}
			
				
			
			}
				
				

		}
	  if (arg1.toString().equals("alive")){
	//  iv.setImageResource (R.drawable.mqtt_transmit);
	  mHandler.postAtTime(heartbeat,SystemClock.uptimeMillis() + 400);
	  }

	 
}
}