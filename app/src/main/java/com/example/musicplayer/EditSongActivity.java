package com.example.musicplayer;

import static com.example.musicplayer.PlayerActivity.position;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.musicplayer.RealPathUtil.RealPathUtil;
import com.example.musicplayer.adapter.CategorySpinnerAdapter;
import com.example.musicplayer.api.CategoryApi;
import com.example.musicplayer.api.SongApi;
import com.example.musicplayer.domain.Category;
import com.example.musicplayer.domain.Song;
import com.example.musicplayer.domain.SongMessage;
import com.example.musicplayer.domain.SongUpdate;
import com.example.musicplayer.retrofit.RetrofitClient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditSongActivity extends AppCompatActivity {
    Button btnChooseSong, btnChooseImg, btnSubmit;
    TextView tvCancel, tvSong, tvImage;
    int currentPosition;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_MP3_REQUEST = 2;
    private Uri mImageUri, mSongUri;

    List<Category> categories;

    EditText edSongName,edAuthor,edSinger;
    Spinner spCategory;

    Song song;

    SongApi songApi;
    CategoryApi categoryApi;

    CategorySpinnerAdapter spinneradapter;

    File fileMp3, fileImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_song);
        init();
    }

    private void setSpinner() {
        spCategory = findViewById(R.id.spCategory);
        categoryApi= RetrofitClient.getInstance().getRetrofit().create(CategoryApi.class);
        List<Category> categoryList;
        categoryApi.getAllCategory().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                categories = response.body();
                spinneradapter = new CategorySpinnerAdapter(categories,EditSongActivity.this);
                spCategory.setAdapter(spinneradapter);
                int i =0,pos =0;
                for(Category category:categories)
                {
                    if(category.getName().equals(song.getCategory().getName())){
                        pos = i;
                    }
                    i++;
                }
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
                submit();
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
        btnChooseImg = findViewById(R.id.btnChooseImage);
        btnChooseSong = findViewById(R.id.btnChooseLink);
        btnSubmit = findViewById(R.id.btnSubmit);
        tvCancel = findViewById(R.id.tvCancel);
        edSongName = findViewById(R.id.edSongName);
        edAuthor = findViewById(R.id.edAuthor);
        edSinger = findViewById(R.id.edSinger);
        tvSong = findViewById(R.id.tvSong);
        tvImage = findViewById(R.id.tvImage);
        setSpinner();
        loadData();
        setEvent();
    }

    private void loadData(){

        Intent intent = getIntent();
        song =(Song) intent.getSerializableExtra("data");
        edSongName.setText(song.getName());
        edAuthor.setText(song.getAuthor());
        edSinger.setText((song.getSinger()));
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

    private void submit(){
        songApi= RetrofitClient.getInstance().getRetrofit().create(SongApi.class);
        SongUpdate songUpdate = new SongUpdate();
        songUpdate.setAuthor(edAuthor.getText().toString());
        songUpdate.setName(edSongName.getText().toString());
        songUpdate.setSinger(edSinger.getText().toString());
        songUpdate.setCategory((Category) spinneradapter.getItem(position));

        Long id = song.getId();
        System.out.println(id);
        System.out.println(songUpdate.getCategory().getName());
        songApi.update(id, songUpdate).enqueue(new Callback<SongMessage>() {
            @Override
            public void onResponse(Call<SongMessage> call, Response<SongMessage> response) {
                System.out.println("---------------");
                Toast.makeText(EditSongActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT);
//                System.out.println(response.body().getMessage());
            }

            @Override
            public void onFailure(Call<SongMessage> call, Throwable t) {

            }
        });

    }
}

