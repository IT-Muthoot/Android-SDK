package com.muthootfinance.retreivesdk;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ApiRequestTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        String apiUrl = params[0];
        String jsonData = params[1];

        HttpURLConnection urlConnection = null;
        try {
            // Create URL
            URL url = new URL(apiUrl);

            // Open connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setFixedLengthStreamingMode(jsonData.getBytes().length);
//            urlConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");

            // Write data to the server
            try (OutputStream os = new BufferedOutputStream(urlConnection.getOutputStream())) {
                os.write(jsonData.getBytes(StandardCharsets.UTF_8));
            }

            // Get response from the server
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                Log.d("Response Async", urlConnection.getContent().toString());
                // InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                // String response = convertStreamToString(inputStream);
                // Handle the response as needed
            } else {
                // Handle the error
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return null;
    }

    // Add any additional methods or logic as needed

    // Example method to convert InputStream to String
    /*
    private String convertStreamToString(InputStream inputStream) {
        // Implement your logic to convert the InputStream to a String
        // ...
    }
    */
}
