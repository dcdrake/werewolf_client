package com.example.werewolf_client;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import restClient.MafiaRestClient;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.widget.AdapterView.OnItemClickListener;
public class PlayerVoteActivity extends ListActivity {

	private ListView playerList;
	ArrayList<String> list;
	ArrayAdapter<String> adapter;
	private int playerArrayLength;

	protected static final String TAG = "debug";
	private String username;
	private String password;
	private String voteeID;
	private Button joinButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_vote);
		// Show the Up button in the action bar.
		setupActionBar();
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			username = extras.getString("username");
			password = extras.getString("password");
		}


		playerList = getListView();
		joinButton = (Button) findViewById(R.id.join);

		MafiaRestClient.setBasicAuth(username, password);
		MafiaRestClient.get("getLivePlayers", null, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject jsonResponse) {
				try {

					JSONArray playersArray = jsonResponse.getJSONArray("livePlayers");
					playerArrayLength = playersArray.length();

					list = new ArrayList<String>();

					for (int i = 0; i < playerArrayLength; ++i) {
						JSONObject jObj = playersArray.getJSONObject(i);
						String userID = jObj.getString("userID");
						list.add(userID);
					}
					adapter = new ArrayAdapter<String>(PlayerVoteActivity.this,
							R.layout.list_item, R.id.label, list);
					playerList.setAdapter(adapter); 



				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		playerList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				voteeID = list.get(position);

				MafiaRestClient.setBasicAuth(username, password);
				MafiaRestClient.post("voteForPlayer/" + username + "/" + voteeID, null, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						Context context = getApplicationContext();

						CharSequence text = "Vote cast";
						int duration = Toast.LENGTH_SHORT;
						Toast toast = Toast.makeText(context, text, duration);
						toast.show();
					}

					@Override
					public void onFailure(Throwable e) {
						Log.e(TAG, "OnFailure!", e);
					}
					@Override
					public void onFailure(Throwable e, String response) {
						Log.e(TAG, "OnFailure!", e);
					}
				});
			}
		}); 
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	public void joinGame(View view){
		adapter = null;
		list.add(username);
		adapter = new ArrayAdapter<String>(PlayerVoteActivity.this,
				R.layout.list_item, R.id.label, list);
		playerList.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		System.out.println("This worked");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.player_vote, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
