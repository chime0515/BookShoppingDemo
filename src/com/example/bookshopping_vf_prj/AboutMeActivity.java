package com.example.bookshopping_vf_prj;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AboutMeActivity extends Activity {

	private GoogleMap map;
	private final LatLng LOCATION_STORE = new LatLng(40.753298, -73.877580);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_me);
        map  = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        map.addMarker(new MarkerOptions().position(LOCATION_STORE).title("Find me here!"));
        
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_STORE, 16);
        map.animateCamera(update);
	}
	
	


}
