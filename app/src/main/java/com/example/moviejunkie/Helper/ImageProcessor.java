package com.example.moviejunkie.Helper;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ImageProcessor {

    public static byte[] getImageFromUrl(String URL){


        byte[] ImgByte= new byte[0];
        try {
            java.net.URL url = new URL("https://image.tmdb.org/t/p/w342" + URL);
            try {
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                URLConnection conn = url.openConnection();
                conn.setRequestProperty("User-Agent", "Firefox");

                try (InputStream inputStream = conn.getInputStream()) {
                    int n = 0;
                    byte[] buffer = new byte[1024];
                    while (-1 != (n = inputStream.read(buffer))) {
                        output.write(buffer, 0, n);
                    }
                }
                ImgByte = output.toByteArray();
                Log.d("IMG", "img array::: "+ImgByte.length);
            } catch (IOException e) {
                Log.d("IO", "IO error:: " + e.getMessage());
            }
        }catch (MalformedURLException ex){
            Log.d("URL", "URL error:: " + ex.getMessage());

        }

       return ImgByte;
    }
}
