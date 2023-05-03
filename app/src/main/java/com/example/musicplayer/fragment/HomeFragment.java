package com.example.musicplayer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;
import com.example.musicplayer.adapter.CategoryAdapter;
import com.example.musicplayer.domain.Category;
import com.example.musicplayer.domain.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView categoryList;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_home, container, false);

        categoryList = view.findViewById(R.id.categoryRecyclerView);
        categoryList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        List<Category> categories = new ArrayList<>();
        categories.add(new Category("https://i0.wp.com/drjennybrockis.com/wp-content/uploads/2017/07/1afa3-img.jpg?w=1080&ssl=1", "Category 1", ""));
        categories.add(new Category("https://i0.wp.com/drjennybrockis.com/wp-content/uploads/2017/07/1afa3-img.jpg?w=1080&ssl=1", "Category 2", ""));
        categories.add(new Category("https://i0.wp.com/drjennybrockis.com/wp-content/uploads/2017/07/1afa3-img.jpg?w=1080&ssl=1", "Category 3", ""));

        CategoryAdapter adapter = new CategoryAdapter(categories);
        categoryList.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Get the clicked item's data
                Category data = categories.get(position);

                // Start a new fragment transaction
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                // Replace the current fragment with a new fragment
                Fragment newFragment = new SongListFragment();
                Bundle args = new Bundle();
                args.putSerializable("category", data.getId());
                newFragment.setArguments(args);

                transaction.replace(R.id.container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view ;
    }
}
