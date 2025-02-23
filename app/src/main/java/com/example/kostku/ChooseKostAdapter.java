package com.example.kostku;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kostku.model.Kost;

import java.util.List;

public class ChooseKostAdapter extends RecyclerView.Adapter<ChooseKostAdapter.ViewHolder> {

    private List <Kost> kostList;

    public ChooseKostAdapter(List<Kost> kostList) {
        this.kostList = kostList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kost_row,parent,false);
        view.setClickable(true);
        view.setFocusableInTouchMode(true);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.kostRowName.setText(kostList.get(position).getName());
        holder.kostRowAddress.setText(kostList.get(position).getAddress());

        holder.cvKost.setClickable(true);

        holder.cvKost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "KONTOL " + kostList.get(position).getName(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return kostList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView kostRowImage;
        private final TextView kostRowName, kostRowAddress;

        private final CardView cvKost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            kostRowImage = itemView.findViewById(R.id.kostRowImage);
            kostRowName = itemView.findViewById(R.id.kostRowName);
            kostRowAddress = itemView.findViewById(R.id.kostRowAddress);
            cvKost = itemView.findViewById(R.id.cvKost);
        }
    }
}
