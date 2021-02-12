package com.example.vocabularybattle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vocabularybattle.R;
import com.example.vocabularybattle.model.WordSource;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LearnRecyclerViewAdapter extends RecyclerView.Adapter<LearnRecyclerViewAdapter.ViewHolder> {
    private List<WordSource> wordSources;
    private Context context;

    public LearnRecyclerViewAdapter(ArrayList<WordSource> wordSources, Context context) {
        this.wordSources = wordSources;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(wordSources.get(position).getSourceName());
        holder.play.setText("Play");
        Picasso.with(context).load(wordSources.get(position).getImageUrl()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return wordSources.size();
    }

    public void setNotes(List<WordSource> wordSources) {
        this.wordSources = wordSources;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView title;
        private MaterialButton play;
        private AppCompatImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            play = itemView.findViewById(R.id.playButton);
            imageView = itemView.findViewById(R.id.wordSourceImage);
        }
    }
}
