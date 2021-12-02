package dk.au.mad21fall.appproject.justdrink.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import dk.au.mad21fall.appproject.justdrink.Model.Location;
import dk.au.mad21fall.appproject.justdrink.Model.OpenHours;
import dk.au.mad21fall.appproject.justdrink.View.StorageActivity;

public class DetailedViewViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private StorageActivity mStorage;
    public MutableLiveData<Location> mLocation;

}