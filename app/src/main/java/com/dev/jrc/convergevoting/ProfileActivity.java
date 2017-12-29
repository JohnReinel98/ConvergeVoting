package com.dev.jrc.convergevoting;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    ImageView profilePic;
    EditText profileName;
    Uri uriprofileImage;
    String profileImageUrl;
    FirebaseAuth mAuth;
    FirebaseUser user;
    TextView profileVerify;

    private static final int CHOOSE_IMAGE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profilePic = findViewById(R.id.profilePic);
        profileName = findViewById(R.id.profileName);
        profileVerify = findViewById(R.id.profileVerify);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        loadUserInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    private void loadUserInfo() {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            if (user.getPhotoUrl() != null) {
                String userPhoto = user.getPhotoUrl().toString();
                Picasso.with(getApplicationContext()).load(userPhoto).into(profilePic);
            }
            if (user.getDisplayName() != null) {
                String displayName = user.getDisplayName();
                profileName.setText(displayName);
            }
            if(user.isEmailVerified()){
                profileVerify.setText("Email Verified");
            }else{
                profileVerify.setText("Email Not Verified");
            }
        }


    }

    public void ProfPicPress(View v){
        imageChooser();
    }

    public void ProfSavePress(View v){
        saveUser();
    }

    public void ProfVerifyPress(View v){
        sendUserVerify();
    }

    private void sendUserVerify() {
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(),"Email Verification Sent!",Toast.LENGTH_LONG).show();
            }
        });
    }


    private void saveUser() {
        String displayName = profileName.getText().toString();

        if(displayName.isEmpty()){
            profileName.setError("Name required!");
            profileName.requestFocus();
            return;
        }

        if(user!=null && profileImageUrl != null){
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .setPhotoUri(Uri.parse(profileImageUrl)).build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"Profile Updated",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            uriprofileImage = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriprofileImage);
                profilePic.setImageBitmap(bitmap);
                uploadImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
        StorageReference profileImage = FirebaseStorage.getInstance().getReference("profilePics/"+System.currentTimeMillis()+".jpg");
        if(uriprofileImage != null){
            profileImage.putFile(uriprofileImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    profileImageUrl = taskSnapshot.getDownloadUrl().toString();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }

    private void imageChooser (){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Select Profile Image"),CHOOSE_IMAGE);
    }
}
