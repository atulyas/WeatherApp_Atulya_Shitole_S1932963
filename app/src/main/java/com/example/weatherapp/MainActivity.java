//Name: Atulya Shitole
//Student ID: S1932963

package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    // Sample location data
    private final String[] locations = {"Glasgow", "London", "New York", "Oman", "Mauritius", "Bangladesh"};
    private final String[] locationIds = {"2648579", "2643743", "5128581", "287286", "934154", "1185241"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get reference to the LinearLayout container
        LinearLayout containerLayout = findViewById(R.id.containerLayout);

        // Loop through locations and create a CardView for each
        for (int i = 0; i < locations.length; i++) {
            CardView cardView = createCardView(locations[i], locationIds[i]);
            containerLayout.addView(cardView);
        }
    }

    // Function to create a CardView for a location
    private CardView createCardView(String location, String locationId) {
        // Inflate the CardView layout
        CardView cardView = (CardView) LayoutInflater.from(this).inflate(R.layout.cardview, null);

        // Get references to views inside the CardView
        TextView locationTextView = cardView.findViewById(R.id.locationTextView);
        Button forecastButton = cardView.findViewById(R.id.forecastButton);
        Button observationButton = cardView.findViewById(R.id.observationButton);

        // Set location name
        locationTextView.setText(location);

        // Set tag to buttons to store locationId
        forecastButton.setTag(locationId);
        observationButton.setTag(locationId);

        // Set OnClickListener for forecastButton
        forecastButton.setOnClickListener(v -> {
            // Handle forecast button click
            String id = (String) v.getTag();
            String url3days ="https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/"+id;
            Log.d("Location ID", "Forecast button clicked for location ID: " + url3days);
            Intent intent = new Intent(MainActivity.this, Forecast.class);
            intent.putExtra("url3days", url3days);
            intent.putExtra("location", location);
            startActivity(intent);
        });

        // Set OnClickListener for observationButton
        observationButton.setOnClickListener(v -> {
            // Handle observation button click
            String id = (String) v.getTag();

            String urlRss ="https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/"+id;
            Log.d("Location ID", "Observation button clicked for location ID: " + urlRss);
            Intent intent = new Intent(MainActivity.this, Observation.class);
            intent.putExtra("urlRss", urlRss);
            intent.putExtra("location", location);
            startActivity(intent);
        });

        return cardView;
    }
}