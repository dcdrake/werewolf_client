package com.example.werewolf_client;

import restClient.MafiaRestClient;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class StartGame extends Activity {

	private EditText hoursText;
	private EditText minutesText;
	private EditText killRadiusText;
	private String username;
	private String password;
	int dayNightFreq;
	int killRadius;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_game);

		hoursText = (EditText) findViewById(R.id.editText1);
		minutesText = (EditText) findViewById(R.id.editText2);
		killRadiusText = (EditText) findViewById(R.id.editText3);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			username = extras.getString("username");
			password = extras.getString("password");
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start_game, menu);
		return true;
	}
	public void startGame(View view) {

		int hours = Integer.parseInt(hoursText.getText().toString());
		int minutes = Integer.parseInt(minutesText.getText().toString());

		killRadius = Integer.parseInt(killRadiusText.getText().toString());
		dayNightFreq = (hours * 60) + minutes;

		//AsyncHttpClient client = new AsyncHttpClient();
		MafiaRestClient.post("http://mafia-service.herokuapp.com/startGame/" + username + "/" + dayNightFreq
				+ "/" + killRadius, null, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				Context context = getApplicationContext();
				CharSequence text = "Game started successfully.";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();

			}
		});
		Intent intent = new Intent(this, PlayerVoteActivity.class);
		intent.putExtra("username", username);
		intent.putExtra("password", password);
		startActivity(intent);
	}

}
