package com.koltaapp.koltaguru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BimbinganStudentTaskAct extends AppCompatActivity {

    Button btn_back,add_task;
    TextView nama_mhs;
    DatabaseReference reference,reference2,reference3;
    RecyclerView recyclerView;
    ArrayList<BimbinganStudentTaskItem> bimbinganStudentTaskItems;
    BimbinganStudentTaskAdapter bimbinganStudentTaskAdapter;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    String MhsId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bimbingan_student_task);

        getUsernameLocal();

        btn_back = findViewById(R.id.btn_back);
        add_task = findViewById(R.id.add_task);
        nama_mhs = findViewById(R.id.nama_mhs);

        final String username_baru = getIntent().getStringExtra("username");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bimbinganStudentTaskItems = new ArrayList<BimbinganStudentTaskItem>();

        reference2 = FirebaseDatabase.getInstance().getReference().child("Mahasiswa").child(username_baru);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_mhs.setText(dataSnapshot.child("nama").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("Tugas")
                .child(username_key_new).child("bimbingan").child(username_baru).child("tugas");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    BimbinganStudentTaskItem p = dataSnapshot1.getValue(BimbinganStudentTaskItem.class);
                    bimbinganStudentTaskItems.add(p);
                }
                bimbinganStudentTaskAdapter = new BimbinganStudentTaskAdapter(BimbinganStudentTaskAct.this, bimbinganStudentTaskItems);
                recyclerView.setAdapter(bimbinganStudentTaskAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gototask = new Intent(BimbinganStudentTaskAct.this, BimbinganMainAct.class);
                startActivity(gototask);
            }
        });

        add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoaddtask = new Intent(BimbinganStudentTaskAct.this, AddTaskAct.class);
                gotoaddtask.putExtra("username",username_baru);
                startActivity(gotoaddtask);
            }
        });
    }

    public  void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}