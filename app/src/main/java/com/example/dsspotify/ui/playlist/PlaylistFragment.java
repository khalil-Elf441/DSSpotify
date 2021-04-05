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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dsspotify.R;
import com.example.dsspotify.ui.playlist.adapter.PlaylistRecycleViewAdapter;
import com.example.dsspotify.ui.playlist.objects.Song;

import java.util.ArrayList;
import java.util.List;

public class PlaylistFragment extends Fragment {

    private PlaylistViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
      //  dashboardViewModel = new ViewModelProvider(this).get(PlaylistViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
       // final TextView textView =  root.findViewById(R.id.text_dashboard);
/*
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
*/
        //
        List<Song> SongList = new ArrayList<>();
        SongList.add(new Song("Titsepoken 2015","Lensko"));
        SongList.add(new Song("hole2","khalil"));
        SongList.add(new Song("hole3","khalil"));
        SongList.add(new Song("hole4","khalil"));
        SongList.add(new Song("hole5","khalil"));
        SongList.add(new Song("hole6","khalil"));
        SongList.add(new Song("hole1","khalil"));
        SongList.add(new Song("hole2","khalil"));
        SongList.add(new Song("hole3","khalil"));
        SongList.add(new Song("hole4","khalil"));
        SongList.add(new Song("hole5","khalil"));
        SongList.add(new Song("hole6","khalil"));


        //
        final RecyclerView recycleview_song_playlist = root.findViewById(R.id.rcyclview_song_playlist);
        recycleview_song_playlist.setLayoutManager(new LinearLayoutManager(getContext()));
        PlaylistRecycleViewAdapter adapter = new PlaylistRecycleViewAdapter(SongList,getActivity());

        //
        recycleview_song_playlist.setAdapter(adapter);
        adapter.notifyDataSetChanged();




        return root;
    }
}