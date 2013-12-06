package com.example.werewolf_client;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import restClient.MafiaRestClient;

import com.littlefluffytoys.littlefluffylocationlibrary.LocationInfo;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationLibrary;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationLibraryConstants;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameStatusActivity extends Activity {

	private static final String TAG = "debug";
	TextView tv1;
	Button mButton;
	ImageView img;
	private String username;
	private String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_status);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			username = extras.getString("username");
			password = extras.getString("password");
		}
		
		tv1 = (TextView) findViewById(R.id.textView1);
		mButton = (Button) findViewById(R.id.button1);
		img = (ImageView) findViewById(R.id.imageView1);

		MafiaRestClient.setBasicAuth(username, password);
		MafiaRestClient.get("getGameState/" + username + "/" + password, null, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				if(response.equals("day")){
					img.setImageResource(R.drawable.sun);
				}else{
					img.setImageResource(R.drawable.werewolf_moon);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_status, menu);
		return true;
	}

	@Override
	public void onResume() {
		super.onResume();

		refreshDisplay();

		// This demonstrates how to dynamically create a receiver to listen to the location updates.
		// You could also register a receiver in your manifest.
		final IntentFilter lftIntentFilter = new IntentFilter(LocationLibraryConstants.getLocationChangedPeriodicBroadcastAction());
		registerReceiver(lftBroadcastReceiver, lftIntentFilter);
	}

	@Override
	public void onPause() {
		super.onPause();

		unregisterReceiver(lftBroadcastReceiver);
	}

	private final BroadcastReceiver lftBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// extract the location info in the broadcast
			final LocationInfo locationInfo = (LocationInfo) intent.getSerializableExtra(LocationLibraryConstants.LOCATION_BROADCAST_EXTRA_LOCATIONINFO);
			// refresh the display with it
			refreshDisplay(locationInfo);
		}
	};

	public void forceUpdate(View view){
		LocationLibrary.forceLocationUpdate(this);
		Toast.makeText(getApplicationContext(), "Forcing a location update", Toast.LENGTH_SHORT).show();
	}

	private void refreshDisplay() {
		refreshDisplay(new LocationInfo(this));
	}

	private void refreshDisplay(final LocationInfo locationInfo) {
		tv1.setText("Lat: " + locationInfo.lastLat + "\n" + "Long: " + locationInfo.lastLong);
	}

}
