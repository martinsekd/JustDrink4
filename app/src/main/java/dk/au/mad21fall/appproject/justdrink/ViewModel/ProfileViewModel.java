package dk.au.mad21fall.appproject.justdrink.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dk.au.mad21fall.appproject.justdrink.Model.Gender;
import dk.au.mad21fall.appproject.justdrink.Model.ProfileSettings;

public class ProfileViewModel extends ViewModel {

    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    public MutableLiveData<ProfileSettings> mProfile;

    public ProfileViewModel() {
        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mProfile = new MutableLiveData<ProfileSettings>();


    }

    public void updateSettings(ProfileSettings settings) {
        myRef.setValue(settings);
    }


    // TODO: Implement the ViewModel
}