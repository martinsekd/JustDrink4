package dk.au.mad21fall.appproject.justdrink.ViewModel;

import android.content.Context;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import dk.au.mad21fall.appproject.justdrink.Model.Location;
import dk.au.mad21fall.appproject.justdrink.Model.Repository;

public class ListViewFragmentViewModel extends ViewModel {
    private Context mContext;
    private MutableLiveData<ArrayList<Location>> mLocationList;

    public ListViewFragmentViewModel(Context context) {
        mContext = context;

        mLocationList = new MutableLiveData<>();

    }

    public MutableLiveData<ArrayList<Location>> getLocations() {
        //if(mLocationList==null) {
        //    mLocationList = Repository.getInstance(mContext).getLocations();
        //}
        return Repository.getInstance(mContext).getLocations();
        /*if(mLocationList==null) {
            mLocationList = new MutableLiveData<ArrayList<Location>>();
            Repository.getInstance(mContext).getLocations().observe((AppCompatActivity) mContext, new Observer<ArrayList<Location>>() {
                @Override
                public void onChanged(ArrayList<Location> locations) {
                    mLocationList.setValue(locations);
                }
            });
        }
        return mLocationList;*/
    }
}
