// Name: Atulya Shitole
// Student ID: S1932963

package com.example.weatherapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Forecast extends AppCompatActivity {

    TextView placeNameTextView, placeDescriptionTextView;
    TextView day1TextView, temperatureDay1TextView, windDirectionDay1TextView;
    TextView day2TextView, temperatureDay2TextView, windDirectionDay2TextView;
    TextView day3TextView, temperatureDay3TextView, windDirectionDay3TextView;

    ImageView centerImageView1,centerImageView2, centerImageView3;
    String urlSource, location;
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        placeNameTextView = findViewById(R.id.placeNameTextView);
        placeDescriptionTextView = findViewById(R.id.placeDescriptionTextView);

        day1TextView = findViewById(R.id.day1TextView);
        temperatureDay1TextView = findViewById(R.id.temperatureDay1TextView);
        windDirectionDay1TextView = findViewById(R.id.windDirectionDay1TextView);

        day2TextView = findViewById(R.id.day2TextView);
        temperatureDay2TextView = findViewById(R.id.temperatureDay2TextView);
        windDirectionDay2TextView = findViewById(R.id.windDirectionDay2TextView);

        day3TextView = findViewById(R.id.day3TextView);
        temperatureDay3TextView = findViewById(R.id.temperatureDay3TextView);
        windDirectionDay3TextView = findViewById(R.id.windDirectionDay3TextView);

         centerImageView1 = findViewById(R.id.centerImageView);
         centerImageView2 = findViewById(R.id.centerImageView1);
         centerImageView3 = findViewById(R.id.centerImageView3);


        urlSource = getIntent().getStringExtra("url3days");
        location = getIntent().getStringExtra("location");
        startProgress();

    }

    private class ParseXmlTask extends AsyncTask<String, Void, Weather[]> {

        @Override
        protected Weather[] doInBackground(String... strings) {
            try {
                return parseXml(strings[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Weather[] result) {
            if (result != null && result.length >= 3) {
                // Day 1
                placeNameTextView.setText(result[0].getPlaceName());
                placeDescriptionTextView.setText(result[0].getPlaceDescription());

                String[] parts1 = result[1].getTitle().split(","); // Split the string at ","
                String title1 = parts1[0];
                day1TextView.setText(title1);
                temperatureDay1TextView.setText( result[1].getTemperature().replaceAll("�", "°"));
                windDirectionDay1TextView.setText( result[1].getDescription().replaceAll("�", "°").replaceAll(",", "\n"));
                Log.e("result[1].getTemperature()",result[1].getTemperature());

                String[] partss1 = title1.split(":");
                String weatherCondition = partss1[1].trim();

                if (weatherCondition.equals("Light Rain")) {
                    centerImageView1.setImageResource(R.drawable.sleet);
                } else if (weatherCondition.equals("Heavy Rain")) {
                    centerImageView1.setImageResource(R.drawable.rain);
                } else if (weatherCondition.equals("Partly Cloudy")) {
                    centerImageView1.setImageResource(R.drawable.snow);
                } else if (weatherCondition.equals("Light Cloud")) {
                    centerImageView1.setImageResource(R.drawable.day_partial_cloud);
                } else if (weatherCondition.equals("Cloudy")) {
                    centerImageView1.setImageResource(R.drawable.cloudy);
                } else if (weatherCondition.equals("Clear Sky")) {
                    centerImageView1.setImageResource(R.drawable.day_clear);
                } else if (weatherCondition.equals("Sunny Intervals")) {
                    centerImageView1.setImageResource(R.drawable.day_clear);
                } else if (weatherCondition.equals("Sunny")) {
                    centerImageView1.setImageResource(R.drawable.day_clear);
                } else if (weatherCondition.equals("Drizzle")) {
                    centerImageView1.setImageResource(R.drawable.night_rain_thunder);
                } else if (weatherCondition.equals("Light Rain Showers")) {
                    centerImageView1.setImageResource(R.drawable.night_sleet);
                } else {
                    centerImageView1.setImageResource(R.drawable.day_snow); // Default image
                }

                // Day 2
                String[] parts2 = result[2].getTitle().split(","); // Split the string at ","
                String title2 = parts2[0];
                day2TextView.setText(title2);
                temperatureDay2TextView.setText( result[2].getTemperature().replaceAll("�", "°"));
                windDirectionDay2TextView.setText( result[2].getDescription().replaceAll("�", "°").replaceAll(",", "\n"));


                String[] partss2 = title2.split(":");
                String weatherCondition2 = partss2[1].trim();

                if (weatherCondition2.equals("Light Rain")) {
                    centerImageView2.setImageResource(R.drawable.sleet);
                } else if (weatherCondition2.equals("Heavy Rain")) {
                    centerImageView2.setImageResource(R.drawable.rain);
                } else if (weatherCondition2.equals("Partly Cloudy")) {
                    centerImageView2.setImageResource(R.drawable.snow);
                } else if (weatherCondition2.equals("Light Cloud")) {
                    centerImageView2.setImageResource(R.drawable.day_partial_cloud);
                } else if (weatherCondition2.equals("Cloudy")) {
                    centerImageView2.setImageResource(R.drawable.cloudy);
                } else if (weatherCondition2.equals("Clear Sky")) {
                    centerImageView2.setImageResource(R.drawable.day_clear);
                } else if (weatherCondition2.equals("Sunny Intervals")) {
                    centerImageView2.setImageResource(R.drawable.day_clear);
                } else if (weatherCondition2.equals("Sunny")) {
                    centerImageView2.setImageResource(R.drawable.day_clear);
                } else if (weatherCondition2.equals("Drizzle")) {
                    centerImageView2.setImageResource(R.drawable.night_rain_thunder);
                } else if (weatherCondition2.equals("Light Rain Showers")) {
                    centerImageView2.setImageResource(R.drawable.night_sleet);
                } else {
                    centerImageView2.setImageResource(R.drawable.day_snow); // Default image
                }


                // Day 3
                String[] parts3 = result[3].getTitle().split(","); // Split the string at ","
                String title3 = parts3[0];
                day3TextView.setText(title3);
                temperatureDay3TextView.setText( result[3].getTemperature().replaceAll("�", "°"));
                windDirectionDay3TextView.setText( result[3].getDescription().replaceAll("�", "°").replaceAll(",", "\n"));

                String[] partss3 = title3.split(":");
                String weatherCondition3 = partss3[1].trim();

                if (weatherCondition3.equals("Light Rain")) {
                    centerImageView3.setImageResource(R.drawable.sleet);
                } else if (weatherCondition3.equals("Heavy Rain")) {
                    centerImageView3.setImageResource(R.drawable.rain);
                } else if (weatherCondition3.equals("Partly Cloudy")) {
                    centerImageView3.setImageResource(R.drawable.snow);
                } else if (weatherCondition3.equals("Light Cloud")) {
                    centerImageView3.setImageResource(R.drawable.day_partial_cloud);
                } else if (weatherCondition3.equals("Cloudy")) {
                    centerImageView3.setImageResource(R.drawable.cloudy);
                } else if (weatherCondition3.equals("Clear Sky")) {
                    centerImageView3.setImageResource(R.drawable.day_clear);
                } else if (weatherCondition3.equals("Sunny Intervals")) {
                    centerImageView3.setImageResource(R.drawable.day_clear);
                } else if (weatherCondition3.equals("Sunny")) {
                    centerImageView3.setImageResource(R.drawable.day_clear);
                } else if (weatherCondition3.equals("Drizzle")) {
                    centerImageView3.setImageResource(R.drawable.night_rain_thunder);
                } else if (weatherCondition3.equals("Light Rain Showers")) {
                    centerImageView3.setImageResource(R.drawable.night_sleet);
                } else {
                    centerImageView3.setImageResource(R.drawable.day_snow); // Default image
                }
            }
        }
    }

    private Weather[] parseXml(String xmlString) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new StringBufferInputStream(xmlString));

        Weather[] weatherArray = new Weather[4]; // Changed to 4 for day 1, day 2, and day 3

        // Parse channel details
        Element channelElement = (Element) document.getElementsByTagName("channel").item(0);
        if (channelElement != null) {
            String placeName = channelElement.getElementsByTagName("title").item(0).getTextContent();
            String placeDescription = channelElement.getElementsByTagName("description").item(0).getTextContent();
            weatherArray[0] = new Weather(placeName, placeDescription);
        }

        // Parse item details for day 1
        Element itemElement1 = (Element) document.getElementsByTagName("item").item(0);
        if (itemElement1 != null) {
            String title = itemElement1.getElementsByTagName("title").item(0).getTextContent();
            String[] parts = title.split(",");
            String temperature = parts[1];
            String[] temperatureParts = temperature.split("\\(");
            temperature = temperatureParts[1].replaceAll("\\)", "");
            String description = itemElement1.getElementsByTagName("description").item(0).getTextContent();


            weatherArray[1] = new Weather(title, temperature, description);
        }

        // Parse item details for day 2
        Element itemElement2 = (Element) document.getElementsByTagName("item").item(1);
        if (itemElement2 != null) {
            String title = itemElement2.getElementsByTagName("title").item(0).getTextContent();
            String[] parts = title.split(",");
            String temperature = parts[1];
            String[] temperatureParts = temperature.split("\\(");
            temperature = temperatureParts[1].replaceAll("\\)", "");
            String description = itemElement2.getElementsByTagName("description").item(0).getTextContent();


            weatherArray[2] = new Weather(title, temperature, description);
        }

// Parse item details for day 3
        Element itemElement3 = (Element) document.getElementsByTagName("item").item(2);
        if (itemElement3 != null) {
            String title = itemElement3.getElementsByTagName("title").item(0).getTextContent();
            String[] parts = title.split(",");
            String temperature = parts[1];
            String[] temperatureParts = temperature.split("\\(");
            temperature = temperatureParts[1].replaceAll("\\)", "");
            String description = itemElement3.getElementsByTagName("description").item(0).getTextContent();


            weatherArray[3] = new Weather(title, temperature, description);
        }

        return weatherArray;
    }

    public void startProgress() {
        new Thread(new Task(urlSource)).start();
    }

    private class Task implements Runnable {
        private String url;

        public Task(String aurl) {
            url = aurl;
        }

        @Override
        public void run() {
            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine;

            Log.e("MyTag", "in run");
            try {
                Log.e("MyTag", "in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                while ((inputLine = in.readLine()) != null) {
                    result += inputLine;
                }
                in.close();
            } catch (IOException ae) {
                Log.e("MyTag", "toexception");
            }

            // Get rid of the first tag <?xml version="1.0" encoding="utf-8"?>
            int i = result.indexOf(">");
            if (i != -1) {
                result = result.substring(i + 1);
            }
            new ParseXmlTask().execute(result);
        }
    }
}

class Weather {
    private String placeName;
    private String placeDescription;
    private String title;
    private String temperature;
    private String description;


    public Weather(String placeName, String placeDescription) {
        this.placeName = placeName;
        this.placeDescription = placeDescription;
    }

    public Weather(String title, String temperature, String description) {
        this.title = title;
        this.temperature = temperature;
        this.description = description;

    }

    public String getPlaceName() {
        return placeName;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public String getTitle() {
        return title;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }

}
