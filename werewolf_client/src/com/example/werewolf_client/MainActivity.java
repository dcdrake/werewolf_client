package com.example.werewolf_client;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import restClient.MafiaRestClient;

public class MainActivity extends Activity {

	protected static final String TAG = "debug";
	private EditText usernameText;
	private EditText passwordText;

	private Button registerButton;
	private Button loginButton;

	private String username;
	private String password;

	private int activeGame;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		MafiaRestClient.get("getGames", null, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				if(response.equals("None")){
					activeGame = 0;
				}else{
					activeGame = 1;
				}
			}
		});

		usernameText = (EditText) findViewById(R.id.username);
		passwordText = (EditText) findViewById(R.id.password);

		registerButton = (Button) findViewById(R.id.createButton);
		loginButton = (Button) findViewById(R.id.button1);

		if(savedInstanceState == null){
			username = "";
		}
		else{
			username = savedInstanceState.getString("username");
			password = savedInstanceState.getString("password");
		}

		//FInd out if there is a game in progress
	}


	//OnClick() methods
	public void registerUser(View view){
		Intent intent = new Intent(this, RegisterUserActivity.class);
		startActivity(intent);
	}

	public void loginUser(View view){
		final String username = usernameText.getText().toString();
		final String password = passwordText.getText().toString();

		//Log user in
		MafiaRestClient.post("loginUser/" + username + "/" + password, 
				null, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				Context context = getApplicationContext();
				CharSequence text = "User " + username + " logged in.";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();

				//If a game has not been started, go to game creation screen
				if(activeGame == 0){
					Intent intent = new Intent(MainActivity.this, StartGame.class);
					intent.putExtra("username", username);
					intent.putExtra("password", password);
					startActivity(intent);
				}else{
					Intent intent = new Intent(MainActivity.this, GameStatusActivity.class);
					intent.putExtra("username", username);
					intent.putExtra("password", password);
					startActivity(intent);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
