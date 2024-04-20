// Name: Atulya Shitole
// Student ID: S1932963
package com.example.weatherapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class Observation extends AppCompatActivity {

    TextView titleTextView, descriptionTextView;
    TextView temperatureTextView;

    String urlSource, location;
    private String result = "";
    ImageView centerImageView1,centerImageView2, centerImageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation);

        titleTextView = findViewById(R.id.placeNameTextView);
        descriptionTextView = findViewById(R.id.placeDescriptionTextView);

        temperatureTextView = findViewById(R.id.temperatureTextView);

        centerImageView3 = findViewById(R.id.centerImageView);

        urlSource = getIntent().getStringExtra("urlRss");
        location = getIntent().getStringExtra("location");
        startProgress();
    }

    private class ParseXmlTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... strings) {
            try {
                return parseXml(strings[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] result) {
            if (result != null && result.length == 4) {
                titleTextView.setText(result[0].substring(0, result[0].length()-1) + location);
                descriptionTextView.setText(result[1].replaceAll("�", "°"));

                String[] weatherDetails = result[3].split(", ");
                Log.e("result[3]", weatherDetails.length + result[3]);

                String weatherCondition3 = "Light Rain";

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
                if (weatherDetails.length >= 7) {
                    temperatureTextView.setText(result[3].replaceAll("�", "°").replaceAll(",", "\n"));
                }
            }
        }
    }

    private String[] parseXml(String xmlString) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new StringBufferInputStream(xmlString));

        String channelTitle = "";
        String itemTitle = "";
        String channelDescription = "";
        String itemDescription = "";

        Element channelElement = (Element) document.getElementsByTagName("channel").item(0);
        if (channelElement != null) {
            channelTitle = channelElement.getElementsByTagName("title").item(0).getTextContent();
            channelDescription = channelElement.getElementsByTagName("description").item(0).getTextContent();
        }

        Element itemElement = (Element) document.getElementsByTagName("item").item(0);
        if (itemElement != null) {
            itemTitle = itemElement.getElementsByTagName("title").item(0).getTextContent();
            itemDescription = itemElement.getElementsByTagName("description").item(0).getTextContent();
        }

        return new String[]{channelTitle, itemTitle, channelDescription, itemDescription};
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