package com.example.musicplayer;

import static com.example.musicplayer.PlayerActivity.position;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.musicplayer.RealPathUtil.RealPathUtil;
import com.example.musicplayer.adapter.CategoryAdapter;
import com.example.musicplayer.adapter.CategorySpinnerAdapter;
import com.example.musicplayer.api.CategoryApi;
import com.example.musicplayer.api.SongApi;
import com.example.musicplayer.domain.Category;
import com.example.musicplayer.retrofit.RetrofitClient;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSongActivity extends AppCompatActivity {
    Button btnChooseSong, btnChooseImg, btnSubmit;

    EditText edSongName, edAuthor, edSinger;

    Spinner spCategory;
    TextView tvCancel, tvImage, tvSong;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_MP3_REQUEST = 2;
    private Uri mImageUri, mSongUri;

    File fileMp3, fileImage;

    CategoryApi categoryApi;

    SongApi songApi;

    CategorySpinnerAdapter spinneradapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_song);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        init();
        setSpinner();
        setEvent();
    }

    private void setSpinner() {
        categoryApi= RetrofitClient.getInstance().getRetrofit().create(CategoryApi.class);
        categoryApi.getAllCategory().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> categories;
                categories = response.body();
                spinneradapter = new CategorySpinnerAdapter(categories,AddSongActivity.this);
                spCategory.setAdapter(spinneradapter);
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });
    }

    private void setEvent() {
        btnChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        btnChooseSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseSong();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void init(){
        edSongName = findViewById(R.id.edSongName);
        edAuthor = findViewById(R.id.edAuthor);
        edSinger = findViewById(R.id.edSinger);
        spCategory = findViewById(R.id.spCategory);
        btnChooseImg = findViewById(R.id.btnChooseImage);
        btnChooseSong = findViewById(R.id.btnChooseLink);
        btnSubmit = findViewById(R.id.btnSubmit);
        tvCancel = findViewById(R.id.tvCancel);
        tvImage = findViewById(R.id.tvImage);
        tvSong = findViewById(R.id.tvSong);
    }

    //Upload Image
    // This method is called when the user selects an image from their device
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            String IMAGE_PATH= RealPathUtil.getRealPath(this,mImageUri);
            fileImage = new File(IMAGE_PATH);
            tvImage.setText(fileImage.getName());
        } else if (requestCode == PICK_MP3_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mSongUri = data.getData();
            String IMAGE_PATH= RealPathUtil.getRealPath(this,mSongUri);
            // Handle the selected MP3 file here
            fileMp3 = new File(IMAGE_PATH);
            tvSong.setText(fileMp3.getName());
        }
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
        // Tạo một đối tượng RequestBody từ tệp tin
        RequestBody requestFileImage=RequestBody.create(MediaType.parse("multipart/form-data"), fileImage);
        RequestBody requestFileMp3=RequestBody.create(MediaType.parse("multipart/form-data"), fileMp3);

        // Tạo một đối tượng MultipartBody.Part từ RequestBody
        MultipartBody.Part image = MultipartBody.Part.createFormData("image", fileImage.getName(), requestFileImage);
        MultipartBody.Part mp3 = MultipartBody.Part.createFormData("file", fileMp3.getName(), requestFileMp3);

        String songName = edSongName.getText().toString().trim();
        RequestBody requestSongName = RequestBody.create(MediaType.parse("text/plain"), songName);

        String author = edAuthor.getText().toString().trim();
        RequestBody requestAuthor = RequestBody.create(MediaType.parse("text/plain"), author);

        String singer = edSinger.getText().toString().trim();
        RequestBody requestSinger = RequestBody.create(MediaType.parse("text/plain"), singer);

        Category selectedCategory = (Category) spinneradapter.getItem(position);
        Long id  = selectedCategory.getId();
        RequestBody requestIdCategory = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id));

        songApi = RetrofitClient.getRetrofit().create(SongApi.class);
        songApi.createSong(mp3, image, requestSongName, requestAuthor, requestSinger, requestIdCategory).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(AddSongActivity.this, response.body(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
