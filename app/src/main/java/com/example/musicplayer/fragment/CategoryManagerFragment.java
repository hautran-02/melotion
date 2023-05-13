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

import com.example.musicplayer.CategoryFormActivity;
import com.example.musicplayer.R;
import com.example.musicplayer.adapter.CategoryAdapter;
import com.example.musicplayer.domain.Category;
import com.example.musicplayer.domain.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryManagerFragment extends Fragment {
    ImageButton btnAdd;
    List<Category> categoryList;
    View view;
    int currentPosition;
    private RecyclerView mRecyclerView;
    private CategoryAdapter mAdapter;
    public CategoryManagerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_category_manager, container, false);

        init();

        return view;
    }

    private void setEvent() {
    }

    private void loadData(){

        mRecyclerView = view.findViewById(R.id.rcvCategoryManager);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Create a list of songs
        categoryList = new ArrayList<Category>();
        categoryList.add(new Category(1, "Rap", "https://media.vov.vn/sites/default/files/styles/large/public/2022-01/top_8_rv_mua_2_0.jpg", "Danh sách 100 bài hát Rap Việt hot nhất được NhacCuaTui cập nhật liên tục dựa theo lượt nghe, yêu thích trên nhiều nền tảng."));
        categoryList.add(new Category(2, "Classic", "https://media.vov.vn/sites/default/files/styles/large/public/2022-01/top_8_rv_mua_2_0.jpg", "Danh sách 100 bài hát Rap Việt hot nhất được NhacCuaTui cập nhật liên tục dựa theo lượt nghe, yêu thích trên nhiều nền tảng."));
        categoryList.add(new Category(3, "Morden", "https://media.vov.vn/sites/default/files/styles/large/public/2022-01/top_8_rv_mua_2_0.jpg", "Danh sách 100 bài hát Rap Việt hot nhất được NhacCuaTui cập nhật liên tục dựa theo lượt nghe, yêu thích trên nhiều nền tảng."));

        // Create and set the adapter for the RecyclerView
        mAdapter = new CategoryAdapter(categoryList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                User data = userList.get(position);
//
//                Intent intent = new Intent(getActivity(), EditUserActivity.class);
//                intent.putExtra("data", data.getId());
//                startActivity(intent);
                showDialog();
            }
        });
    }

    private void init() {
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
                Category data = categoryList.get(currentPosition);
                Intent intent = new Intent(getActivity(), CategoryFormActivity.class);
                intent.putExtra("data", data);
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
