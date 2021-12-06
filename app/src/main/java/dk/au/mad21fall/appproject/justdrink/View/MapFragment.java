package dk.au.mad21fall.appproject.justdrink.View;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.w3c.dom.Text;

import dk.au.mad21fall.appproject.justdrink.HelperClasses.JustDrinkViewModelFactory;
import dk.au.mad21fall.appproject.justdrink.Model.Location;
import dk.au.mad21fall.appproject.justdrink.R;
import dk.au.mad21fall.appproject.justdrink.ViewModel.MapFragmentViewModel;
import dk.au.mad21fall.appproject.justdrink.databinding.ActivityMapsBinding;

public class MapFragment extends Fragment implements OnMapReadyCallback{


    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private View view;
    private MapFragmentViewModel vm;
    Context context;






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
            // Add a marker in Sydney and move the camera

            //Info window adaptor
            mMap.setInfoWindowAdapter(new DetailedFragmentAdapter(MapFragment.this));

            //Info window listener
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Toast.makeText(context, "Clicked Info window", Toast.LENGTH_SHORT).show();
                }
            });

           mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
               @Override
               public boolean onMarkerClick(Marker marker) {
                   marker.showInfoWindow();
                   Toast.makeText(context, marker.getTitle(), Toast.LENGTH_SHORT).show();
                   return false;
               }
           });

            return;
        }
        mMap.setMyLocationEnabled(true);
        // Add a marker in Sydney and move the camera
        LatLng aarhus = new LatLng(56.17, 10.2045);
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
                        while (snapshots.iterator().hasNext()) {
                            Location loc = snapshots.iterator().next().getValue(Location.class);
                            MarkerOptions marker = new MarkerOptions().position(new LatLng(loc.lat, loc.long1)).title(loc.name);

                            marker.icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_action_name));
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

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(aarhus, 15.0f));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getContext(), "Du har trykket på " + marker.getTitle(), Toast.LENGTH_SHORT).show();
                DetailedViewFragment detailed = new DetailedViewFragment(marker.getTitle());
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.view_pager, detailed);
                transaction.commit();
                return false;
            }
        });



        }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_maps, container, false);
        vm = new JustDrinkViewModelFactory(getContext()).create(MapFragmentViewModel.class);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SupportMapFragment fragment = new SupportMapFragment();
        transaction.add(R.id.map, fragment);
        transaction.commit();
        context = container.getContext();

        fragment.getMapAsync(this);
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();



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
