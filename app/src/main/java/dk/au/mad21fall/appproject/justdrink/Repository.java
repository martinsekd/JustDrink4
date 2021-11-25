package dk.au.mad21fall.appproject.justdrink;

import android.app.Application;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

//Creating repository
class Repository {
    private static final Repository ourInstance = new Repository();
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseStorage mStorage;
    private LiveData<List<String>> getText;
    static Repository getInstance() {
        return ourInstance;
    }

    private Repository() {
        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance();
    }

    //Making repository
    public Repository(Application application){

    }
}
