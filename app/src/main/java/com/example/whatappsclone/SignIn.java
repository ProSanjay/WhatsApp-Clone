package com.example.whatappsclone;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.whatappsclone.databinding.ActivitySignInBinding;
import com.example.whatappsclone.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class SignIn extends AppCompatActivity {
 ActivitySignInBinding binding;
  public  FirebaseAuth auth;
  ProgressDialog dialog;
  GoogleSignInClient mGoogleSignInClient;
  FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        database=FirebaseDatabase.getInstance();
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        dialog=new ProgressDialog(SignIn.this);
        dialog.setTitle("Logging In");
        dialog.setMessage("We are logging into your account");
        auth=FirebaseAuth.getInstance();
       binding.SignIn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               {
                   dialog.show();
                   String email, pass;
                   email = binding.signInEmailAddress.getText().toString();
                   pass = binding.SignInPassword.getText().toString();
                   auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           dialog.dismiss();
                           if (task.isSuccessful()) {
                               Intent intent = new Intent(SignIn.this, MainActivity.class);
                               startActivity(intent);

                           } else {
                               Toast.makeText(SignIn.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
               }
           }
       });
        if(auth.getCurrentUser()!=null){
            startActivity(new Intent(SignIn.this,MainActivity.class));
            finish();
        }

binding.CreateNewaccount.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(SignIn.this,SignUp.class));
    }
});
  binding.Siningoogle.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          someActivityResultLauncher.launch(new Intent(mGoogleSignInClient.getSignInIntent()));
      }
  });

    }
    
    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                      Intent data=new Intent(result.getData());

                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        try {
                            // Google Sign In was successful, authenticate with Firebase
                            GoogleSignInAccount account = task.getResult(ApiException.class);
                            Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                            firebaseAuthWithGoogle(account.getIdToken());
                        } catch (ApiException e) {
                            // Google Sign In failed, update UI appropriately
                            Log.w("TAG", "Google sign in failed", e);
                        }
                    }
                }
            });




    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
                            User users=new User();
                            users.setUserName(user.getDisplayName());
                            users.setProfilePic(user.getPhotoUrl().toString());
                            users.setUserId(user.getUid());
                            database.getReference().child("Users").child(user.getUid()).setValue(users);
//                            updateUI(user);
                            startActivity(new Intent(SignIn.this,MainActivity.class));
                            Toast.makeText(SignIn.this, "Sign in with google", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignIn.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });
    }

}