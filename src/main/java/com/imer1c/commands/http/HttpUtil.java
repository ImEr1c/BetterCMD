package com.imer1c.commands.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imer1c.BetterCMDApplication;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class HttpUtil {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void sendHttpGetRequest(String link, Map<String, String> requestProperties, BetterCMDApplication app, boolean formatJsonResponse) throws IOException
    {
        URL url = new URL(link);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");

        requestProperties.forEach(urlConnection::setRequestProperty);

        int responseCode = urlConnection.getResponseCode();

        app.write("Response Code: " + responseCode);
        app.write("");

        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

        String line = reader.readLine();

        while (line != null)
        {
            if (formatJsonResponse)
            {
                printFormattedJson(line, app);
            }
            else
            {
                app.write(line);
            }

            line = reader.readLine();
        }
    }

    public static void sendHttpPostRequest(String link, Map<String, String> requestProperties, String body, BetterCMDApplication app, boolean formatJsonResponse) throws IOException
    {
        URL url = new URL(link);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");

        requestProperties.forEach(urlConnection::setRequestProperty);
        urlConnection.setDoOutput(true);

        OutputStream outputStream = urlConnection.getOutputStream();
        outputStream.write(body.getBytes());
        outputStream.close();

        int responseCode = urlConnection.getResponseCode();

        app.write("Response Code: " + responseCode);
        app.write("");

        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

        String line = reader.readLine();

        while (line != null)
        {
            if (formatJsonResponse)
            {
                printFormattedJson(line, app);
            }
            else
            {
                app.write(line);
            }

            line = reader.readLine();
        }
    }

    public static void printFormattedJson(String json, BetterCMDApplication app)
    {
        json = GSON.toJson(GSON.fromJson(json, Object.class));

        String[] split = json.split("\n");

        for (String s : split)
        {
            app.write(s);
        }
    }
}
