package com.example.weprotector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdoptionRequestAdapter extends RecyclerView.Adapter<AdoptionRequestAdapter.ViewHolder> {

    private Context context;
    private List<AdoptionRequest> requestList;

    public AdoptionRequestAdapter(Context context, List<AdoptionRequest> requestList) {
        this.context = context;
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_adoption_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AdoptionRequest request = requestList.get(position);

        // Bind data
        holder.tvPetName.setText("Pet: " + request.petName);
        holder.tvBreed.setText("Breed: " + request.breed);
        holder.tvAge.setText("Age: " + request.age);
        holder.tvAdopterName.setText("Adopter: " + request.adopterName);
        holder.tvAdopterPhone.setText("Phone: " + request.adopterPhone);
        holder.tvAdopterEmail.setText("Email: " + request.adopterEmail);
        holder.tvStatus.setText("Status: " + request.status);

        // Accept button listener
        holder.btnAccept.setOnClickListener(v -> {
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("adoptionRequests");
            dbRef.child(request.requestId).child("status").setValue("accepted")
                    .addOnSuccessListener(aVoid -> Toast.makeText(context, "Request Accepted", Toast.LENGTH_SHORT).show());
        });

        // Reject button listener
        holder.btnReject.setOnClickListener(v -> {
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("adoptionRequests");
            dbRef.child(request.requestId).child("status").setValue("rejected")
                    .addOnSuccessListener(aVoid -> Toast.makeText(context, "Request Rejected", Toast.LENGTH_SHORT).show());
        });
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPetName, tvBreed, tvAge, tvAdopterName, tvAdopterPhone, tvAdopterEmail, tvStatus;
        Button btnAccept, btnReject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPetName = itemView.findViewById(R.id.tvPetName);
            tvBreed = itemView.findViewById(R.id.tvBreed);
            tvAge = itemView.findViewById(R.id.tvAge);
            tvAdopterName = itemView.findViewById(R.id.tvAdopterName);
            tvAdopterPhone = itemView.findViewById(R.id.tvAdopterPhone);
            tvAdopterEmail = itemView.findViewById(R.id.tvAdopterEmail);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnAccept = itemView.findViewById(R.id.btnAccept);
            btnReject = itemView.findViewById(R.id.btnReject);
        }
    }
}
