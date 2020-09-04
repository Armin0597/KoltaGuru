package com.koltaapp.koltaguru;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BimbinganStudentListAdapter extends RecyclerView.Adapter<BimbinganStudentListAdapter.MyViewHolder>  {

    Context context;
    ArrayList<BimbinganStudentListItem> bimbinganStudentListItems;

    public BimbinganStudentListAdapter(Context c, ArrayList<BimbinganStudentListItem> p){
        context = c;
        bimbinganStudentListItems = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_bimbingan_student_task, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.with(holder.xurl_photo_profile.getContext())
                .load(bimbinganStudentListItems.get(position).getUrl_photo_profile()).centerCrop().fit()
                .into(holder.xurl_photo_profile);
        holder.xnama.setText(bimbinganStudentListItems.get(position).getNama());
        holder.xnim.setText(bimbinganStudentListItems.get(position).getNim());

        final String getImageMahasiswa = bimbinganStudentListItems.get(position).getUrl_photo_profile();
        final String getNamaMahasiswa = bimbinganStudentListItems.get(position).getNama();
        final String getNimMahasiswa = bimbinganStudentListItems.get(position).getNim();
        final String getProdiMahasiswa = bimbinganStudentListItems.get(position).getProdi();
        final String getEmailMahasiswa = bimbinganStudentListItems.get(position).getEmail_address();
        final String getUsernameMahasiswa = bimbinganStudentListItems.get(position).getUsername();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotobimbinganmhs = new Intent(context, BimbinganStudentTaskAct.class);
                gotobimbinganmhs.putExtra("url_photo_profile", getImageMahasiswa);
                gotobimbinganmhs.putExtra("nama", getNamaMahasiswa);
                gotobimbinganmhs.putExtra("nim", getNimMahasiswa);
                gotobimbinganmhs.putExtra("prodi", getProdiMahasiswa);
                gotobimbinganmhs.putExtra("email_address", getEmailMahasiswa);
                gotobimbinganmhs.putExtra("username", getUsernameMahasiswa);
                context.startActivity(gotobimbinganmhs);
            }
        });
    }


    @Override
    public int getItemCount() {
        return bimbinganStudentListItems.size();
    }

    class  MyViewHolder extends RecyclerView.ViewHolder{

        ImageView xurl_photo_profile;
        TextView xnama,xnim;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            xurl_photo_profile = itemView.findViewById(R.id.xurl_photo_profile);
            xnama = itemView.findViewById(R.id.xnama);
            xnim = itemView.findViewById(R.id.xnim);
        }
    }
}
