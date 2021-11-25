package dk.au.mad21fall.appproject.justdrink.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dk.au.mad21fall.appproject.justdrink.R;

public class DetailedViewFragment extends Fragment {

    private DetailedViewViewModel mViewModel;


    public static DetailedViewFragment newInstance() {
        return new DetailedViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detailed_view_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetailedViewViewModel.class);
        // TODO: Use the ViewModel
    }

}