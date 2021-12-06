package dk.au.mad21fall.appproject.justdrink.View;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import dk.au.mad21fall.appproject.justdrink.HelperClasses.JustDrinkViewModelFactory;
import dk.au.mad21fall.appproject.justdrink.Model.Day;
import dk.au.mad21fall.appproject.justdrink.Model.Location;
import dk.au.mad21fall.appproject.justdrink.R;
import dk.au.mad21fall.appproject.justdrink.ViewModel.DetailedViewViewModel;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class DetailedViewFragment extends Fragment implements OnMapReadyCallback, OnMarkerClickListener {

    private static ActionBar marker;
    //InfoWindow detailed view
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private DetailedViewViewModel vm;
    private GoogleMap mMap;
    private Context context;
    //Widgets
    private ImageView Image_Bar;
    private TextView Bar_Name;
    private TextView Bar_OpenHours;
    private TextView Bar_Contacts;
    private TextView Bar_Adresse;
    private TextView Bar_Rating;







    public DetailedViewFragment(String title) {
        title.equals(Bar_Name);


    }
    public static DetailedViewFragment newInstance() {
        return new DetailedViewFragment((String) marker.getTitle());
    }



    //Set widgets
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.detailed_view_fragment, container, false);

        vm = new JustDrinkViewModelFactory(getContext()).create(DetailedViewViewModel.class);
        View v = inflater.inflate(R.layout.detailed_view_fragment, container, false);

        //Get reference to UI widgets
        Image_Bar = (ImageView) v.findViewById(R.id.Image_Bar);
        Bar_Name = (TextView) v.findViewById(R.id.Bar_Name);
        Bar_OpenHours = (TextView) v.findViewById(R.id.Bar_OpenHours);
        Bar_Contacts = (TextView) v.findViewById(R.id.Bar_Contacts);
        Bar_Adresse = (TextView) v.findViewById(R.id.Bar_Adresse);
        Bar_Rating = (TextView) v.findViewById(R.id.Bar_rating);

        //In fragment setContentView is used as return onCreateView
        return inflater.inflate(R.layout.detailed_view_fragment, container, false);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });

/*
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
*/
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {




            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View v = LayoutInflater.from(getContext()).inflate(R.layout.detailed_view_fragment,null);

                //Setting up infoWindow
                TextView Bar_Name = v.findViewById(R.id.Bar_Name);
                TextView Bar_adresse = v.findViewById(R.id.Bar_Adresse);
                TextView Bar_Contacts = v.findViewById(R.id.Bar_Contacts);
                TextView Bar_openhours = v.findViewById(R.id.Bar_OpenHours);
                TextView Bar_rating = v.findViewById(R.id.Bar_rating);
                ImageView img = v.findViewById(R.id.Image_Bar);
                Bar_Name.setText(marker.getTitle());
                Bar_adresse.setText(marker.getSnippet());


                //Set text for InfoWindow
                Location location = (Location) marker.getTag();
                Bar_rating.setText(location.getRating().toString());
                Bar_Contacts.setText(location.getPhoneNumber());
                Bar_openhours.setText(location.openhours +"");
                img.setImageResource(R.drawable.ic_baseline_local_bar_24);


                return v;
            }
        });
    }







    //SetOnMarkerClickListener
    @Override
    public boolean onMarkerClick(Marker marker) {

        Location location = new Location();
        location.name = Bar_Name.getText().toString();
        location.address = Bar_Adresse.getText().toString();
        String Numberstr = Bar_Contacts.getText().toString();
        location.phoneNumber = Integer.parseInt(Numberstr);
        String Ratingstr = Bar_Rating.getText().toString();
        location.rating = Double.parseDouble(Ratingstr);
        List<Day> openhours = location.openhours;
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
    }




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
