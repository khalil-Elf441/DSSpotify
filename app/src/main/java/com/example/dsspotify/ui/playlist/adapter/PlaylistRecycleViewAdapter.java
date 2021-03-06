package com.example.dsspotify.ui.playlist.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dsspotify.MainActivity;
import com.example.dsspotify.R;
import com.example.dsspotify.ui.playlist.objects.Song;

import java.util.List;

public class PlaylistRecycleViewAdapter extends  RecyclerView.Adapter<PlaylistRecycleViewAdapter.ItemViewHolder> {

    private List<Song> SongList;
    private Context mContext;


    public PlaylistRecycleViewAdapter(List<Song> songList, Context mContext) {
        SongList = songList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song_playlist, parent,false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        final Song song = SongList.get(position);
        holder.titre.setText(song.getTitle());
        holder.artist.setText(song.getArtist());

        holder.imgview_play_trigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                MainActivity.mediaPlayer = MediaPlayer.create(mContext,song.getRessourceId());
                MainActivity.txtview_titre.setText(song.getTitle());
                MainActivity.txtview_artist.setText("by "+song.getArtist());
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != SongList ? SongList.size() : 0);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView titre;
        protected TextView artist;
        protected ImageView imgview_play_trigger;

        public ItemViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.imgView_item);
            this.titre = (TextView) view.findViewById(R.id.txtview_titre);
            this.artist = (TextView) view.findViewById(R.id.txtview_artist);
            this.imgview_play_trigger = (ImageView) view.findViewById(R.id.imgview_item_song_play_trigger);
        }
    }
}
