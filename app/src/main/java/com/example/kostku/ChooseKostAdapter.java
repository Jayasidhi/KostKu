package com.example.kostku;

import android.content.Intent;
import android.util.Log;
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
import com.example.kostku.model.UserSession;

import java.util.List;

public class ChooseKostAdapter extends RecyclerView.Adapter<ChooseKostAdapter.ViewHolder> {

    private static List<Kost> kostList;
    private ChooseKostAdapterListener mChooseKostAdapterListener;

    int[] images;

    public ChooseKostAdapter(List<Kost> kostList, int[] images, ChooseKostAdapterListener chooseKostAdapterListener) {
        if (kostList.size() > 0) {
            Log.d("D", "ChooseKostAdapter: " + kostList.get(0).getAddress());
        } else {
            Log.d("D", "ChooseKostAdapter: " + "data kosong");
        }
        this.kostList = kostList;
        this.images = images;
        this.mChooseKostAdapterListener = chooseKostAdapterListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kost_row, parent, false);
        view.setClickable(true);
        view.setFocusableInTouchMode(true);

        return new ViewHolder(view, mChooseKostAdapterListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.kostRowName.setText(kostList.get(position).getName());
        holder.kostRowAddress.setText(kostList.get(position).getAddress());
        holder.kostRowImage.setImageResource(images[position]);

        holder.cvKost.setClickable(true);

//        holder.cvKost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Toast.makeText(view.getContext(), "KONTOL " + kostList.get(position).getName(), Toast.LENGTH_LONG).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return kostList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView kostRowImage;
        private final TextView kostRowName, kostRowAddress;
        private ChooseKostAdapterListener chooseKostAdapterListener;

        private final CardView cvKost;

        public ViewHolder(@NonNull View itemView, ChooseKostAdapterListener chooseKostAdapterListener) {
            super(itemView);

            kostRowImage = itemView.findViewById(R.id.kostRowImage);
            kostRowName = itemView.findViewById(R.id.kostRowName);
            kostRowAddress = itemView.findViewById(R.id.kostRowAddress);
            cvKost = itemView.findViewById(R.id.cvKost);
            this.chooseKostAdapterListener = chooseKostAdapterListener;

            cvKost.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            chooseKostAdapterListener.chooseKostAdapterListener(getAdapterPosition());
        }
    }

    public interface ChooseKostAdapterListener {
        void chooseKostAdapterListener(int position);
    }
}
