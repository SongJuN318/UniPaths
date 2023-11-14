package com.example.unipaths.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.unipaths.R;
import com.example.unipaths.Models.UserHelperClass;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class SignUpPage extends AppCompatActivity {

    private EditText userName, userPassword, userPassword2, userEmail;
    private ProgressBar loadingProgress;
    private Button signup, signin, googleBtn, fbBtn;
    private FirebaseAuth mAuth;

    CallbackManager callbackManager;
    FirebaseDatabase database;
    DatabaseReference reference;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        callbackManager = CallbackManager.Factory.create();
        userName = findViewById(R.id.username_tb);
        userEmail = findViewById(R.id.email_tb);
        userPassword = findViewById(R.id.password_tb);
        userPassword2 = findViewById(R.id.confirm_password_tb);
        loadingProgress = findViewById(R.id.progressBar);
        signup = findViewById(R.id.signup_btn);
        loadingProgress.setVisibility(View.INVISIBLE);
        signin = findViewById(R.id.signin_btn);
        googleBtn = findViewById(R.id.signin_google_btn);
        fbBtn = findViewById(R.id.login_fb_btn);

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        startActivity(new Intent(SignUpPage.this, DiscussionForum.class));
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        fbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(SignUpPage.this, Arrays.asList("public_profile"));
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpPage.this, SignInPage.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");
                loadingProgress.setVisibility(View.VISIBLE);
                final String password = userPassword.getText().toString();
                final String password2 = userPassword2.getText().toString();
                final String name =  userName.getText().toString();
                final String email = userEmail.getText().toString();

                if(email.isEmpty() || password.isEmpty() || password2.isEmpty() || name.isEmpty() || !password.equals(password2)){
                    showMessage("Please Verify all fields");
                    loadingProgress.setVisibility(View.GONE);
                }else{
                    loadingProgress.setVisibility(View.VISIBLE);
                    CreateUserAccount(email, name, password);
                }
            }
        });

    }

    void signIn(){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle Facebook Login
        callbackManager.onActivityResult(requestCode, resultCode, data);

        // Handle Google Login
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                navigateToSecondAcitivity();
            } catch (ApiException e) {
                String errorMessage = "Google sign in failed: " + e.getStatusCode() + " " + e.getMessage();
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
            }
        }
    }

    void navigateToSecondAcitivity(){
        finish();
        Intent intent = new Intent(SignUpPage.this, DiscussionForum.class);
        startActivity(intent);
    }

    private void CreateUserAccount(String email, String name, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            String userid = firebaseUser.getUid();
                            String bio = "Busy";
                            String imageurl = "https://firebasestorage.googleapis.com/v0/b/unipaths-adb69.appspot.com/o/profile_icon.png?alt=media&token=bfae9b79-9bc4-4c77-b718-72a4051d1341";
                            UserHelperClass helperClass = new UserHelperClass(name, email, password, userid, bio, imageurl);
                            loadingProgress.setVisibility(View.GONE);
                            reference.child(userid).setValue(helperClass);
                            Toast.makeText(SignUpPage.this, "Account created", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Exception e = task.getException();
                            String error = e != null ? e.getMessage() : "Unknown error";
                            Toast.makeText(SignUpPage.this, "Authentication failed: " + error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
    }


}