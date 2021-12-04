package dk.au.mad21fall.appproject.justdrink.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.FirebaseDatabase;

import dk.au.mad21fall.appproject.justdrink.Model.Location;
import dk.au.mad21fall.appproject.justdrink.Model.OpenHours;
import dk.au.mad21fall.appproject.justdrink.View.DetailedViewFragment;
import dk.au.mad21fall.appproject.justdrink.View.StorageActivity;

public class DetailedViewViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    public DetailedViewFragment DetailView;
    public MutableLiveData<Location> mLocation;

    private LatLng position;










}