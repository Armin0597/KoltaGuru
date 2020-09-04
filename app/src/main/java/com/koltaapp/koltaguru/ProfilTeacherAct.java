package com.koltaapp.koltaguru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfilTeacherAct extends AppCompatActivity {

    Button btn_editprofil,btn_back,btn_signout;
    TextView nama,nip,email_address,prodi,xnip,xnama;
    ImageView photo_profile;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_teacher);

        getUsernameLocal();

        //mendaftarkan variabel
        btn_back = findViewById(R.id.btn_back);
        btn_editprofil = findViewById(R.id.btn_editprofil);
        btn_signout = findViewById(R.id.btn_signout);
        nama = findViewById(R.id.nama);
        nip = findViewById(R.id.nip);
        xnip = findViewById(R.id.xnip);
        xnama = findViewById(R.id.xnama);
        photo_profile = findViewById(R.id.photo_profile);
        email_address = findViewById(R.id.email_address);
        prodi = findViewById(R.id.prodi);

        reference = FirebaseDatabase.getInstance().getReference().child("Dosen").child(username_key_new);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama.setText(dataSnapshot.child("nama").getValue().toString());
                nip.setText(dataSnapshot.child("nip").getValue().toString());
                xnip.setText(dataSnapshot.child("nip").getValue().toString());
                xnama.setText(dataSnapshot.child("nama").getValue().toString());
                email_address.setText(dataSnapshot.child("email_address").getValue().toString());
                prodi.setText(dataSnapshot.child("prodi").getValue().toString());
                Picasso.with(ProfilTeacherAct.this).load(dataSnapshot.child("url_photo_profile").getValue().toString())
                        .centerCrop().fit().into(photo_profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_editprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoeditteacherpage = new Intent(ProfilTeacherAct.this, ProfilEditTeacherAct.class);
                startActivity(gotoeditteacherpage);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotohometeacherpage = new Intent(ProfilTeacherAct.this, HomePageTeacherAct.class);
                startActivity(gotohometeacherpage);
            }
        });

        btn_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mengahapus isi/ nilai/ value dari username local
                //menghapus data pada local storage
                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key, null);
                editor.apply();

                //berpindah activity
                Intent gotosignin = new Intent(ProfilTeacherAct.this, SignInTeacherAct.class);
                startActivity(gotosignin);
                finish();
            }
        });

    }

    public  void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}