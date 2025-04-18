package com.example.kostku;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kostku.model.Transaction;

import java.util.List;

public class ManagementKostAdapter extends RecyclerView.Adapter<ManagementKostAdapter.ViewHolder> {

    private List<Transaction> transactionList;

    public ManagementKostAdapter(List<Transaction> transactionList){
        this.transactionList = transactionList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expired_kost_row, parent, false);

        return new ManagementKostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagementKostAdapter.ViewHolder holder, int position) {
        holder.fullName.setText(transactionList.get(position).getName());
        holder.phoneNumber.setText(transactionList.get(position).getPhone_number());
        holder.roomNumber.setText(transactionList.get(position).getRoom_number());
        holder.expiredDate.setText(transactionList.get(position).getCheckout_date());
        holder.checkinDate.setText(transactionList.get(position).getCheckin_date());
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView fullName, phoneNumber, roomNumber, expiredDate, checkinDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.tv_nama_penghuni);
            phoneNumber = itemView.findViewById(R.id.tv_notelp_penghuni);
            roomNumber = itemView.findViewById(R.id.tv_kamar_penghuni);
            expiredDate = itemView.findViewById(R.id.tv_checkout_penghuni);
            checkinDate = itemView.findViewById(R.id.tv_checkin_penghuni);
        }
    }
}
