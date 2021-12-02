package dk.au.mad21fall.appproject.justdrink.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import dk.au.mad21fall.appproject.justdrink.Model.Location;
import dk.au.mad21fall.appproject.justdrink.R;
import dk.au.mad21fall.appproject.justdrink.databinding.ActivityMapsBinding;

import static android.content.ContentValues.TAG;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInAnonymously:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            Toast.makeText(MapsActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("message");

        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        /*String barId = database.getReference().push().getKey();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Places/"+barId);
        Location loc = new Location();
        loc.name = "Australian Bar";
        loc.address = "Bugten";
        List<Day> openhours = new ArrayList<Day>();
        openhours.add(new Day(00,00));
        openhours.add(new Day(18,02));
        openhours.add(new Day(18,02));
        openhours.add(new Day(18,02));
        openhours.add(new Day(18,05));
        openhours.add(new Day(18,05));
        openhours.add(new Day(00,00));

        List<DrinkItems> drinks = new ArrayList<DrinkItems>();
        DrinkItems ol = new DrinkItems();
        ol.name = "Øl";
        ol.description = "Lavet på byg";
        ol.image = "";
        ol.price = 19.95;
        drinks.add(ol);

        loc.drinkItems = drinks;
        loc.openhours = openhours;

        myRef.setValue(loc);*/

        /*myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> listText = new ArrayList<String>();
                Iterable<DataSnapshot> snapshots = snapshot.getChildren();
                while(snapshots.iterator().hasNext()) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        /*myRef.child("message").setValue("Test15")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed

                    }
                });*/

        if(isNetworkAvailable(this)) {

        } else {
            Toast.makeText(this,"Ingen internet",Toast.LENGTH_SHORT).show();
        }
        /*
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String test = snapshot.getKey();

                int t = 3;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myRef.push().getKey();
        /*myRef.setValue("dd4").addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Firebase",e.getLocalizedMessage());
            }
        });*/
        //myRef.push().setValue("dd1");
        /*
        String key1 = myRef.push().getKey();
        Log.d("firebase",key1);
        myRef.child(key1).setValue("test1");
        //myRef.push().setValue("dd3");


        /*we work here */
        /*
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue().toString();
                int tal = 3;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myRef.push().setValue("Hello, World!");

        myRef.push().setValue("Hello, World!").addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Firebase",e.getLocalizedMessage());
            }
        });

        //add a comment
        //com
        // Read from the database

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //HashMap value = dataSnapshot.getValue(HashMap.class);
                //Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        */
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return;
        }
        mMap.setMyLocationEnabled(true);
        // Add a marker in Sydney and move the camera
        LatLng aarhus = new LatLng(56.17,10.2045);
        MarkerOptions a1 = new MarkerOptions().position(aarhus).title("Aarhus");

        mMap.addMarker(new MarkerOptions().position(aarhus).title("Marker in Aarhus"));

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Places");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<String> listText = new ArrayList<String>();
                        Iterable<DataSnapshot> snapshots = snapshot.getChildren();
                        while(snapshots.iterator().hasNext()) {
                            Location loc = snapshots.iterator().next().getValue(Location.class);
                            MarkerOptions marker = new MarkerOptions().position(new LatLng(loc.lat,loc.long1)).title(loc.name);

                            //marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_name));
                            mMap.addMarker(marker);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(aarhus,15.0f));
    }

    @Override
    protected void onPause() {
        super.onPause();
        user = mAuth.getCurrentUser();
    }

    public static boolean isNetworkAvailable(Context con) {
        try {
            ConnectivityManager cm = (ConnectivityManager) con
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}