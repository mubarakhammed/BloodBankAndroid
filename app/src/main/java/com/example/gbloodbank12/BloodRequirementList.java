package com.example.gbloodbank12;

import android.app.Activity;
import androidx.annotation.NonNull;

import android.app.ProgressDialog;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class BloodRequirementList extends ArrayAdapter<BloodRequirement> {

    private Activity context;
    private List<BloodRequirement> requirementList;
    private android.os.Handler mhandler;
    ProgressDialog progressDialog;
    private Runnable mrunnable;

    public BloodRequirementList(Activity context,List<BloodRequirement> requirementList){
        super(context,R.layout.requirement_list_layout,requirementList);
        this.context = context;
        this.requirementList = requirementList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItems = inflater.inflate(R.layout.requirement_list_layout,null,true);

        TextView textViewName = listViewItems.findViewById(R.id.rtextViewName);
        TextView textViewBlood = listViewItems.findViewById(R.id.rtextViewBlood);
        TextView textViewContactNo = listViewItems.findViewById(R.id.rtextViewContactNo);
        TextView textViewHName = listViewItems.findViewById(R.id.rtextViewHName);
        TextView textViewHContactNo = listViewItems.findViewById(R.id.rtextViewHContactNo);
        Button donate = listViewItems.findViewById(R.id.donate);


        BloodRequirement requirement = requirementList.get(position);

        textViewName.setText("Patient_Name: " + requirement.getClientName());
        textViewBlood.setText("BloodGroup: " + requirement.getBloodGroup());
        textViewContactNo.setText("P_ContactNo.: "+ requirement.getC_clientNo());
        textViewHName.setText("Hospital_Name: " + requirement.getHospitalName());
        textViewHContactNo.setText("H_ContactNo.: "+ requirement.getH_contactNo());

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);


        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                mrunnable = new Runnable() {
                    @Override
                    public void run() {
                        hideDialog();
                        Toast.makeText(getContext(), "the hospital will get in touch as soon as possible", Toast.LENGTH_LONG).show();
                    }
                };
                mhandler = new Handler();
                mhandler.postDelayed(mrunnable, 3000);

            }

        });

        return listViewItems;
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
