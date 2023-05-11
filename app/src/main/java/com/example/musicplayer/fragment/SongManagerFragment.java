package com.example.musicplayer.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.AddSongActivity;
import com.example.musicplayer.AdminActivity;
import com.example.musicplayer.EditSongActivity;
import com.example.musicplayer.PlayingActivity;
import com.example.musicplayer.R;
import com.example.musicplayer.adapter.SongListAdapter;
import com.example.musicplayer.adapter.SongManagerAdapter;
import com.example.musicplayer.domain.OnItemClickListener;
import com.example.musicplayer.domain.Song;

import java.util.ArrayList;
import java.util.List;

public class SongManagerFragment extends Fragment {
    ImageButton btnAdd;
    List<Song> songList;
    View view;
    int currentPosition;
    private RecyclerView mRecyclerView;
    private SongManagerAdapter mSongAdapter;
    public SongManagerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_song_manager, container, false);

        init();

        return view;
    }

    private void setEvent() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddSongActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadData(){

        mRecyclerView = view.findViewById(R.id.rcvSongListManager);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Create a list of songs
        songList = new ArrayList<>();
        songList.add(new Song("Song Title 1", "Song Artist 1", R.drawable.default_song));
        songList.add(new Song("Song Title 2", "Song Artist 2", R.drawable.default_song));
        songList.add(new Song("Song Title 3", "Song Artist 3", R.drawable.default_song));
        songList.add(new Song("Song Title 4", "Song Artist 4", R.drawable.default_song));
        songList.add(new Song("Song Title 5", "Song Artist 5", R.drawable.default_song));

        // Create and set the adapter for the RecyclerView
        mSongAdapter = new SongManagerAdapter(songList);
        mRecyclerView.setAdapter(mSongAdapter);

        mSongAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                Song data = songList.get(position);
//
//                Intent intent = new Intent(getActivity(), PlayingActivity.class);
//                intent.putExtra("data", data.getId());
//                startActivity(intent);
                currentPosition = position;
                showDialog();
            }
        });
    }

    private void init() {
        btnAdd = view.findViewById(R.id.btnForwardAddSong);
        System.out.println(btnAdd);
        setEvent();
        loadData();
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet);

        LinearLayout deteteLayout = dialog.findViewById(R.id.layout_delete);
        LinearLayout editLayout = dialog.findViewById(R.id.layout_edit);

        deteteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "click Delete", Toast.LENGTH_SHORT).show();
            }
        });

        editLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Song data = songList.get(currentPosition);
                Intent intent = new Intent(getActivity(), EditSongActivity.class);
                intent.putExtra("data", "1");
                Toast.makeText(getActivity(), "click Edit", Toast.LENGTH_SHORT).show();

                startActivity(intent);
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialoAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
}
