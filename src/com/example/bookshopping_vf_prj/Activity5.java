package com.example.bookshopping_vf_prj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Activity5 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity5);
	}


	public void Checkout(View view){
		Intent intent = new Intent(this, Activity1.class);
		startActivity(intent);
	}
}
