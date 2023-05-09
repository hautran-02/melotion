package com.example.musicplayer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.musicplayer.AddSongActivity;
import com.example.musicplayer.R;

public class SongManagerFragment extends Fragment {
    ImageButton btnAdd;
    public SongManagerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_song_manager, container, false);

        init();
        setEvent();

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

    private void init() {
        btnAdd = getActivity().findViewById(R.id.btnAddSong);
    }
}
