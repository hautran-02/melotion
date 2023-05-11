package com.example.musicplayer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.MainActivity;
import com.example.musicplayer.PlayerActivity;
import com.example.musicplayer.PlayingActivity;
import com.example.musicplayer.R;
import com.example.musicplayer.adapter.SongAdapter;
import com.example.musicplayer.adapter.SongListAdapter;
import com.example.musicplayer.api.CategoryApi;
import com.example.musicplayer.api.SongApi;
import com.example.musicplayer.domain.Category;
import com.example.musicplayer.domain.OnItemClickListener;
import com.example.musicplayer.domain.Song;
import com.example.musicplayer.domain.SongMessage;
import com.example.musicplayer.retrofit.RetrofitClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private SongListAdapter mSongAdapter;

    private SongApi songApi;

    private  Long categoryId;

    public SongListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View View = inflater.inflate(R.layout.fragment_song_list, container, false);

//        Get categoryId
        Bundle bundle = getArguments();
        if(bundle != null){
            categoryId = bundle.getLong("category");
        }
        System.out.println(categoryId);

        // Initialize the RecyclerView
        mRecyclerView = View.findViewById(R.id.rcvSongList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        GetSong();
        return View;
    }

    // SongFragment.java
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the arguments passed from the CategoryFragment
        Bundle bundle = getArguments();
        if (bundle != null) {
            int itemId = bundle.getInt("categoryItem");
            // Use the itemId to get the data you want
            // ...
        }
    }

    private void GetSong(){
        songApi = RetrofitClient.getInstance().getRetrofit().create(SongApi.class);

        songApi.SongCategory(categoryId).enqueue(new Callback<SongMessage>() {
            @Override
            public void onResponse(Call<SongMessage> call, Response<SongMessage> response) {
                List<Song> songs;
                SongMessage songMessage = response.body();
                songs = songMessage.getSongs();
                List<Song> songList = songs;
                mSongAdapter = new SongListAdapter(songs);
                mRecyclerView.setAdapter(mSongAdapter);
                mRecyclerView.setHasFixedSize(true);
                mSongAdapter.notifyDataSetChanged();
                if(songs!=null && !songs.isEmpty()) {
                    mSongAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Song data = songs.get(position);
                            Intent intent = new Intent(getActivity(), PlayerActivity.class);
                            intent.putExtra("position",position);
                            intent.putExtra("songs", (Serializable) songs);
                            System.out.println("-----------------");
                            System.out.println(data);
                            //intent.putExtra("songList", new ArrayList<>(songList));
                            startActivity(intent);
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<SongMessage> call, Throwable t) {
                System.out.println(t);
            }
        });
    }
}
