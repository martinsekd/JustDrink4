package dk.au.mad21fall.appproject.justdrink.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import dk.au.mad21fall.appproject.justdrink.Model.Location;
import dk.au.mad21fall.appproject.justdrink.Model.OpenHours;

public class DetailedViewViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private StorageActivity mStorage;
    public MutableLiveData<Location> mLocation;

}