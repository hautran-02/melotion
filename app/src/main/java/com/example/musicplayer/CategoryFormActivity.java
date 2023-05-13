package com.example.musicplayer;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicplayer.domain.Category;

import java.io.File;

public class CategoryFormActivity extends AppCompatActivity {
    ImageButton  btnChooseImg;
    Button btnSubmit, btnCancel;
    TextView tvCancel, tvImg, tvTitle, tvSubmit;
    int currentPosition;
    Category data, obj;
    Boolean isEditForm;
    EditText edName, edDescription;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_MP3_REQUEST = 2;
    private Uri mImageUri, mSongUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_category);

        init();
    }

    private void setEvent() {
        btnChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadData(){
        //   Kiểm tra: nếu có dữ liệu là form edit, khong là form add
        if(data != null){
            isEditForm = true;
            tvTitle.setText(getResources().getString(R.string.edit_category));
            edName.setText(data.getName());
            edDescription.setText(data.getDescription());
            tvImg.setText(data.getImage());
        } else {
            isEditForm = false;
            tvTitle.setText(getResources().getString(R.string.add_category));
            edName.setText("");
            edDescription.setText("");
            tvImg.setText(getResources().getString(R.string.choose_img));
        }
    }

    private void init(){
        btnChooseImg = findViewById(R.id.btnUpCategoryImg);
        btnSubmit = findViewById(R.id.btnCategorySubmit);
        btnCancel = findViewById(R.id.btnCategoryCancel);
        edName = findViewById(R.id.edCategoryName);
        edDescription = findViewById(R.id.edCategoryDescription);
        tvImg = findViewById(R.id.tvCategoryImg);
        tvTitle = findViewById(R.id.tvTitleCategory);

        Intent intent = getIntent();
        data = (Category) intent.getSerializableExtra("data");

        setEvent();
        loadData();
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

    private void submit(){
        obj.setName(String.valueOf(edName.getText()));
        obj.setDescription(String.valueOf(edDescription.getText()));
        obj.setImage(String.valueOf(tvImg.getText()));

        if(isEditForm){
            edit();
        } else {
            add();
        }
    }

    private void add(){

    }

    private void edit(){

    }
}
