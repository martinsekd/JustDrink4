package dk.au.mad21fall.appproject.justdrink.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
import java.util.List;
import java.util.concurrent.Executor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import dk.au.mad21fall.appproject.justdrink.MapsActivity;
import dk.au.mad21fall.appproject.justdrink.Model.Day;
import dk.au.mad21fall.appproject.justdrink.Model.DrinkItems;
import dk.au.mad21fall.appproject.justdrink.Model.Location;
import dk.au.mad21fall.appproject.justdrink.R;
import dk.au.mad21fall.appproject.justdrink.databinding.ActivityMapsBinding;

import static android.content.ContentValues.TAG;

public class MapFragment extends Fragment implements OnMapReadyCallback{

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private View view;

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
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

                            marker.icon(bitmapDescriptorFromVector(getActivity(),R.drawable.ic_action_name));
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_maps, container, false);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SupportMapFragment fragment = new SupportMapFragment();
        transaction.add(R.id.map, fragment);
        transaction.commit();

        fragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        mAuth.signInAnonymously()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInAnonymously:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                        }
                    }
                });

        database = FirebaseDatabase.getInstance();

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
    }

    @Override
    public void onPause() {
        super.onPause();
        user = mAuth.getCurrentUser();
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
