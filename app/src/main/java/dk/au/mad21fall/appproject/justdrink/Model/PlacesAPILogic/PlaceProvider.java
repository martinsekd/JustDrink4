package dk.au.mad21fall.appproject.justdrink.Model.PlacesAPILogic;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.tasks.OnSuccessListener;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class PlaceProvider {
    public FusedLocationProviderClient mfusedClient;
    LocationCallback mlocationCallback;
    public Location lastLocation;
    Context mContext;
    LocationRequest mLocationRequest;

    public PlaceProvider(Context context) {
        mContext = context;

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return;
        }
        mfusedClient = LocationServices.getFusedLocationProviderClient(mContext);

        mlocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if(locationResult!=null) {
                    for (Location location : locationResult.getLocations()) {
                        lastLocation = location;
                    }
                }
            }
        };

    }

    public LocationCallback getCallback() {
        return mlocationCallback;
    }
    public void stopTracking() {
        if (mfusedClient != null) {
            mfusedClient.removeLocationUpdates(mlocationCallback);
        }
    }

    public void startTrack() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(6 * 1000);
        mLocationRequest.setFastestInterval(3 * 1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return;
        }

        mfusedClient.requestLocationUpdates(mLocationRequest, mlocationCallback, Looper.getMainLooper());
    }
}
