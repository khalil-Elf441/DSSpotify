package com.example.dsspotify.ui.playlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dsspotify.R;

public class PlaylistFragment extends Fragment {

    private PlaylistViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(PlaylistViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView =  root.findViewById(R.id.txtview_auteur);
        final RecyclerView rcyclview_song_playlist = root.findViewById(R.id.rcyclview_song_playlist);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        //rcyclview_song_playlist



        return root;
    }
}