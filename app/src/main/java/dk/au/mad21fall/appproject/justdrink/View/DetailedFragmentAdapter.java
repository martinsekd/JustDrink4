package dk.au.mad21fall.appproject.justdrink.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import dk.au.mad21fall.appproject.justdrink.R;

public class DetailedFragmentAdapter implements GoogleMap.InfoWindowAdapter {
    MapFragment context;

    //Constructor
    public DetailedFragmentAdapter(MapFragment context)
    {this.context = context;}

    @Override
    public View getInfoWindow(Marker marker) {
        //Widgets from Layout, set textview to Android:id.
        View view = LayoutInflater.from(context.context).inflate(R.layout.detailed_view_fragment,null);
        TextView t1_Bar_name = (TextView) view.findViewById(R.id.title);
        TextView t2_Bar_Rating = (TextView) view.findViewById(R.id.Snippet);
        TextView t3_Bar_Adress = (TextView) view.findViewById(R.id.Bar_Adresse);
        TextView t4_Bar_Contacts = (TextView) view.findViewById(R.id.Bar_Contacts);
        TextView t5_Openhours = (TextView) view.findViewById(R.id.Bar_OpenHours);

        //Set widgets from XML to title and snippet.
        LatLng ll = marker.getPosition();
        t1_Bar_name.setText(marker.getTitle());
        t2_Bar_Rating.setText(marker.getSnippet());
        t3_Bar_Adress.setText(marker.getTitle());
        t4_Bar_Contacts.setText(marker.getTitle());
        t5_Openhours.setText(marker.getSnippet());
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        //Return null nothings happens.
        return null;
    }
}
