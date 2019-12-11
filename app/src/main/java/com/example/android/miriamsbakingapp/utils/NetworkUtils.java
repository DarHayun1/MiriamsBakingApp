package com.example.android.miriamsbakingapp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {


    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String RECIPES_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static String getRecipesFromHttp() throws IOException {
        URL url = new URL(RECIPES_URL);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            if (scanner.hasNext())
                return scanner.next();
            else
                return null;
        } finally {
            urlConnection.disconnect();
        }

    }

}
