package com.munachimsoani.android.personalfeedapp.utilities;

import android.util.Log;

import com.munachimsoani.android.personalfeedapp.model.News;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtils {


    public static ArrayList<News> makeNewsListTopStories(String jsonResult) {

        ArrayList<News> newsList = new ArrayList<>();

        String title = "";
        String abstracts = "";
        String url = "";
        String published_date = "";
        String image_url = "";

        try {


            JSONObject mainJSONObject = new JSONObject(jsonResult);
            JSONArray results = mainJSONObject.getJSONArray("results");

//            Log.d("test", String.valueOf(results.length()));
            for (int i = 0; i < results.length(); i++) {

                JSONObject item = results.getJSONObject(i);

                 title = item.getString("title");
                 abstracts = item.getString("abstract");
                 url = item.getString("url");
                 published_date = item.getString("published_date");

                JSONArray multimedia = item.getJSONArray("multimedia");

//                Log.d("test", String.valueOf(multimedia.length()));

                if(multimedia.length() == 0){

                    // When the news has no image set a default image right now it's blank
                    image_url = "";
                }

                    for (int j = 0; j < multimedia.length(); j++) {

                        JSONObject itemImage = multimedia.getJSONObject(j);

                            image_url = itemImage.getString("url");


                    }

//                    Log.d("test", image_url);




                newsList.add(new News(title,abstracts,url,published_date,image_url));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return  newsList;

    }
}
