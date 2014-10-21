package com.example.bookshopping_vf_prj;

import java.math.BigDecimal;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Activity4 extends Activity {

	private List<Product> mCartList;
	private ProductAdapter mProductAdapter;
	private ListView listViewCatalog;
	private TextView TextViewTax;
	private TextView productPriceTextView;
	private TextView TextViewFinalBill;
	
	SessionManager session;
	
	private static final double taxRate = 0.065; // 6.5%

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity4);

		session = new SessionManager(getApplicationContext());
		session.checkLogin();
		
		mCartList = ShoppingCartHelper.getCartList();

		// Make sure to clear the selections
		for (int i = 0; i < mCartList.size(); i++) {
			mCartList.get(i).selected = false;
		}

		// Create the list
		listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);
		
		
		mProductAdapter = new ProductAdapter(mCartList, getLayoutInflater(),
				true);
		listViewCatalog.setAdapter(mProductAdapter);

		listViewCatalog.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent cartDetailsIntent = new Intent(getBaseContext(),
						CartDetailsActivity.class);

				cartDetailsIntent.putExtra(ShoppingCartHelper.PRODUCT_INDEX,
						position);

				startActivity(cartDetailsIntent);
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();

		// !!!! Here will have a bug, resume from update cart will not renew the
		// list!
		// Refresh the data
		if (mProductAdapter != null) {
			mProductAdapter.notifyDataSetChanged();
		}
		// Reset the cartList for refresh the adaptor
		mCartList = ShoppingCartHelper.getCartList();
		mProductAdapter = new ProductAdapter(mCartList, getLayoutInflater(),
				true);
		listViewCatalog.setAdapter(mProductAdapter);

		// listViewCatalog.setAdapter(mProductAdapter);

		double subTotal = 0;
		for (Product p : mCartList) {
			int quantity = ShoppingCartHelper.getProductQuantity(p);
			subTotal += p.price * quantity;
		}

		productPriceTextView = (TextView) findViewById(R.id.TextViewSubtotal);
		TextViewTax = (TextView) findViewById(R.id.TextViewTax);
		TextViewFinalBill = (TextView) findViewById(R.id.TextViewFinalBill);
		
		subTotal = new BigDecimal(subTotal).setScale(2,2).doubleValue();
		
		double tax = subTotal * taxRate;
		BigDecimal bd  = new BigDecimal(tax);
		
		productPriceTextView.setText("Total before tax  $" +subTotal );
		TextViewTax.setText			("Tax                         $"+bd.setScale(2, 2));
		
		TextViewFinalBill.setText	("Order Total           $" +new BigDecimal(subTotal+tax).setScale(2,2));
		
	}

	public void Checkout(View view) {

		if (mCartList.isEmpty()) {
			Toast.makeText(this, "Your shopping Cart is empty",
					Toast.LENGTH_LONG).show();
		} else {
			Intent intent = new Intent(Activity4.this, Activity5.class);
			startActivity(intent);
		}

	}

	public void SignOut(View view){
		session.logoutUser();
		finish();	
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity4, menu);
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
