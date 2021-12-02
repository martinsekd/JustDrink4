package dk.au.mad21fall.appproject.justdrink.Model.PlacesAPILogic;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.util.concurrent.ListenableFutureTask;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.util.concurrent.ListeningExecutorService;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.util.concurrent.MoreExecutors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import dk.au.mad21fall.appproject.justdrink.Model.Location;

public class PlaceAPIClass {
    private RequestQueue queue;
    private Context mContext;
    private ListeningExecutorService executorService;


    public PlaceAPIClass(Context context) {
        mContext = context;
        queue = Volley.newRequestQueue(context);
        executorService = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());

    }


    public void sendRequest(double lat, double lng, int radius,Response.Listener<List<Location>> listener) {
        StringRequest request = new StringRequest(Request.Method.GET, requestBuilder(lat,lng,radius), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onResponse(getPlacesByString(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }

    private String requestBuilder(double lat, double lng, int radius) {
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json"+
                "?location="+lat+"%2C"+lng+
                "&radius="+radius+
                "&type=bar"+
                "&key=AIzaSyD2_ExNVsdQpZJnTmUfO2I6i4Sjqnv_el8";
        return url;
    }

    private List<Location> getPlacesByString(String json) {
        ArrayList<Location> list = new ArrayList<>();
        try {
            JSONObject jo = new JSONObject(json);
            JSONArray places = jo.getJSONArray("results");
            for (int i=0;i<places.length();i++) {
                Location l = new Location();
                l.name = places.getJSONObject(i).getString("name");
                l.address = places.getJSONObject(i).getString("vicinity");
                l.lat = places.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                l.long1 = places.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                l.rating = places.getJSONObject(i).getDouble("rating");
                list.add(l);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
