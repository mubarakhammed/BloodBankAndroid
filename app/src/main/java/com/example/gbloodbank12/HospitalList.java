package com.example.gbloodbank12;

import android.app.Activity;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class HospitalList extends ArrayAdapter<HospitalInformation> {

    private Activity context;
    private List<HospitalInformation> hospitalList;

    public HospitalList(Activity context,List<HospitalInformation> hospitalList){
        super(context,R.layout.hospital_list_layout,hospitalList);
        this.context = context;
        this.hospitalList = hospitalList;
    }

    @NonNull
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItems = inflater.inflate(R.layout.hospital_list_layout,null,true);

        TextView textViewName = listViewItems.findViewById(R.id.textViewName);
        TextView textViewAddress = listViewItems.findViewById(R.id.textViewAddress);
        TextView textViewContactNo = listViewItems.findViewById(R.id.textViewContactNo);
        Button buttonHospital = listViewItems.findViewById(R.id.callHospital);

        HospitalInformation hospital = hospitalList.get(position);

        textViewName.setText("Name: " + hospital.getName());
        textViewAddress.setText("Address: " + hospital.getAddress());
        textViewContactNo.setText("ContactNo.: "+ hospital.getContactNo());
        buttonHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + "09069469999"));
                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    context.startActivity(intent);
                }

            }
        });

        return listViewItems;
    }
}
