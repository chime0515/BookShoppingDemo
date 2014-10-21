package com.example.bookshopping_vf_prj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Activity3 extends Activity {
	public static final String SERVER_CAT_URL = "http://www.json-generator.com/api/json/get/bPBGoJpUbS?indent=2";

	private List<Product> mProductList;
	private List<String> categories;
	private Spinner catSpinner;
	private ProgressBar progressBar;
	private TextView catTextView;
	
	private SessionManager session;
	
	static int CAT_INDEX=0; 

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity3);

		session = new SessionManager(getApplicationContext());
		session.checkLogin();
		// categories = ShoppingCartHelper.getCategories();
		
		String user = session.getUserDetails().get(SessionManager.KEY_NAME);
		Toast.makeText(getBaseContext(), "Welecom! "+user, Toast.LENGTH_LONG).show();

		// Create the list
		catSpinner = (Spinner) findViewById(R.id.catSpinner);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		catTextView = (TextView) findViewById(R.id.catTextView);
		
		// Go background to fetch the category list
		new CateFetchTask().execute();

		// Display items bases on selected category in Spinner 
		catSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				CAT_INDEX = arg2;
				catTextView.setText(categories.get(CAT_INDEX));
				
				// Obtain a reference to the product catalog
				mProductList = ShoppingCartHelper.getCatalog(getResources(), CAT_INDEX);
				
				ListView listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);
				listViewCatalog.setAdapter(new ProductAdapter(mProductList,
						getLayoutInflater(), false));

				listViewCatalog.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent productDetailsIntent = new Intent(getBaseContext(),
								ProductDetailsActivity.class);
						// Send category index and product index to next activity
						productDetailsIntent.putExtra(ShoppingCartHelper.CAT_INDEX,
								CAT_INDEX);
						productDetailsIntent.putExtra(ShoppingCartHelper.PRODUCT_INDEX,
								position);
						startActivity(productDetailsIntent);
					}
				});
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
			
		
		Button viewShoppingCart = (Button) findViewById(R.id.ButtonViewCart);
		
		// Go to shopping cart activity
		viewShoppingCart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent viewShoppingCartIntent = new Intent(getBaseContext(),
						Activity4.class);
				startActivity(viewShoppingCartIntent);
				
			}
		});

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity3, menu);
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
	

	public void SignOut(View view){
		session.logoutUser();
		finish();
	}
	
	class CateFetchTask extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPreExecute() {
			progressBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			StringBuilder sb = null;
			try {
				URL url = new URL(SERVER_CAT_URL);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				InputStream input = connection.getInputStream();
				InputStreamReader inReader = new InputStreamReader(input);
				BufferedReader bfr = new BufferedReader(inReader);
				sb = new StringBuilder();
				String line = null;

				while ((line = bfr.readLine()) != null) {
					sb.append(line);
				}

				connection.disconnect();
				input.close();
				inReader.close();
				bfr.close();

			} catch (MalformedURLException e) {

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return sb.toString();
		}
		
		protected void onPostExecute(String s) {
			

			categories = new ArrayList<String>();
			categories = ParseJSONToCat(s);
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					Activity3.this, android.R.layout.simple_spinner_item,
					categories);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			catSpinner.setAdapter(adapter);
		
			progressBar.setVisibility(View.GONE);
		}


	}

	public List<String> ParseJSONToCat(String s) {

		List<String> list = new ArrayList<String>();

		try {
			JSONArray jarr = new JSONArray(s);
			for (int i = 0; i < jarr.length(); i++) {
				JSONObject jobj = jarr.getJSONObject(i);
				// Log.i("test", jobj.getString("categoryTitle"));
				list.add(jobj.getString("categoryTitle"));
			}

		} catch (JSONException e1) {

		}

		return list;

	}
}
