package com.example.gbloodbank12;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListDonors extends AppCompatActivity {

    ProgressDialog progressDialog;
    public static final String TAG = "Feedbacks";

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    private List<DonorList> listitems;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_donors);



        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Getting donors");
        progressDialog.setCancelable(false);

        recyclerView = (RecyclerView)findViewById(R.id.donorRecylcer);
        recyclerView.setHasFixedSize(true);
        listitems = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DonorAdapter(listitems, R.layout.feedback_list, this);
        recyclerView.setAdapter(adapter);



        getDonors();


    }

    private void getDonors() {

        showDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        try {
            String url = "https://traceworker.herokuapp.com/api/v1/feedback";

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Log.d(TAG, "onResponse: " + s);
                    hideDialog();

                    try {

                        JSONObject jsonObject = new JSONObject(s);
                        JSONArray array = jsonObject.getJSONArray("feedback");
                        for (int j = 0; j < array.length(); j++) {
                            JSONObject o = array.getJSONObject(j);
                            DonorList item = new DonorList(
                                   // o.getString("user_id"),
                                    o.getString("message"),
                                    o.getString("created_at")

                            );
                            listitems.add(item);
                        }

                        adapter = new DonorAdapter(listitems, R.layout.feedback_list, getApplicationContext());
                        recyclerView.setAdapter(adapter);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    hideDialog();
                    // negative_reply();
                    Log.d(TAG, "onErrorResponse: " + error.toString());

                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Accept", "application/json");
                    return headers;
                }
            };
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


        private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();

    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();

    }
}

