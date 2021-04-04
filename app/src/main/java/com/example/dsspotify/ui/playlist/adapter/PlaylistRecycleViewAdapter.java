package com.example.dsspotify.ui.playlist.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dsspotify.R;

public class PlaylistRecycleViewAdapter extends  RecyclerView.Adapter<PlaylistRecycleViewAdapter.ItemViewHolder> {


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView titre;
        protected TextView auteur;

        public ItemViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.imgView_item);
            this.titre = (TextView) view.findViewById(R.id.txtview_titre);
            this.auteur = (TextView) view.findViewById(R.id.txtview_auteur);
        }
    }
}
