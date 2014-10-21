package com.example.bookshopping_vf_prj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Activity1 extends Activity {

	private static final String SERVER_URL = "http://www.json-generator.com/api/json/get/bKRxFqbbuG?indent=2";

	private SharedPreferences sharedPreferences;
	private Button loginButton;
	private EditText loginEditText;
	private EditText pwdEditText;
	private CheckBox rememberCheckBox;
	private ImageView logoImageView;
	private ProgressBar progressBar;

	private SessionManager session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity1);

		session = new SessionManager(getApplicationContext());

		sharedPreferences = getSharedPreferences("login_data",
				Context.MODE_PRIVATE);
		loginButton = (Button) findViewById(R.id.loginButton);
		loginEditText = (EditText) findViewById(R.id.loginEditText);
		pwdEditText = (EditText) findViewById(R.id.pwdEditText);
		rememberCheckBox = (CheckBox) findViewById(R.id.rememberCheckBox);
		logoImageView = (ImageView) findViewById(R.id.logoImageView);

		if (sharedPreferences.getBoolean("setRemember", true) == true) {
			loginEditText.setText(sharedPreferences.getString("account", "")
					.toString());
			pwdEditText.setText(sharedPreferences.getString("pwd", "")
					.toString());
			rememberCheckBox.setChecked(sharedPreferences.getBoolean(
					"setRemember", true));
		}

		rememberCheckBox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				if (rememberCheckBox.isChecked()) {
					Editor editor = sharedPreferences.edit();

					String account = loginEditText.getText().toString();
					String pwd = pwdEditText.getText().toString();

					editor.putString("account", account);
					editor.putString("pwd", pwd);
					editor.putBoolean("setRemember", true);
					editor.putString("logged", "logged");
					editor.commit();
				} else {
					Editor editor = sharedPreferences.edit();
					editor.clear();
					editor.commit();
				}
			} // end onClick()
		});

	}

	public void Login(View view) {

		// Here should send username and pwd to server using AsyncTask to verify
		// user

		String account = loginEditText.getText().toString();
		String pwd = pwdEditText.getText().toString();

		if (account.trim().equals("admin") && pwd.equals("admin")) {
			Log.i("test", "login");

			session.createLoginSession(account, pwd);

			Intent intent = new Intent(Activity1.this, Activity3.class);
			intent.putExtra("account", account);
			intent.putExtra("pwd", pwd);

			Toast.makeText(getBaseContext(), "Login success",
					Toast.LENGTH_SHORT).show();
			startActivity(intent);
			finish();
		} else {
			Toast.makeText(getBaseContext(), "Login fail", Toast.LENGTH_SHORT)
					.show();
		}

	}

	public void SignUp(View view) {
		Intent intent = new Intent(Activity1.this, Activity2.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity1, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			
			Intent intent = new Intent(getBaseContext(), AboutMeActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
