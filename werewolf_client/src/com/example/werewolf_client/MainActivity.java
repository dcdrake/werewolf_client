package com.example.werewolf_client;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import restClient.MafiaRestClient;

public class MainActivity extends Activity {

	private EditText usernameText;
	private EditText passwordText;
	
	private Button registerButton;
	private Button loginButton;
	
	private String username;
	private String password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
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
	}
	
	
	//OnClick() methods
	public void registerUser(View view){
		Intent intent = new Intent(this, RegisterUserActivity.class);
		startActivity(intent);
	}

	public void loginUser(View view){
		//AsyncHttpClient client = new AsyncHttpClient();
		final String username = usernameText.getText().toString();
		String password = passwordText.getText().toString();

		MafiaRestClient.post("loginUser/" + username + "/" + password, 
				null, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				Context context = getApplicationContext();
				CharSequence text = "User " + username + " logged in.";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();

			}
		});
		
		
		Intent intent = new Intent(this, StartGame.class);
		intent.putExtra("username", username);
		intent.putExtra("password", password);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
