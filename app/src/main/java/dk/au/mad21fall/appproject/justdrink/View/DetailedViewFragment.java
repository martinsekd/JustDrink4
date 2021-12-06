package dk.au.mad21fall.appproject.justdrink.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;

import dk.au.mad21fall.appproject.justdrink.HelperClasses.JustDrinkViewModelFactory;
import dk.au.mad21fall.appproject.justdrink.Model.Day;
import dk.au.mad21fall.appproject.justdrink.Model.Location;
import dk.au.mad21fall.appproject.justdrink.R;
import dk.au.mad21fall.appproject.justdrink.ViewModel.DetailedViewViewModel;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.Marker;

public class DetailedViewFragment extends Fragment implements OnMarkerClickListener {

    //InfoWindow detailed view
    private DetailedViewViewModel vm;
    //Widgets
    private ImageView Image_Bar;
    private TextView Bar_name;
    private TextView Bar_OpenHours;
    private TextView Bar_Contacts;
    private TextView Bar_Adresse;
    private TextView Bar_Rating;

    private String nameOfPlace;



    public DetailedViewFragment() {

    }

    public DetailedViewFragment(String barname) {
        nameOfPlace = barname;
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
        Bar_name = v.findViewById(R.id.title);
        Bar_OpenHours = v.findViewById(R.id.Bar_OpenHours);
        Bar_Contacts = v.findViewById(R.id.Bar_Contacts);
        Bar_Adresse = v.findViewById(R.id.Bar_Adresse);
        Bar_Rating = v.findViewById(R.id.Snippet);



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
        });



        return v; }


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
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //mDetailedView = new ViewModelProvider(this).get(DetailedViewViewModel.class);
        // TODO: Use the ViewModel


    }
}

    //@Override
    //public boolean onMarkerClick(Marker marker) {
      //  if (marker.equals(mAarhus)) {
    //      //Causes the marker to jump into position when clicked
    //      final Handler handler = new Handler();
    //      final long start = SystemClock.uptimeMillis();
    //      final long duration = 1500;
//
    //          final Interpolator interpolator = new BounceInterpolator();
    //      handler.post(new Runnable() {
    //          @Override
    //          public void run() {
    //              long elapsed = SystemClock.uptimeMillis() - start;
    //              float t = Math.max(
    //                      1 - interpolator.getInterpolation((float) elapsed / duration), 0);
    //              marker.setAnchor(0.5f, 1.0f + 2 * t);
//
    //                  if (t > 0.0) {
    //                  // Post again 16ms later.
    //                  handler.postDelayed(this, 16);
    //              }
    //          }
    //      });
    //  }

    //  return false;
    //}
