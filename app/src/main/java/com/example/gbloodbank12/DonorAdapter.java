package com.example.gbloodbank12;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DonorAdapter extends RecyclerView.Adapter<DonorAdapter.ViewHolder> {



    private List<DonorList> listitems;
    private Context mcontext;

    public DonorAdapter(List<DonorList> listitems, int list_item, Context context) {
        this.listitems = listitems;
        this.mcontext = context;
    }

    @NonNull
    @Override
    public DonorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.feedback_list, parent, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DonorAdapter.ViewHolder holder, int position) {

        final DonorList listHomeItem = listitems.get(position);
       // holder.sender.setText(listHomeItem.getSender());
        holder.message.setText(listHomeItem.getMessage());
        holder.date.setText(listHomeItem.getDate());





    }


    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public Context getContext() {
        return mcontext;
    }

    public void setContext(Context context) {
        this.mcontext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView sender, message, date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           // sender =  itemView.findViewById(R.id.sender);
            message = itemView.findViewById(R.id.mesage);
            date = itemView.findViewById(R.id.fback_date);
        }
    }
}
