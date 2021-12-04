package dk.au.mad21fall.appproject.justdrink.Model;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.GnssAntennaInfo;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.android.volley.Response;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.util.concurrent.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import dk.au.mad21fall.appproject.justdrink.Model.PlacesAPILogic.PlaceAPIClass;
import dk.au.mad21fall.appproject.justdrink.Model.PlacesAPILogic.PlaceProvider;

import static android.content.ContentValues.TAG;

public class Repository {
    private static Repository ourInstance;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private ProfileSettings mProfile;
    private String uuid;
    private Context mContext;
    private ExecutorService executor;

    private PlaceProvider placeProvider;
    private PlaceAPIClass placeAPI;


    public static Repository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new Repository(context);
        }
        return ourInstance;
    }


    private Repository(Context context) {
        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mProfile = new ProfileSettings();
        executor = Executors.newSingleThreadExecutor();
        mContext = context;
        placeProvider = new PlaceProvider(mContext);
        placeProvider.startTrack();

        placeAPI = new PlaceAPIClass(context);


        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Task<android.location.Location> loc = placeProvider.mfusedClient.getLastLocation();
        loc.addOnSuccessListener(executor, new OnSuccessListener<android.location.Location>() {
            @Override
            public void onSuccess(android.location.Location location) {
                placeAPI.sendRequest(location.getLatitude(), location.getLongitude(), 1500, new Response.Listener<List<Location>>() {
                    @Override
                    public void onResponse(List<Location> response) {

                        for (Location loc : response) {
                            String barId = mDatabase.getReference().push().getKey();
                            DatabaseReference myORef = FirebaseDatabase.getInstance().getReference("Places/");
                            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Places/" + barId);
                            myORef.orderByChild("name").equalTo(loc.name).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (!snapshot.exists()) {
                                        myRef.setValue(loc);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                });
            }
        });


        /*placeAPI.sendRequest(placeProvider.lastLocation.getLatitude(), placeProvider.lastLocation.getLongitude(), 1500, new Response.Listener<List<Location>>() {
            @Override
            public void onResponse(List<Location> response) {
                int tal = 3;
            }
        });*/

    }

    public LiveData<String> getUUID(Context context) {
        MutableLiveData<String> liveData = new MutableLiveData<String>();

        if (uuid == null) {
            mAuth.signInAnonymously()
                    .addOnCompleteListener(executor, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInAnonymously:success");
                                uuid = mAuth.getCurrentUser().getUid();
                                liveData.postValue(uuid);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInAnonymously:failure", task.getException());
                            }
                        }
                    });
        } else {
            liveData.postValue(uuid);
        }
        return liveData;
    }

    public void getDistanceFromPlace(double lat, double lng,Response.Listener<Float> listener) {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<android.location.Location> loc = placeProvider.mfusedClient.getLastLocation();
        loc.addOnSuccessListener(executor, new OnSuccessListener<android.location.Location>() {
            @Override
            public void onSuccess(android.location.Location location) {
                float distance[] = new float[2];
                android.location.Location.distanceBetween(location.getLatitude(),location.getLongitude(),lat,lng,distance);
                listener.onResponse(distance[0]);

            }
        });
    }
    public void asd() {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    mProfile = snapshot.getValue(ProfileSettings.class);
                } else {
                    ProfileSettings profile1 = new ProfileSettings();
                    profile1.firstName = "";
                    profile1.age = 0;
                    profile1.gender = Gender.OTHER;
                    myRef.setValue(profile1);
                    mProfile = profile1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
