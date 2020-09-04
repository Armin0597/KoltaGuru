package com.koltaapp.koltaguru;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class AddTaskAct extends AppCompatActivity {

    Button btn_save;
    LinearLayout btn_back;
    EditText nama_mhs,edit_nama_tugas,edit_file_tugas,edit_deskripsi_tugas,edit_tanggal,edit_tanggal_pertemuan;
    DatePickerDialog.OnDateSetListener mDatesetListener,mDatesetListener_pertemuan;

    DatabaseReference reference,reference2,ref_username_dosen,ref_username_mhs;
    StorageReference storage;

    Uri doc_location;
    Integer doc_max = 1;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        getUsernameLocal();

        final String new_username = getIntent().getStringExtra("username");


        nama_mhs = findViewById(R.id.nama_mhs);
        edit_nama_tugas = findViewById(R.id.edit_nama_tugas);
        edit_file_tugas = findViewById(R.id.edit_file_tugas);
        edit_deskripsi_tugas = findViewById(R.id.edit_deskripsi_tugas);
        edit_tanggal = findViewById(R.id.edit_tanggal);
        edit_tanggal_pertemuan = findViewById(R.id.edit_tanggal_pertemuan);
        btn_save = findViewById(R.id.btn_save);
        btn_back = findViewById(R.id.btn_back);

        nama_mhs.setEnabled(false);
        edit_nama_tugas.requestFocus();

        edit_tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int  day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddTaskAct.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDatesetListener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        edit_tanggal_pertemuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int  day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddTaskAct.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDatesetListener_pertemuan, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDatesetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "-" + month + "-" + year;
                edit_tanggal.setText(date);
            }
        };

        mDatesetListener_pertemuan = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "-" + month + "-" + year;
                edit_tanggal_pertemuan.setText(date);
            }
        };

        edit_file_tugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findDoc();
            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("Mahasiswa").child(new_username);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_mhs.setText(dataSnapshot.child("nama").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storage = FirebaseStorage.getInstance().getReference().child("Drafusers").child(username_key_new).child(edit_nama_tugas.getText().toString());
                reference2 = FirebaseDatabase.getInstance().getReference().child("Tugas")
                        .child(username_key_new).child("bimbingan").child(new_username).child("tugas").child(edit_nama_tugas.getText().toString());

                final String fileName;
                Cursor cursor = getContentResolver().query(doc_location,null,null,null,null);
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                fileName = cursor.getString(idx);
                cursor.close();

                final StorageReference storageReference =
                        storage.child(fileName);
                storageReference.putFile(doc_location).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String uri_doc = uri.toString();
                                reference2.getRef().child("url_document").setValue(uri_doc);
                                reference2.getRef().child("nama_file").setValue(fileName);
                                reference2.getRef().child("nama_tugas").setValue(edit_nama_tugas.getText().toString());
                                reference2.getRef().child("deskripsi").setValue(edit_deskripsi_tugas.getText().toString());
                                reference2.getRef().child("tanggal_pertemuan").setValue(edit_tanggal_pertemuan.getText().toString());
                                reference2.getRef().child("tanggal_pengumpulan").setValue(edit_tanggal.getText().toString());
                                reference2.getRef().child("username").setValue(new_username);
                                //ubah state menjadi loading
                                btn_save.setEnabled(false);
                                btn_save.setText("Loading ...");

                                Intent gotobimbingan = new Intent(AddTaskAct.this, BimbinganStudentTaskAct.class);
                                gotobimbingan.putExtra("username",new_username);
                                startActivity(gotobimbingan);
                            }
                        });
                    }
                });
                ref_username_dosen = FirebaseDatabase.getInstance().getReference().child("Tugas").child(username_key_new).child("username");
                ref_username_dosen.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ref_username_dosen.getRef().setValue(username_key_new);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                ref_username_mhs = FirebaseDatabase.getInstance().getReference().child("Tugas")
                        .child(username_key_new).child("bimbingan").child(new_username).child("username");
                ref_username_mhs.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ref_username_mhs.getRef().setValue(new_username);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotobimbingan = new Intent(AddTaskAct.this, BimbinganStudentTaskAct.class);
                gotobimbingan.putExtra("username",new_username);
                startActivity(gotobimbingan);
            }
        });
    }

    String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    //menemukan dokumen
    public void findDoc(){
        Intent doc = new Intent();
        doc.setType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        doc.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(doc, doc_max);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == doc_max && resultCode == RESULT_OK && data != null && data.getData() != null) {
            doc_location = data.getData();

            final String fileName;
            Cursor cursor = getContentResolver().query(doc_location,null,null,null,null);
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            fileName = cursor.getString(idx);
            cursor.close();
            edit_file_tugas.setText(fileName);
        }
    }

    public  void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}