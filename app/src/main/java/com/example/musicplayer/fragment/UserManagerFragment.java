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
import com.example.musicplayer.EditSongActivity;
import com.example.musicplayer.EditUserActivity;
import com.example.musicplayer.R;
import com.example.musicplayer.UserFormActivity;
import com.example.musicplayer.adapter.SongManagerAdapter;
import com.example.musicplayer.adapter.UserAdapter;
import com.example.musicplayer.domain.OnItemClickListener;
import com.example.musicplayer.domain.Song;
import com.example.musicplayer.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserManagerFragment extends Fragment  {
    ImageButton btnAdd;
    List<User> userList;
    View view;
    int currentPosition;
    private RecyclerView mRecyclerView;
    private UserAdapter mUserAdapter;
    public UserManagerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_user_manager, container, false);

        init();

        return view;
    }

    private void setEvent() {
    }

    private void loadData(){

        mRecyclerView = view.findViewById(R.id.rcvUserListManager);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Create a list of songs
        userList = new ArrayList<User>();
//        userList.add(new User(1, "0354872144", "Hau", "Tran", "hautran@020132", "12345"));
//        userList.add(new User(2, "0354872144", "Hau", "Tran", "hautran@020132", "12345"));
//        userList.add(new User(3, "0354872144", "Hau", "Tran", "hautran@020132", "12345"));

        // Create and set the adapter for the RecyclerView
        mUserAdapter = new UserAdapter(userList);
        mRecyclerView.setAdapter(mUserAdapter);

        mUserAdapter.setOnItemClickListener(new OnItemClickListener() {
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
                User data = userList.get(currentPosition);
                Intent intent = new Intent(getActivity(), UserFormActivity.class);
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
