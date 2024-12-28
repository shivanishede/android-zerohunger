package com.example.zerohunger;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ReceiverAdapter extends RecyclerView.Adapter<ReceiverAdapter.ReceiverViewHolder> {

    private List<DataModel> dataList;
    private List<DataModel> filteredList;

    public ReceiverAdapter(List<DataModel> dataList) {
        this.dataList = dataList;
        this.filteredList = new ArrayList<>(dataList);
    }

    @NonNull
    @Override
    public ReceiverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receiver_item, parent, false);
        return new ReceiverViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiverViewHolder holder, int position) {
        DataModel data = filteredList.get(position);

        // Bind data to views
        holder.nameTextView.setText(data.getName());
        holder.phoneTextView.setText(data.getPhoneNumber());
        holder.emailTextView.setText(data.getMailId());
        holder.dateTextView.setText(data.getDate());
        holder.expiryDateTextView.setText(data.getExpiryDate());
        holder.addressTextView.setText(data.getAddress());
        holder.pincodeTextView.setText(data.getPincode());
        holder.quantityTextView.setText(data.getQuantity());

        holder.locationButton.setOnClickListener(v -> {
            String address = data.getAddress();
            String pincode = data.getPincode();
            openGoogleMaps(v, address, pincode);  // Pass both address and pincode
        });

    }

    private void openGoogleMaps(View view, String address, String pincode) {
        if (address != null && !address.isEmpty() && pincode != null && !pincode.isEmpty()) {
            // Create a URI for the address and pincode
            String mapUri = "geo:0,0?q=" + address + ", " + pincode;  // geo URI with both address and pincode
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse(mapUri));
            mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  // Ensure it opens as a new task
            view.getContext().startActivity(mapIntent);  // Directly open the map
        } else {
            Toast.makeText(view.getContext(), "Address or Pincode not available", Toast.LENGTH_SHORT).show();
        }
    }




    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void updateData(List<DataModel> newDataList) {
        this.dataList = newDataList;
        this.filteredList = new ArrayList<>(newDataList); // Reset filteredList to show all data
        notifyDataSetChanged();
    }

    public void filter(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(dataList); // Show all items when the query is empty
        } else {
            for (DataModel data : dataList) {
                if (data.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(data);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ReceiverViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, phoneTextView, emailTextView, dateTextView, expiryDateTextView,
                addressTextView, pincodeTextView, quantityTextView ;
        Button locationButton;

        public ReceiverViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            expiryDateTextView = itemView.findViewById(R.id.expiryDateTextView);
            addressTextView = itemView.findViewById(R.id.addressTextView);
            pincodeTextView = itemView.findViewById(R.id.pincodeTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            locationButton = itemView.findViewById(R.id.locationButton);
        }
    }
}
