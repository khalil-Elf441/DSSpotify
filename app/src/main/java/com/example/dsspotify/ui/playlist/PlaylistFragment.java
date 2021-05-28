package com.example.dsspotify.ui.playlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dsspotify.MainActivity;
import com.example.dsspotify.R;
import com.example.dsspotify.ui.playlist.adapter.PlaylistRecycleViewAdapter;
import com.example.dsspotify.ui.playlist.objects.Song;

import java.util.ArrayList;
import java.util.List;



/*
 *Ce fragment presente les différentes musiques proposé par le serveur de streaming
 *
 * @author khalil-Elf441
 *
 */
public class PlaylistFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_playlist, container, false);

        //
        List<Song> SongList = new ArrayList<>();
        //example des musiques
       //SongList.add(new Song("SkyRock","Radio",R.raw.lensko_titsepoken_2015));
        //SongList.add(new Song("memo","Maroon-5",R.raw.lensko_titsepoken_2015));
        //SongList.add(new Song("OneRepublic","OneRepublic",R.raw.lensko_titsepoken_2015));
        //SongList.add(new Song("music","Souf",R.raw.lensko_titsepoken_2015));

        //



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