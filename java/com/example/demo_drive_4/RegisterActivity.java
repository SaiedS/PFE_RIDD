package com.example.demo_drive_4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class RegisterActivity extends AppCompatActivity {


    EditText regName,regEmail,regPassword;
    Button regButton;
    private FirebaseAuth auth;
    ImageView userImage;
    static int REQUEST_CODE=1;
    Uri pickedImUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regName=findViewById(R.id.reg_name);
        regEmail=findViewById(R.id.reg_email);
        regPassword=findViewById(R.id.reg_password);
        regButton=findViewById(R.id.register_button);
        userImage = findViewById(R.id.reg_image);

        auth = FirebaseAuth.getInstance();

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();


            }
        });
    }

    private void openGallery() {
        startActivityForResult(new Intent().setAction(Intent.ACTION_GET_CONTENT).setType("image/*"),REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && requestCode==RESULT_OK){
            if(data != null){
                pickedImUri = data.getData();
                userImage.setImageURI(pickedImUri);
            }
        }

    }

    private void registerUser() {
        String name,email,password;
        name=regName.getText().toString().trim();
        email=regEmail.getText().toString().trim();
        password=regPassword.getText().toString().trim();
        if(name.isEmpty()|| TextUtils.isEmpty(email)|| password.isEmpty()){
            Toast.makeText(this,"Merci de remplir toute les cases",Toast.LENGTH_SHORT).show();
        }else{
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this,"Inscription réussi",Toast.LENGTH_SHORT).show();
                        updateUI(name,pickedImUri,auth.getCurrentUser());
                    }else{
                        Toast.makeText(RegisterActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }


    }

    private void updateUI(String name, Uri pickedImUri, FirebaseUser currentUser) {
        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("user_image");
        final StorageReference imFilePath = mStorage.child(pickedImUri.getLastPathSegment());
        imFilePath.putFile(pickedImUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                         UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).setPhotoUri(uri).build();
                         currentUser.updateProfile(changeRequest);
                    }
                });
            }
        });
    }
}