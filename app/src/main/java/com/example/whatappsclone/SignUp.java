package com.example.whatappsclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whatappsclone.databinding.ActivitySignInBinding;
import com.example.whatappsclone.databinding.ActivitySignUpBinding;
import com.example.whatappsclone.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
ActivitySignUpBinding binding;
FirebaseAuth auth;
FirebaseDatabase database;
ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        dialog=new ProgressDialog(SignUp.this);
        dialog.setTitle("Creating Account");
        dialog.setMessage("we are creating your account");
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
    binding.Signup.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.show();
            String Email,pass;
            Email=binding.EmailAddress.getText().toString();
            pass=binding.Password.getText().toString();
            auth.createUserWithEmailAndPassword(Email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull  Task<AuthResult> task) {
                    dialog.dismiss();
                    if(task.isSuccessful()){
                        String userName,email,password;
                        userName=binding.userName.getText().toString();
                        email=binding.EmailAddress.getText().toString();
                        password=binding.Password.getText().toString();
                        User user=new User(userName,email,password);
                        String Id=task.getResult().getUser().getUid();
                        database.getReference().child("Users").child(Id).setValue(user);
                        startActivity(new Intent(SignUp.this,MainActivity.class));
                    }else{
                        Toast.makeText(SignUp.this,task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    });
  binding.AlreadyAccount.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          startActivity(new Intent(SignUp.this,SignIn.class));
      }
  });

    }
}