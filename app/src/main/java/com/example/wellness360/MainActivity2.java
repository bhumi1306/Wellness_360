package com.example.wellness360;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    public static String PREFS_NAME="MyPrefsFile";
    Button bProceed;
    Button twit, facebook;
    EditText etEmail, etPassword;
    private TextInputEditText editusername, editpassword;

    private String username, password;
    Button login;
    TextView signutext;
    private DBHandler dbHandler;
    boolean isAllFieldsChecked = false;

    public ImageView profile;
    public TextView id;
    SignInClient onetap;
    BeginSignInRequest signup;
    private static final int REQ_ONE_TAP = 2;
    private boolean showOneTapUI = true;
    Button gbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        FirebaseApp.initializeApp(this);
        twit = findViewById(R.id.button5);
        facebook = findViewById(R.id.button4);
        editusername = findViewById(R.id.editTextTextEmailAddress);
        editpassword = findViewById(R.id.editTextTextPassword);
        login = findViewById(R.id.button6);
        signutext = findViewById(R.id.textView2);

        onetap = Identity.getSignInClient(this);
        signup= BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(getString(R.string.web_client_id))
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .build();

        gbtn= findViewById(R.id.button2);
        ActivityResultLauncher<IntentSenderRequest> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
               if(result.getResultCode() == Activity.RESULT_OK){
                   try {
                       SignInCredential credential = onetap.getSignInCredentialFromIntent(result.getData());
                       String idToken = credential.getGoogleIdToken();
                       if (idToken !=  null) {
                           String email  = credential.getId();
                           Toast.makeText(MainActivity2.this,"Login Successful",Toast.LENGTH_SHORT).show();
                           SharedPreferences sharedPreferences= getSharedPreferences(MainActivity2.PREFS_NAME,0);
                           SharedPreferences.Editor editor= sharedPreferences.edit();
                           editor.putBoolean("hasLoggedIn",true);
                           editor.commit();
                           openact3();
                       }
                   } catch (ApiException e) {
                     e.printStackTrace();
                   }
               }
            }
        });
        gbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onetap.beginSignIn(signup)
                        .addOnSuccessListener(MainActivity2.this, new OnSuccessListener<BeginSignInResult>() {
                            @Override
                            public void onSuccess(BeginSignInResult result) {
                                IntentSenderRequest intentSenderRequest = new IntentSenderRequest.Builder(result.getPendingIntent().getIntentSender()).build();
                                activityResultLauncher.launch(intentSenderRequest);
                            }
                        })
                        .addOnFailureListener(MainActivity2.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("TAG", e.getLocalizedMessage());
                            }
                        });
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = String.valueOf(editusername.getText());
                password = String.valueOf(editpassword.getText());
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                String URL = "http://192.168.196.203/Wellness360/login.php";
                if(!username.equals("") && !password.equals("")) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("success")) {
                                SharedPreferences sharedPreferences= getSharedPreferences(MainActivity2.PREFS_NAME,0);
                                SharedPreferences.Editor editor= sharedPreferences.edit();
                                editor.putBoolean("hasLoggedIn",true);
                                editor.commit();
                                Toast.makeText(getApplicationContext(), "Login successfull", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Invalid Login Username/Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> data = new HashMap<>();
                            data.put("username", username);
                            data.put("password", password);
                            return data;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
                else{
                    Toast.makeText(getApplicationContext(),"All fields are required!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        signutext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),Signup.class);
                startActivity(intent);
                finish();
            }
        });

        twit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity2.this, Twitteractivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                SharedPreferences sharedPreferences= getSharedPreferences(MainActivity2.PREFS_NAME,0);
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.putBoolean("hasLoggedIn",true);
                editor.commit();
                startActivity(i);
                finish();
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity2.this, FacebookAuthActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                SharedPreferences sharedPreferences= getSharedPreferences(MainActivity2.PREFS_NAME,0);
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.putBoolean("hasLoggedIn",true);
                editor.commit();
                startActivity(i);
                finish();
            }
        });
    }

    void openact3(){
        Intent i = new Intent(MainActivity2.this, MainActivity3.class);
        startActivity(i);
        finish();
    }
    private boolean CheckAllFields() {
        String emailToText = etEmail.getText().toString();
        if (emailToText.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()) {
            Toast.makeText(this, "Enter valid Email address !", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etPassword.length() == 0) {
            etPassword.setError("Password is required");
            return false;
        }
            if (etPassword.length() <= 7) {
            etPassword.setError("Password must be minimum 7 characters");
            return false;
        }
        return true;
    }
}
