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
import com.example.musicplayer.domain.OnItemClickListener;
import com.example.musicplayer.domain.Song;

import java.util.ArrayList;
import java.util.List;

public class SongListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private SongListAdapter mSongAdapter;

    private  int categoryId;

    public SongListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_song_list, container, false);

//        Get categoryId
        Bundle bundle = getArguments();
        if(bundle != null){
            categoryId = bundle.getInt("category");
        }
        System.out.println(categoryId);

        // Initialize the RecyclerView
        mRecyclerView = rootView.findViewById(R.id.rcvSongList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Create a list of songs
        List<Song> songList = new ArrayList<>();
        songList.add(new Song("Song Title 1", "Song Artist 1", R.drawable.default_song));
        songList.add(new Song("Song Title 2", "Song Artist 2", R.drawable.default_song));
        songList.add(new Song("Song Title 3", "Song Artist 3", R.drawable.default_song));
        songList.add(new Song("Song Title 4", "Song Artist 4", R.drawable.default_song));
        songList.add(new Song("Song Title 5", "Song Artist 5", R.drawable.default_song));


        // Create and set the adapter for the RecyclerView
        mSongAdapter = new SongListAdapter(songList);
        mRecyclerView.setAdapter(mSongAdapter);

        mSongAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Song data = songList.get(position);

/*                *//*If layout_playing is fragment*//*

                // Start a new fragment transaction
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                // Replace the current fragment with a new fragment
                Fragment newFragment = new SongListFragment();
                Bundle args = new Bundle();
                args.putSerializable("data", data.getId());
                newFragment.setArguments(args);

                transaction.replace(R.id.container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();*/

//                If layout_playing is activity

                Intent intent = new Intent(getActivity(), PlayerActivity.class);
                intent.putExtra("data", data.getId());
                startActivity(intent);
            }
        });
        return rootView;
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
}
