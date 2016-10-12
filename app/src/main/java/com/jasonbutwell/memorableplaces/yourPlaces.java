package com.jasonbutwell.memorableplaces;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class yourPlaces extends AppCompatActivity implements GoogleMap.OnMapLongClickListener, OnMapReadyCallback {

    int location;
    private GoogleMap mMap;
    public ActionBar actionBar;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();      // If home button closed then finish this activity
                return true;
            default:
                return super.onOptionsItemSelected(item);   // default
        }
    }

    @Override
    public void onMapLongClick( LatLng point ) {

        // Use the geocoder to enable us to resolve an address from the Lat / Long
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        // The default is to just store the date in case we cannot get a place name
        String label = new Date().toString();

        try {
            // Store the address in the address list using the geocoder with lat / lon, we want the first result only.
            List<Address> listAddresses = geocoder.getFromLocation(point.latitude, point.longitude,1);

            // Check we have obtained an address line to store
            if ( listAddresses != null && listAddresses.size() > 0) {

                // Set label to the first address we stored
                label = listAddresses.get(0).getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        MainActivity.places.add( label );                   // Add the label of the new marker to the arraylist in the MainActivity

        MainActivity.arrayAdapter.notifyDataSetChanged();   // Notify to the array adapter that we have changed the list

        MainActivity.locations.add(point);                  // Add the Lat / Lng to the locations array list in MainActivity

        mMap.addMarker(( new MarkerOptions()                // Create the new marker on the map
        .position( point )
        .title( label )
        .icon( BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN ))));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_ACTION_BAR);

        setContentView(R.layout.activity_your_places);

        location = -1; // Set to default to say we don't have a valid location.

        // Enable the back button on the action bar
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // get the data we passed in from earlier from the first intent.
        
        Intent intent = getIntent();
        //Log.i("locationInfo", Integer.toString(intent.getIntExtra("locationInfo", -1)));
        location = intent.getIntExtra("locationInfo",-1);   // -1 is the default value if no value was picked up

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        // check to make sure the location is not the first element and also that the location is not -1.
        // Anything above the value of 0 represents a valid list view index.

        if ( location != -1 && location != 0 ) {

            // Get location from the locations array list using the location as the indexing variable and use a zoom level of 10 to zoom in.
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MainActivity.locations.get(location), 10));

            // Show the previous location on the map from both array lists using the location as the index.
            // This time display that in red instead of green.

            mMap.addMarker((new MarkerOptions()
                    .position(MainActivity.locations.get(location))
                    .title(MainActivity.places.get(location))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))));
        }

        // Add the long click listener
        mMap.setOnMapLongClickListener(this);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("yourPlaces Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
