package com.jasonbutwell.memorableplaces;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // static class variable that is accessible outside of this class
    public static ArrayList<String> places;
    public static ArrayAdapter arrayAdapter;
    public static ArrayList<LatLng> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById( R.id.listView );

        places = new ArrayList<>();
        locations = new ArrayList<>();

        places.add("Add a new place...");

        locations.add(new LatLng(0,0)); // Add a default location for the centre of the map

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, places);
        listView.setAdapter(arrayAdapter);

        // call the map activity

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), yourPlaces.class);
                intent.putExtra("locationInfo", position);
                startActivity(intent);
            }
        });
    }
}
