package com.example.musicplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.musicplayer.R;
import com.example.musicplayer.domain.Song;

import java.util.List;

public class SongAdapter extends ArrayAdapter<Song> {
    private Context mContext;
    private int mResource;

    public SongAdapter(Context context, int resource, List<Song> songs) {
        super(context, resource, songs);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(mResource, parent, false);
        }

        Song song = getItem(position);

        ImageView albumArt = view.findViewById(R.id.imgSong);
        albumArt.setImageResource(song.getImage());

        TextView songTitle = view.findViewById(R.id.tvSongName);
        songTitle.setText(song.getName());

        TextView artistName = view.findViewById(R.id.tvArtist);
        artistName.setText(song.getArtist());

        return view;
    }
}
