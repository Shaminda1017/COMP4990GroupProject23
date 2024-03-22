package com.example.a1;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {

    private List<HelperClass> dataList;
    private OnItemClickListener mListener; // Listener member variable

    // Constructor
    public DoctorAdapter(List<HelperClass> dataList) {
        this.dataList = dataList;
    }

    // Interface for item click listener
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Setter method for the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HelperClass doctor = dataList.get(position);
        holder.bind(doctor);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewDoctorName;
        private final TextView textViewDoctorSpecialization;
        private final TextView textViewDoctorHospital;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            textViewDoctorName = itemView.findViewById(R.id.textview_doctor_name);
            textViewDoctorSpecialization = itemView.findViewById(R.id.textview_doctor_specialization);
            textViewDoctorHospital = itemView.findViewById(R.id.textview_doctor_hospital);

            // Set click listener for the item view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

        public void bind(HelperClass doctor) {
            textViewDoctorName.setText(doctor.getFullName());
            textViewDoctorSpecialization.setText(doctor.getSpecialization());
            textViewDoctorHospital.setText(doctor.getHospital());
        }
    }
}
