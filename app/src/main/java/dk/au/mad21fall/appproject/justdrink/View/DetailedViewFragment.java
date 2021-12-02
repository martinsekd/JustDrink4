package dk.au.mad21fall.appproject.justdrink.ui.main;

import androidx.lifecycle.LifecycleOwner;
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

import dk.au.mad21fall.appproject.justdrink.Model.Location;
import dk.au.mad21fall.appproject.justdrink.Model.OpenHours;
import dk.au.mad21fall.appproject.justdrink.R;

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


    public static DetailedViewFragment newInstance() {
        return new DetailedViewFragment();
    }

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


        mDetailedView.mLocation.observe(getViewLifecycleOwner(), new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                Bar_name.setText(mLocation.name);
                Bar_Contacts.setText(mLocation.phoneNumer);
                Bar_Adresse.setText(mLocation.address);
                Bar_Rating.setText("");



            }
        });
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDetailedView = new ViewModelProvider(this).get(DetailedViewViewModel.class);
        // TODO: Use the ViewModel

    }

}