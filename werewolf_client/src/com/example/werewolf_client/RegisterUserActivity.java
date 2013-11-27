package com.example.werewolf_client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import restClient.MafiaRestClient;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.*;

public class RegisterUserActivity extends Activity {

	private static final String DEBUG_TAG = "test";
	protected static final String TAG = "debug";
	private EditText usernameText;
	private EditText passwordText;
	private EditText verifyPasswordText;
	private EditText firstnameText;
	private EditText lastnameText;

	private TextView httpTestString;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_user);

		usernameText = (EditText) findViewById(R.id.username);
		passwordText = (EditText) findViewById(R.id.password);
		verifyPasswordText = (EditText) findViewById(R.id.verifypassword);
		firstnameText = (EditText) findViewById(R.id.firstname);
		lastnameText = (EditText) findViewById(R.id.lastname);
		httpTestString = (TextView) findViewById(R.id.textTest);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register_user, menu);
		return true;
	}

	public void registerNewUser(View view) {

		final String username = usernameText.getText().toString();
		String password = passwordText.getText().toString();

		// Register a new user in the system
		MafiaRestClient.post("registerNewUser/" + username + "/" + password, 
				null, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				Context context = getApplicationContext();
				CharSequence text = "User " + username + " registered successfully.";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}
		});
		
		// Set BasicAuth credentials and create a new player
		MafiaRestClient.setBasicAuth(username, password);
		MafiaRestClient.post("addPlayer/" + username + "/20/40/True", 
				null, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				Context context = getApplicationContext();
				CharSequence text = "Player created for user: " + username;
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}
		});
		
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
}


