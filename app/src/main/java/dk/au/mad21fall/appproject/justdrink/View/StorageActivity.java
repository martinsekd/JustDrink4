package dk.au.mad21fall.appproject.justdrink.View;

import android.os.Bundle;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StorageActivity extends AppCompatActivity {
    private final String TAG = "java.StorageActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseStorage storage = FirebaseStorage.getInstance();

        includesForCreateReference();
    }

    private void includesForCreateReference() {
        FirebaseStorage storage = FirebaseStorage.getInstance();


        //Create a storage reference from our app
        StorageReference storageRef = storage.getReference();

        //Create Child reference
        //ImageRef points at "Images"
        StorageReference imagesRef = storageRef.child("Images");

        //SpaceRef points at "Images/space.png"
        //ImageRef still points at "images"
        StorageReference spaceRef = storageRef.child("Images/space.png");

        //GetParent allows us to move our reference to a parent node
        imagesRef = spaceRef.getParent();

        //getRoot allows us to move all the way back to the top of our bucket
        //rootRef now points to the root
        StorageReference rootRef = spaceRef.getRoot();

        //earthRef points at "Images/earth.png"
        StorageReference earthRef = spaceRef.getParent().child("earth.png");

        //nullRef is null, since the parent of root is null
        StorageReference nullRef = spaceRef.getRoot().getParent();


        spaceRef.getPath();

        spaceRef.getName();

        spaceRef.getBucket();

        //Points to "Images/space.png"
        String fileName = "";

        String path = spaceRef.getPath();

        String name = spaceRef.getName();

        imagesRef = spaceRef.getParent();



    }
}
