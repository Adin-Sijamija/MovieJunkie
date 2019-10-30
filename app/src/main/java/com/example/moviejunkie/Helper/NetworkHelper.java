package com.example.moviejunkie.Helper;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkHelper {


    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager)   context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            // Network is present and connected
            isAvailable = true;
        }
        return isAvailable;
    }


    public static boolean checkActiveInternetConnection(Context context) {

        if (isNetworkAvailable(context)) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();

                if (urlc.getResponseCode() == 200){
                    urlc.disconnect();
                    return true;
                }
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
                Log.e("Error", "Error: ", e);
            }
        } else {
            Log.d("Error", "No network present");
        }
        return false;
    }

}
