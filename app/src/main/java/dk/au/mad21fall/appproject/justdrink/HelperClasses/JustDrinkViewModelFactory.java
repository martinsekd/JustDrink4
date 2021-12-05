package dk.au.mad21fall.appproject.justdrink.HelperClasses;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dk.au.mad21fall.appproject.justdrink.ViewModel.ChatFragmentViewModel;
import dk.au.mad21fall.appproject.justdrink.ViewModel.DetailedViewViewModel;
import dk.au.mad21fall.appproject.justdrink.ViewModel.ListViewFragmentViewModel;
import dk.au.mad21fall.appproject.justdrink.ViewModel.MapFragmentViewModel;
import dk.au.mad21fall.appproject.justdrink.ViewModel.ProfileViewModel;

//inspired by Procedure in answer:
//https://stackoverflow.com/questions/46283981/android-viewmodel-additional-arguments
public class JustDrinkViewModelFactory implements ViewModelProvider.Factory {

    Context mContext;

    public JustDrinkViewModelFactory(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (ListViewFragmentViewModel.class.equals(modelClass)) {
            return (T) new ListViewFragmentViewModel(mContext);
        } else if (ChatFragmentViewModel.class.equals(modelClass)) {
            return (T) new ChatFragmentViewModel(mContext);
        } else if (DetailedViewViewModel.class.equals(modelClass)) {
            return (T) new DetailedViewViewModel(mContext);
        } else if (MapFragmentViewModel.class.equals(modelClass)) {
            return (T) new MapFragmentViewModel(mContext);
        } else if (ProfileViewModel.class.equals(modelClass)) {
            return (T) new ProfileViewModel(mContext);
        }
        return (T) new MapFragmentViewModel(mContext);

    }
}
