package com.example.a1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {

    private List<HelperClass> doctorList;

    public DoctorAdapter(List<HelperClass> doctorList) {
        this.doctorList = doctorList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HelperClass doctor = doctorList.get(position);
        holder.bind(doctor);
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView doctorNameTextView;
        private TextView doctorSpecializationTextView;
        private TextView doctorHospitalTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorNameTextView = itemView.findViewById(R.id.doctor_name);
            doctorSpecializationTextView = itemView.findViewById(R.id.doctor_specialization);
            doctorHospitalTextView = itemView.findViewById(R.id.doctor_hospital);
        }

        public void bind(HelperClass doctor) {
            doctorNameTextView.setText(doctor.getFullName());
            doctorSpecializationTextView.setText(doctor.getSpecialization());
            doctorHospitalTextView.setText(doctor.getHospital());
        }
    }
}
