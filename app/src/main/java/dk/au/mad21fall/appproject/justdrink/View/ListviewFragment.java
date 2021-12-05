package dk.au.mad21fall.appproject.justdrink.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dk.au.mad21fall.appproject.justdrink.HelperClasses.JustDrinkViewModelFactory;
import dk.au.mad21fall.appproject.justdrink.Model.Location;
import dk.au.mad21fall.appproject.justdrink.Model.PlaceListLogic.PlaceListAdapter;
import dk.au.mad21fall.appproject.justdrink.Model.Repository;
import dk.au.mad21fall.appproject.justdrink.R;
import dk.au.mad21fall.appproject.justdrink.ViewModel.ListViewFragmentViewModel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ListviewFragment extends Fragment {

    RecyclerView placeListView;
    ListViewFragmentViewModel vm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.listview_fragment, container, false);
        vm = new JustDrinkViewModelFactory(getContext()).create(ListViewFragmentViewModel.class);

        placeListView = v.findViewById(R.id.placeList1);

        //Initialize adapter
        PlaceListAdapter adapter = new PlaceListAdapter(getContext());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        placeListView.setLayoutManager(layoutManager);
        placeListView.setItemAnimator(new DefaultItemAnimator());
        placeListView.setAdapter(adapter);

        vm.getLocations().observe(getViewLifecycleOwner(), new Observer<ArrayList<Location>>() {
            @Override
            public void onChanged(ArrayList<Location> locations) {
                adapter.setPlaceList(locations);
                placeListView.setAdapter(adapter);
            }
        });

        return v;
    }


    public void clickPlace(int i) {

    }
}