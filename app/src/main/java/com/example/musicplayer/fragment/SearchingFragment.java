package com.example.musicplayer.fragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.musicplayer.R;

public class SearchingFragment extends Fragment {
    View view;
    EditText edSearching;
    ImageButton ibBack;
    TextView tv;
    FragmentManager fragmentManager;
    public SearchingFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_searching, container, false);

        init();
        setEvent();

        return view;
    }

    private void init(){
        edSearching = view.findViewById(R.id.edSearching);
        ibBack = view.findViewById(R.id.ibBackSearching);
        tv = view.findViewById(R.id.tvFirstSong);
    }

    private void setEvent(){
        //to opacity for hint of editext (edSearching) when selected
        ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.appbar_text));
        edSearching.setHintTextColor(colorStateList);
        edSearching.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edSearching.setHintTextColor(getResources().getColor(R.color.transparent));
                } else {
                    edSearching.setHintTextColor(colorStateList);
                }
            }
        });

        //back to previous page
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
}
