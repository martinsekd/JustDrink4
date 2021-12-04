package dk.au.mad21fall.appproject.justdrink.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.FirebaseDatabase;

import dk.au.mad21fall.appproject.justdrink.Model.Location;
import dk.au.mad21fall.appproject.justdrink.Model.OpenHours;
import dk.au.mad21fall.appproject.justdrink.View.StorageActivity;

public class DetailedViewViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    public MutableLiveData<Location> mLocation;
    private Context mContext;

    public DetailedViewViewModel(Context context) {
        mContext = context;
    }










}