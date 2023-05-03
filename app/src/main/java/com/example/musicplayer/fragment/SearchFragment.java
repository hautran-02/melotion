package com.example.musicplayer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.musicplayer.R;

public class SearchFragment extends Fragment {
    EditText edSearch;
    Button btnSearch;
    View view;
    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_search, container, false);

        init();
        setEvent();

        return view;
    }

    private void setEvent(){
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager(); // if using support library
                SearchingFragment searchingFragment = new SearchingFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, searchingFragment);
                fragmentTransaction.commit();
            }
        });
    }

    private void init(){
        btnSearch = view.findViewById(R.id.btnSearch);
    }
}
