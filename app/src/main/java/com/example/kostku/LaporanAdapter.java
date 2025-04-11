package com.example.kostku;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kostku.model.Laporan;
import com.example.kostku.model.UserSession;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LaporanAdapter extends RecyclerView.Adapter<LaporanAdapter.ViewHolder> {
    private List<Laporan> laporanList;


    public LaporanAdapter(List<Laporan> laporanList) {
        this.laporanList = laporanList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.laporan_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LaporanAdapter.ViewHolder holder, int position) {
        Laporan laporanitem = laporanList.get(position);

        holder.kategori.setText(laporanitem.getKategori());
        holder.rowIsiKeluhan.setText(laporanitem.getIsi_keluhan());
        if (laporanitem.getStatus_laporan().equals("0")) {
            holder.rowStatusLaporan.setText("Diproses");
        } else {
            holder.rowStatusLaporan.setText("Selesai");
        }
        holder.date.setText(laporanitem.getTanggal_lapor());
        holder.kamar.setText(laporanitem.getRoom_name());

        if (UserSession.getInstance().getRole() != 0 || laporanitem.getStatus_laporan().equals("1")) {
            holder.completeLaporan.setVisibility(View.GONE);
        }

        holder.completeLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Button Clicked!", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Apakah Laporan Telah Selesai Diproses ?");

                builder.setPositiveButton("Sudah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //update status laporan
                        DatabaseReference mDatabase;
                        mDatabase = FirebaseDatabase.getInstance("https://kostku-89690-default-rtdb.firebaseio.com/").getReference().child("laporan").child(laporanitem.getId());
                        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Map<String, Object> laporanUpdate = new HashMap<>();
                                laporanUpdate.put("status_laporan", "1");
                                mDatabase.updateChildren(laporanUpdate);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        Toast.makeText(view.getContext(), "Thank You!", Toast.LENGTH_SHORT).show();
                        holder.completeLaporan.setVisibility(View.GONE);
                    }
                });

                builder.setNegativeButton("Belum", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getContext(), "Thank You!", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return laporanList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView kategori, rowIsiKeluhan, rowStatusLaporan, date, kamar;
        private Button completeLaporan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kategori = itemView.findViewById(R.id.laporan_title);
            rowIsiKeluhan = itemView.findViewById(R.id.isi_laporan);
            rowStatusLaporan = itemView.findViewById(R.id.status_laporan);
            kamar = itemView.findViewById(R.id.kamar_laporan);
            date = itemView.findViewById(R.id.tanggal_laporan);
            completeLaporan = itemView.findViewById(R.id.done_laporan_button);


//            itemView.findViewById(R.id.done_laporan_button).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(view.getContext(), "Button Clicked!", Toast.LENGTH_SHORT).show();
//                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                    builder.setTitle("Apakah Laporan Telah Selesai Diproses ?");
//
//                    builder.setPositiveButton("Sudah", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//
//                            //update status laporan
//                            DatabaseReference mDatabase;
//                            mDatabase = FirebaseDatabase.getInstance("https://kostku-89690-default-rtdb.firebaseio.com/").getReference().child("laporan").child(roomId);
//                            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                    Map<String, Object> kostUpdate = new HashMap<>();
//                                    kostUpdate.put("isbooked", true);
//                                    kostUpdate.put("checkout_date", checkout_date);
//                                    mDatabase.updateChildren(kostUpdate);
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                }
//                            });
//
//
//                            Toast.makeText(view.getContext(), "Thank You!", Toast.LENGTH_SHORT).show();
//                            itemView.findViewById(R.id.done_laporan_button).setVisibility(View.GONE);
//                            // logic update status dan pindah ke history
//                        }
//                    });
//
//                    builder.setNegativeButton("Belum", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            Toast.makeText(view.getContext(), "Thank You!", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                    builder.show();
//                }
//            });


//

        }
    }
}
