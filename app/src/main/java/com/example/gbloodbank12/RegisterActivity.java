package com.example.gbloodbank12;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText emailTextView, name;
    private EditText passwordTextView, address;
    private Button signupButton;
    private Button sendemail;
    private TextView loginTextView;
    private Spinner spinnerChoice;
    private ArrayAdapter<CharSequence> adapter;
    private String userChoice;
    public static final String TAG = "FeedBack";
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    private void registerUser() {
        String email = emailTextView.getText().toString().trim();
        String password = passwordTextView.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Please Enter the Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!email.matches("^([a-zA-Z0-9_.!#$%&'*+-/=?^_`{|}~;]+)@([a-zA-Z0-9_.]+)\\.([a-zA-Z]{2,5})$")){
            Toast.makeText(this, "Please Enter Your Correct Email.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Please Enter the Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length()<8){
            Toast.makeText(this, "Password should be minimum 8 characters long.", Toast.LENGTH_SHORT).show();
            return;
        }

        //if validation is ok we will first shows a progress Dialog
        progressDialog.setMessage("Registering user");
        progressDialog.show();

        //creating user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //checking if success
                        if (task.isSuccessful()) {
                            //User is successfully registered and logged in
                            //we will start the profile activity here*/
                            firebaseAuth.getCurrentUser().sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            // progressDialog.dismiss();
                                            if (task.isSuccessful()) {
                                                Toast.makeText(RegisterActivity.this, "Verification email sent to your email", Toast.LENGTH_SHORT).show();

                                            } else {
                                                Toast.makeText(RegisterActivity.this, "Failed to sent Verification email to your email", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });




                            //startActivity(new Intent(getApplicationContext(),ProfileActivity.class))
                           // if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                                if (userChoice.equals("Hospital")) {
                                    startActivity(new Intent(getApplicationContext(),HospitalInfoActivity.class));
                                    finish();
                                } else {
                                    post(name.getText().toString(), address.getText().toString());
                                    startActivity(new Intent(getApplicationContext(),ClientInfoActivity.class));
                                    finish();

                                }

                                // }
                                //else {
                                //Toast.makeText(RegisterActivity.this, "please Verified your email!", Toast.LENGTH_SHORT).show();
                                //}
                            }
                    }
                });
    }


    public void signupButtonClicked(View view){
        registerUser();
    }

    public void loginViewClicked(View view){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        signupButton = findViewById(R.id.signupButton);
        //sendemail=findViewById(R.id.sendemail);
        emailTextView = findViewById(R.id.emailTextView);
        passwordTextView = findViewById(R.id.passwordTextView);
        address = findViewById(R.id.UserAddressTextView);
        name = findViewById(R.id.UserNameTextView);
        loginTextView = findViewById(R.id.loginTextView);

        spinnerChoice = findViewById(R.id.spinnerChoice);
        adapter = ArrayAdapter.createFromResource(this,R.array.userChoice,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChoice.setAdapter(adapter);
        spinnerChoice.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        userChoice = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void post(final String name, final String number ) {
       // showDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        try {
            String url = "https://traceworker.herokuapp.com/api/v1/feedback/create";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Log.d(TAG, "onResponse: " + s);
                    try {
                        JSONObject jObj = new JSONObject(s);
                        String status = jObj.getString("status");
                        switch (status) {
                            case "success":
                               // hideDialog();
                                Toast.makeText(getApplicationContext(), "Feedback posted successfully", Toast.LENGTH_LONG).show();
                              //  message.isEmpty();
                                break;
                            default:
                              //  hideDialog();
                                Toast.makeText(getApplicationContext(), "Error posting notification", Toast.LENGTH_LONG).show();
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                 //   hideDialog();
                    Toast.makeText(getApplicationContext(), "Error, check internet", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onErrorResponse: " + error.toString());

                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    // Posting params to  url
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", "c853d5e0-3724-11eb-bbec-89e9c3193b14");
                    params.put("message", name+" "+number);

                    return params;
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", "Bearer "+"7211|P8hjttmaiCYaLIRGqR5L8kzQd4HW4USMdRVWI7i5");
                    return headers;
                }
            };

            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//
//    private void showDialog() {
//        if (!progressDialog.isShowing())
//            progressDialog.show();
//    }
//
//    private void hideDialog() {
//        if (progressDialog.isShowing())
//            progressDialog.dismiss();
//
//
//    }
}