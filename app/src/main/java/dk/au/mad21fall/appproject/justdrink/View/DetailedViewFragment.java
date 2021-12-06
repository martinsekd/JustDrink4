package dk.au.mad21fall.appproject.justdrink.View;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BaseInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

import dk.au.mad21fall.appproject.justdrink.HelperClasses.JustDrinkViewModelFactory;
import dk.au.mad21fall.appproject.justdrink.Model.Day;
import dk.au.mad21fall.appproject.justdrink.Model.Location;
import dk.au.mad21fall.appproject.justdrink.R;
import dk.au.mad21fall.appproject.justdrink.ViewModel.DetailedViewViewModel;

import com.android.volley.Request;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailedViewFragment extends Fragment implements OnMapReadyCallback {

    //InfoWindow detailed view
    private DetailedViewViewModel vm;
    //Widgets
    private ImageView Image_Bar;
    private TextView Bar_name;
    private TextView Bar_OpenHours;
    private TextView Bar_Contacts;
    private TextView Bar_Adresse;
    private TextView Bar_Rating;
    private GoogleMap mMap;
    private MapView mV;
    private FrameLayout fragment;
    private String nameOfPlace;
    Object markerMap;




    public DetailedViewFragment(String barName) {
        nameOfPlace = barName;
    }

    public DetailedViewFragment() {

    }

    public static DetailedViewFragment newInstance() {
        return new DetailedViewFragment();
    }


    //Set widgets
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new JustDrinkViewModelFactory(getContext()).create(DetailedViewViewModel.class);
        //mDetailedView = new ViewModelProvider(this).get(DetailedViewViewModel.class);
        View v = inflater.inflate(R.layout.detailed_view_fragment, container, false);

        Image_Bar = v.findViewById(R.id.Image_Bar);
        Bar_name = v.findViewById(R.id.Bar_Name);
        Bar_OpenHours = v.findViewById(R.id.Bar_OpenHours);
        Bar_Contacts = v.findViewById(R.id.Bar_Contacts);
        Bar_Adresse = v.findViewById(R.id.Bar_Adresse);
        Bar_Rating = v.findViewById(R.id.Bar_rating);

        return v;


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                float pX = (float) latLng.latitude;
                float pY = (float) latLng.longitude;

                mMap.addMarker(new MarkerOptions()
                .position(new LatLng(pX, pY))
                .title("" + Bar_name)).showInfoWindow();
            }
        });

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker marker) {
                View v = LayoutInflater.from(getContext()).inflate(R.layout.detailed_view_fragment,null);
                TextView title = v.findViewById(R.id.Bar_Name);
                TextView snippet = v.findViewById(R.id.Bar_Adresse);
                TextView title1 = v.findViewById(R.id.Bar_Contacts);
                TextView title2 = v.findViewById(R.id.Bar_OpenHours);
                ImageView img = v.findViewById(R.id.Image_Bar);
                title.setText(marker.getTitle());
                snippet.setText(marker.getSnippet());
                title1.setText(marker.getTitle());
                title2.setText(marker.getTitle());
                img.setImageResource(R.drawable.ic_baseline_local_bar_24);

                return v;
            }

            @Override
            public View getInfoContents(Marker marker) {

                return null;
            }
        });




    }





/*

    //SetOnMarkerClickListener
    @Override
    public boolean onMarkerClick(Marker marker) {

        Location mLocation = new Location();
        mLocation.name = Bar_name.getText().toString();
        mLocation.address = Bar_Adresse.getText().toString();
        String Numberstr = Bar_Contacts.getText().toString();
        mLocation.phoneNumer = Integer.parseInt(Numberstr);
        String Ratingstr = Bar_Rating.getText().toString();
        mLocation.rating = Double.parseDouble(Ratingstr);
        List<Day> openhours = mLocation.openhours;
        //Switch case for business days
        int Day = 7;
        switch (Day){
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

        }



        return false;
    }*/




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //mDetailedView = new ViewModelProvider(this).get(DetailedViewViewModel.class);
        // TODO: Use the ViewModel


    }

    /*
        //InfoWindow onChanged override.
        vm.getLocationFromName(nameOfPlace).observe(getViewLifecycleOwner(), new Observer<Location>() {
            @Override
            public void onChanged(Location mLocation) {
                Bar_name.setText(mLocation.name);
                Bar_Contacts.setText(mLocation.phoneNumer + "");
                Bar_Adresse.setText(mLocation.address);
                Bar_Rating.setText(mLocation.rating+"");

                List<Day> openhours = mLocation.openhours;

                //Switch case for business days
                int Day = 7;
                switch (Day){
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

            } }
        });*/



     /* //Add marker to verify code
        final LatLng AarhusLatlng = new LatLng(52.15,10.22);
        Marker Aarhus = mMap.addMarker(
                new MarkerOptions()
                .position(AarhusLatlng)
                .title("" + Bar_name)
                .title("" + Bar_Adresse)
                .title("" + Bar_OpenHours)
                .title("" + Bar_Contacts)
                .title("" + Bar_Rating)
                .snippet(""));*/

}
