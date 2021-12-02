package dk.au.mad21fall.appproject.justdrink;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

class Repository {
    private static final Repository ourInstance = new Repository();
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;

    static Repository getInstance() {
        return ourInstance;
    }

    private Repository() {
        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

}
