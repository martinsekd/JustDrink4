package dk.au.mad21fall.appproject.justdrink.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import dk.au.mad21fall.appproject.justdrink.Model.Location;
import dk.au.mad21fall.appproject.justdrink.Model.OpenHours;
import dk.au.mad21fall.appproject.justdrink.Model.Repository;
import dk.au.mad21fall.appproject.justdrink.View.DetailedViewFragment;
import dk.au.mad21fall.appproject.justdrink.View.StorageActivity;

public class DetailedViewViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    public MutableLiveData<Location> mLocation;

    private Context mContext;



    public DetailedViewViewModel(Context context) {
        mContext = context;
        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mLocation = new MutableLiveData<Location>();


    }

    public MutableLiveData<Location> getLocationFromName(String barName) {
        return Repository.getInstance(mContext).getLocation(barName);
    }

    public void updateView(Location mLocation) {mRef.setValue(mLocation);
    }
}