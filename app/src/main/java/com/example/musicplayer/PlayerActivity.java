package com.example.musicplayer;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.media.AsyncPlayer;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.autofill.AutofillManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

import com.example.musicplayer.SQLite.DatabaseHelper;
import com.example.musicplayer.adapter.SongAdapter;
import com.example.musicplayer.api.FavouriteApi;
import com.example.musicplayer.api.SongApi;
import com.example.musicplayer.api.UserApi;
import com.example.musicplayer.domain.FavouriteMessage;
import com.example.musicplayer.domain.Song;
import com.example.musicplayer.domain.SongMessage;
import com.example.musicplayer.domain.User;
import com.example.musicplayer.retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayerActivity extends AppCompatActivity {

    TextView tvSongNamePlayer, tvTotalTime, tvTime;
    CircleImageView imgDisc;
    SeekBar seekBar;
    ImageView imgPre, imgPlay, imgNext, favorite;
    ObjectAnimator objectAnimator;

    MediaPlayer mediaPlayer;

    private SongApi songApi;

    private SongAdapter mSongAdapter;
    static int position;

    static Boolean favourite;
    List<Song> songs;
    FavouriteApi favouriteApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Intent intent = getIntent();
        //Long id = intent.getLongExtra("data",0);
        position = intent.getIntExtra("position", 0);
        songs =(List<Song>) intent.getSerializableExtra("songs");
        Song songCurrent = songs.get(position);
        init();
        playMusic(songCurrent);
    }

    private void setFavourite(Song song){
        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        /// Viet tiep
        favouriteApi = RetrofitClient.getInstance().getRetrofit().create(FavouriteApi.class);

        favouriteApi.findFavorite(song.getId(),user.getId()).enqueue(new Callback<FavouriteMessage>() {
            @Override
            public void onResponse(Call<FavouriteMessage> call, Response<FavouriteMessage> response) {
                FavouriteMessage favouriteMessage = response.body();
                if(favouriteMessage.getMessage().equals("Successful")){
                    favorite.setImageResource(R.drawable.ic_favorite_black);
                    favourite = true;
                }
                else {
                    favorite.setImageResource(R.drawable.ic_favorite_white);
                    favourite = false;
                }
            }

            @Override
            public void onFailure(Call<FavouriteMessage> call, Throwable t) {

            }
        });
    }

    private void playMusic(){
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            objectAnimator.pause();
            imgPlay.setImageResource(R.drawable.ic_play);
        } else {
            mediaPlayer.start();
            imgPlay.setImageResource(R.drawable.ic_pause);
            objectAnimator.resume();
        }
    }
    private void playNextSong(List<Song> songs ){
        int len = songs.size();
        if (position < len - 1)
        {
            mediaPlayer.stop();
            position+=1;
        }
        else {
            mediaPlayer.stop();
            position = 0;
        }
        objectAnimator.pause();;
        playMusic(songs.get(position));
    }
    private void playPreSong(List<Song> songs){
        int len = songs.size();
        if (position == 0)
        {
            position = len - 1;
        }
        else {
            position -= 1;
        }
        mediaPlayer.stop();
        objectAnimator.pause();;
        playMusic(songs.get(position));
    }

    private void favoriteSong(Song song){
        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        favouriteApi = RetrofitClient.getInstance().getRetrofit().create(FavouriteApi.class);
        if(favourite == false){
            favouriteApi.addFavourite(song.getId(),user.getId()).enqueue(new Callback<FavouriteMessage>() {
                @Override
                public void onResponse(Call<FavouriteMessage> call, Response<FavouriteMessage> response) {
                    FavouriteMessage favouriteMessage = response.body();
                    favorite.setImageResource(R.drawable.ic_favorite_black);
                    favourite = true;
                    System.out.println(favouriteMessage.getMessage());
                }

                @Override
                public void onFailure(Call<FavouriteMessage> call, Throwable t) {

                }
            });
        }
        else {
            System.out.println("11111111------------");
            favouriteApi.deleteFavorite(song.getId(),user.getId()).enqueue(new Callback<FavouriteMessage>() {
                @Override
                public void onResponse(Call<FavouriteMessage> call, Response<FavouriteMessage> response) {
                    FavouriteMessage favouriteMessage = response.body();
                    System.out.println(favouriteMessage.getMessage());
                    favorite.setImageResource(R.drawable.ic_favorite_white);
                    favourite = false;
                }

                @Override
                public void onFailure(Call<FavouriteMessage> call, Throwable t) {

                }
            });
        }

        /// Viet tiep

    }
    private void rontation() {
        imgDisc.setRotation(45);
        objectAnimator = ObjectAnimator.ofFloat(imgDisc, "rotation",0f,360f);
        objectAnimator.setDuration(10000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
    }
    private void playMusic(Song song){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.addData(song.getId());
        List<Long> longs= dbHelper.getAllData();
        System.out.println("-----------");
        for(Long t:longs) {
            System.out.println(t);
        }
        favourite = false;
        setFavourite(song);
        tvSongNamePlayer.setText(song.getName()+"("+song.getSinger()+"-"+song.getAuthor()+")");
        imgPlay.setImageResource(R.drawable.ic_pause);
        tvSongNamePlayer.setSelected(true);
        Picasso.get().load(song.getImage()).into(imgDisc);
        new PlayMp3().execute(song.getLink());
        rontation();
        UpdateSeekBar();
        imgPlay.setOnClickListener(view -> playMusic());
        imgPre.setOnClickListener(view -> playPreSong(songs));
        imgNext.setOnClickListener(view -> playNextSong(songs));
        favorite.setOnClickListener(view -> favoriteSong(song));
    }
    private void init(){
        imgDisc = findViewById(R.id.imgDisc);
        tvTotalTime = findViewById(R.id.tvTotalTime);
        seekBar = findViewById(R.id.seekBar);
        imgPre = findViewById(R.id.imgPre);
        imgPlay= findViewById(R.id.imgPlay);
        imgNext= findViewById(R.id.imgNext);
        tvTime = findViewById(R.id.tvTime);
        tvSongNamePlayer = findViewById(R.id.tvHeaderTitle);
        favorite = findViewById(R.id.favorite);

    }

    class PlayMp3 extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String song) {
            super.onPostExecute(song);
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(song);
                mediaPlayer.prepare();
            } catch (IOException e){
                e.printStackTrace();
            }
            mediaPlayer.start();
            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    seekBar.setProgress(0);
                    mediaPlayer.seekTo(0);
                    mediaPlayer.start();
                }
            });
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if(fromUser){
                        mediaPlayer.seekTo(progress);
//                        .setText(values[progress]);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                        tvTime.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    }
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });
            TimeSong();
        }
    }
    private void UpdateSeekBar()
    {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null){
                    int progress = mediaPlayer.getCurrentPosition();
                    seekBar.setProgress(progress);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    tvTime.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                }
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(runnable, 0);
    }

    private void TimeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        tvTotalTime.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
    }

    @Override
    protected void onStop(){
        super.onStop();
        mediaPlayer.pause();
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        mediaPlayer.pause();
        imgPlay.setImageResource(R.drawable.ic_play);
        objectAnimator.pause();
    }
}
