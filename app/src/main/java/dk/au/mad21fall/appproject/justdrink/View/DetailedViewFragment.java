package dk.au.mad21fall.appproject.justdrink.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;

import dk.au.mad21fall.appproject.justdrink.Model.Day;
import dk.au.mad21fall.appproject.justdrink.Model.Location;
import dk.au.mad21fall.appproject.justdrink.R;
import dk.au.mad21fall.appproject.justdrink.ViewModel.DetailedViewViewModel;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.Marker;

public class DetailedViewFragment extends Fragment {

    private DetailedViewViewModel mDetailedView;
    private Location mLocation;
    //Widgets
    private ImageView Image_Bar;
    private TextView Bar_name;
    private TextView Bar_OpenHours;
    private TextView Bar_Contacts;
    private TextView Bar_Adresse;
    private TextView Bar_Rating;
    GoogleMap GoogleMap;
    Marker marker_1;




    public static DetailedViewFragment newInstance() {
        return new DetailedViewFragment();
    }

    //Set widgets
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mDetailedView = new ViewModelProvider(this).get(DetailedViewViewModel.class);
        View v = inflater.inflate(R.layout.detailed_view_fragment, container, false);
        Image_Bar = v.findViewById(R.id.Image_Bar);
        Bar_name = v.findViewById(R.id.Bar_Name);
        Bar_OpenHours = v.findViewById(R.id.Bar_OpenHours);
        Bar_Contacts = v.findViewById(R.id.Bar_Contacts);
        Bar_Adresse = v.findViewById(R.id.Bar_Adresse);
        Bar_Rating = v.findViewById(R.id.Bar_Rating);


        //InfoWindow onChanged override.
        mDetailedView.mLocation.observe(getViewLifecycleOwner(), new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                Bar_name.setText(mLocation.name);
                Bar_Contacts.setText(mLocation.phoneNumer);
                Bar_Adresse.setText(mLocation.address);
                Bar_Rating.setText(mLocation.rating+"");
            }
        });
        return v; }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDetailedView = new ViewModelProvider(this).get(DetailedViewViewModel.class);
        // TODO: Use the ViewModel

        //Switch case
        int day = 7;
        switch (day){
            case 0:
                Bar_OpenHours.setText("Monday    16:00 - 05:00");
                break;

            case 1:
                Bar_OpenHours.setText("Tuesday   16:00 - 05:00");
                break;

            case 2:
                Bar_OpenHours.setText("Wednesday   16:00 - 05:00");
                break;

            case 3:
                Bar_OpenHours.setText("Thursday   16:00 - 05:00");
                break;

            case 4:
                Bar_OpenHours.setText("Friday   16:00 - 05:00");
                break;

            case 5:
                Bar_OpenHours.setText("Saturday   16:00 - 05:00");
                break;

            case 6:
                Bar_OpenHours.setText("Sunday  16:00 - 05:00");
                break;

        }}
}