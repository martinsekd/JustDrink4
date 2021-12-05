package dk.au.mad21fall.appproject.justdrink.Model;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.GnssAntennaInfo;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import dk.au.mad21fall.appproject.justdrink.Model.PlacesAPILogic.PlaceAPIClass;
import dk.au.mad21fall.appproject.justdrink.Model.PlacesAPILogic.PlaceProvider;
import dk.au.mad21fall.appproject.justdrink.R;
import dk.au.mad21fall.appproject.justdrink.Service.OfferMessage;
import dk.au.mad21fall.appproject.justdrink.Service.PlaceOfferService;

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

    DatabaseReference myRef;

    private Intent service;

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

        service = new Intent(mContext, PlaceOfferService.class);
        mContext.startService(service);
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

    public void getDistanceFromPlace(double lat, double lng, Response.Listener<Float> listener) {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<android.location.Location> loc = placeProvider.mfusedClient.getLastLocation();
        loc.addOnSuccessListener(executor, new OnSuccessListener<android.location.Location>() {
            @Override
            public void onSuccess(android.location.Location location) {
                float distance[] = new float[2];
                android.location.Location.distanceBetween(location.getLatitude(), location.getLongitude(), lat, lng, distance);
                listener.onResponse(distance[0] / 1000);

            }
        });
    }

    private double metersToLatDegree(int meters) {
        return ((double) meters / 111000);
    }

    public void getOffer(Response.Listener<OfferMessage> listener) {
        DatabaseReference offerRef = FirebaseDatabase.getInstance().getReference("Offers");
        getUUID(mContext).observe((AppCompatActivity) mContext, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                DatabaseReference visitPlaceRef = FirebaseDatabase.getInstance().getReference("UserVisit/" + s);
                visitPlaceRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Iterable<DataSnapshot> snapshots = snapshot.getChildren();

                        while (snapshots.iterator().hasNext()) {
                            String uid = snapshot.getKey();
                            offerRef.child(uid).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot ofSnapshot) {
                                    if (ofSnapshot.exists()) {

                                        float[] distance = new float[2];
                                        FirebaseDatabase.getInstance().getReference("Places/" + uid).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot placeSnapshot) {
                                                Location loc = placeSnapshot.getValue(Location.class);
                                                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                                    return;
                                                }
                                                Task<android.location.Location> userLoc = placeProvider.mfusedClient.getLastLocation();
                                                userLoc.addOnSuccessListener(executor, new OnSuccessListener<android.location.Location>() {
                                                    @Override
                                                    public void onSuccess(android.location.Location location) {
                                                        android.location.Location.distanceBetween(loc.lat,loc.long1,location.getLatitude(),location.getLongitude(),distance);
                                                        if(distance[0]<20) {
                                                            OfferMessage msg = ofSnapshot.getValue(OfferMessage.class);
                                                            listener.onResponse(msg);
                                                        }
                                                    }
                                                });

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });


                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

    public void sendOffer(OfferMessage msg) {
        DatabaseReference offerRef = FirebaseDatabase.getInstance().getReference("Offers");
        DatabaseReference placeRef = FirebaseDatabase.getInstance().getReference("Places");

        placeRef.orderByChild("name").equalTo(msg.owner).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String uid = snapshot.getKey();
                    offerRef.child(uid).setValue(msg);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //Places around user within radius in meters
    public void insertNearPlaces(int radius) {
        DatabaseReference placeRef = FirebaseDatabase.getInstance().getReference("Places");

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("UserVisit/"+getUUID(mContext).getValue());
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<android.location.Location> loc = placeProvider.mfusedClient.getLastLocation();
        loc.addOnSuccessListener(executor, new OnSuccessListener<android.location.Location>() {
            @Override
            public void onSuccess(android.location.Location location) {
                //filter serverSide
                placeRef.orderByChild("lat").startAt(location.getLatitude()-metersToLatDegree(radius)).endAt(location.getLatitude()+metersToLatDegree(radius)).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //ArrayList<Location> locations = new ArrayList<Location>();
                        Iterable<DataSnapshot> snapshots = snapshot.getChildren();

                        //filter clientSide
                        while(snapshots.iterator().hasNext()) {
                            Location loc = snapshots.iterator().next().getValue(Location.class);
                            float distance[] = new float[2];
                            android.location.Location.distanceBetween(location.getLatitude(),location.getLongitude(),loc.lat,loc.long1,distance);
                            if(distance[0]<radius) {
                                userRef.push().setValue(loc.name);
                                //locations.add(loc);
                            }
                        }

                        //add to nearby for User

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

    MutableLiveData<ArrayList<Location>> livedata;
    public MutableLiveData<ArrayList<Location>> getLocations() {
        livedata = new MutableLiveData<ArrayList<Location>>();
        executor.submit(new Runnable() {
            @Override
            public void run() {
                myRef = FirebaseDatabase.getInstance().getReference("Places");
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<Location> locations = new ArrayList<Location>();

                        Iterable<DataSnapshot> snapshots = snapshot.getChildren();
                        while(snapshots.iterator().hasNext()) {
                            Location loc = snapshots.iterator().next().getValue(Location.class);
                            locations.add(loc);
                        }
                        livedata.setValue(locations);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        return livedata;
    }

    MutableLiveData<ProfileSettings> profileLiveData = new MutableLiveData<ProfileSettings>();

    public void updateSettings(ProfileSettings settings) {
        getUUID(mContext).observe((AppCompatActivity) mContext, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users/" + s);
                myRef.setValue(settings);
            }
        });
    }
    public MutableLiveData<ProfileSettings> getProfileSettings() {
        getUUID(mContext).observe((AppCompatActivity)mContext, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users/"+s);
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            mProfile = snapshot.getValue(ProfileSettings.class);
                        } else {
                            ProfileSettings profile1 = new ProfileSettings();
                            profile1.firstName = "User1";
                            profile1.age = 0;
                            profile1.gender = Gender.OTHER;
                            myRef.setValue(profile1);
                            mProfile = profile1;
                        }
                        profileLiveData.setValue(mProfile);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        return profileLiveData;
    }

    private MutableLiveData<Location> locationByNameLiveData = new MutableLiveData<Location>();
    public MutableLiveData<Location> getLocation(String barName) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Places");
        myRef.orderByChild("name").equalTo(barName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    Location bar = snapshot.getChildren().iterator().next().getValue(Location.class);
                    locationByNameLiveData.setValue(bar);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return locationByNameLiveData;
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
