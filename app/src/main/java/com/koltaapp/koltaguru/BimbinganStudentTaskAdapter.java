package com.koltaapp.koltaguru;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BimbinganStudentTaskAdapter extends RecyclerView.Adapter<BimbinganStudentTaskAdapter.MyViewHolder> {

    Context context;
    ArrayList<BimbinganStudentTaskItem> bimbinganStudentTaskItems;

    public BimbinganStudentTaskAdapter(Context c, ArrayList<BimbinganStudentTaskItem> p){
        context = c;
        bimbinganStudentTaskItems = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_bimbingan_task_item, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.xnama_tugas.setText(bimbinganStudentTaskItems.get(position).getNama_tugas());
        holder.xdeskripsi.setText(bimbinganStudentTaskItems.get(position).getDeskripsi());
        holder.xdate.setText(bimbinganStudentTaskItems.get(position).getTanggal_pengumpulan());
        holder.xdate_pertemuan.setText(bimbinganStudentTaskItems.get(position).getTanggal_pertemuan());

        final String getNamaTugas = bimbinganStudentTaskItems.get(position).getNama_tugas();
        final String getNamaFile = bimbinganStudentTaskItems.get(position).getNama_file();
        final String getUrlDocument = bimbinganStudentTaskItems.get(position).getUrl_document();
        final String getDeskripsiTugas = bimbinganStudentTaskItems.get(position).getDeskripsi();
        final String getDate = bimbinganStudentTaskItems.get(position).getTanggal_pengumpulan();
        final String getDate_pertemuan = bimbinganStudentTaskItems.get(position).getTanggal_pertemuan();
        final String getUsername = bimbinganStudentTaskItems.get(position).getUsername();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotodetailbimbingan = new Intent(context, BimbinganStudentTaskDetailAct.class);
                gotodetailbimbingan.putExtra("nama_tugas", getNamaTugas);
                gotodetailbimbingan.putExtra("nama_file", getNamaFile);
                gotodetailbimbingan.putExtra("url_document", getUrlDocument);
                gotodetailbimbingan.putExtra("deskripsi", getDeskripsiTugas);
                gotodetailbimbingan.putExtra("tanggal", getDate);
                gotodetailbimbingan.putExtra("tanggal_pertemuan", getDate_pertemuan);
                gotodetailbimbingan.putExtra("username", getUsername);
                context.startActivity(gotodetailbimbingan);
            }
        });

    }


    @Override
    public int getItemCount() {
        return bimbinganStudentTaskItems.size();
    }

    class  MyViewHolder extends RecyclerView.ViewHolder{

        TextView xnama_tugas,xdeskripsi,xdate,xdate_pertemuan;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            xnama_tugas = itemView.findViewById(R.id.xnama_tugas);
            xdeskripsi = itemView.findViewById(R.id.xdeskripsi);
            xdate = itemView.findViewById(R.id.xdate);
            xdate_pertemuan = itemView.findViewById(R.id.xdate_pertemuan);
        }
    }
}
