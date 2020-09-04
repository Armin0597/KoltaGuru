package com.koltaapp.koltaguru;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListPresensiAdapter extends RecyclerView.Adapter<ListPresensiAdapter.MyViewHolder> {

    Context context;
    ArrayList<ListPresensiItem> listPresensiItems;

    public ListPresensiAdapter(Context c, ArrayList<ListPresensiItem> p){
        context = c;
        listPresensiItems = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_presensi_item,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.xkegiatan.setText(listPresensiItems.get(position).getKegiatan());
        holder.xdate.setText(listPresensiItems.get(position).getTanggal_pengumpulan());
        holder.xdate_pertemuan.setText(listPresensiItems.get(position).getTanggal_pertemuan());
    }

    @Override
    public int getItemCount() {
        return listPresensiItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView xkegiatan,xdate,xdate_pertemuan;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            xkegiatan = itemView.findViewById(R.id.xkegiatan);
            xdate = itemView.findViewById(R.id.xdate);
            xdate_pertemuan = itemView.findViewById(R.id.xdate_pertemuan);

        }
    }
}
