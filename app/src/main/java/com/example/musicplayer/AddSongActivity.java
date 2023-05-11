package com.example.musicplayer;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;

public class AddSongActivity extends AppCompatActivity {
    Button btnChooseSong, btnChooseImg, btnAdd;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_MP3_REQUEST = 2;
    private Uri mImageUri, mSongUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);

        init();
        setEvent();
    }

    private void setEvent() {
        btnChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        btnChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseSong();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
    }

    private void init(){
        btnChooseImg = findViewById(R.id.btnChooseImage);
        btnChooseSong = findViewById(R.id.btnChooseLink);
        btnAdd = findViewById(R.id.btnAddSong);
    }

    //Upload Image
    // This method is called when the user selects an image from their device
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();

            // Create a File object from the Uri
            File file = new File(getRealPathFromURI(mImageUri));

            System.out.println(file);
        } else if (requestCode == PICK_MP3_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mSongUri = data.getData();
            // Handle the selected MP3 file here

            File file = new File(getRealPathFromURI(mSongUri));
            System.out.println(mSongUri);
        }
    }

    // This method is used to get the real path of a Uri
    public String getRealPathFromURI(Uri contentUri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        cursor.close();
        return filePath;
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void chooseSong() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/mpeg");
        startActivityForResult(intent, PICK_MP3_REQUEST);
    }

    private void add(){

    }
}