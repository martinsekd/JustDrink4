package dk.au.mad21fall.appproject.justdrink.View;

import android.net.Uri;
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

import java.io.File;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.IOException;

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

    public void includeForDownloadFiles() throws IOException {
        FirebaseStorage storage = FirebaseStorage.getInstance();

        //START Download

        StorageReference storageRef = storage.getReference();

        StorageReference pathReference = storageRef.child("images/stars.jpg");

        StorageReference gsReference = storage.getReferenceFromUrl("gs://bucket/images/stars.jpg");


        StorageReference httpsReference = storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/b/bucket/o/images%20stars.jpg");

        //End Download ref


        //START download to memory

        StorageReference islandRef = storageRef.child("images/island.jpg");

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        //END download to memory

        // [START download_to_local_file]
        islandRef = storageRef.child("images/island.jpg");

        File localFile = File.createTempFile("images", "jpg");

        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Local temp file has been created
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        // [END download_to_local_file]

        // [START download_via_url]
        storageRef.child("users/me/profile.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        // [END download_via_url]

        // [START download_full_example]
        storageRef.child("users/me/profile.png").getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Use the bytes to display the image
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        // [END download_full_example]
    }
}

