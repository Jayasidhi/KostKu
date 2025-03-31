package com.example.kostku;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kostku.model.Laporan;

import java.util.List;

public class LaporanAdapter extends RecyclerView.Adapter<LaporanAdapter.ViewHolder> {

    private List <Laporan> laporanList;

    public LaporanAdapter(List<Laporan> laporanList) {
        this.laporanList = laporanList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.laporan_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LaporanAdapter.ViewHolder holder, int position) {
        Laporan laporanitem = laporanList.get(position);

        holder.kategori.setText(laporanitem.getKategori());
        holder.rowIsiKeluhan.setText(laporanitem.getIsiKeluhan());
        holder.rowStatusLaporan.setText(laporanitem.getStatusLaporan());
        holder.date.setText((CharSequence) laporanitem.getTanggalLapor());

    }

    @Override
    public int getItemCount() {
        return laporanList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView kategori, rowIsiKeluhan, rowStatusLaporan, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kategori = itemView.findViewById(R.id.laporan_title);
            rowIsiKeluhan = itemView.findViewById(R.id.isi_laporan);
            rowStatusLaporan = itemView.findViewById(R.id.status_laporan);
            date = itemView.findViewById(R.id.tanggal_laporan);
        }
    }
}
