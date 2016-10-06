package com.example.rafaelmeyer.mymovies.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rafaelmeyer.mymovies.R;
import com.example.rafaelmeyer.mymovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rafael.meyer on 10/3/16.
 */
public class MyAdapterMain extends RecyclerView.Adapter<MyAdapterMain.ViewHolder> {

    private List<Movie> movies;
    public openClickListener mOpenClickListener;
    public ClickToRemoveFromFavoriteListener myClickToRemoveFromFavoriteListener;
    public MyAdapterMain(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(holder.itemView.getContext()).load(movies.get(position).getPoster()).into(holder.imageViewMovieCoverMain);
        holder.textViewMovieTitleMain.setText(movies.get(position).getTitle());
        holder.textViewYearMain.setText(movies.get(position).getYear());
        holder.textViewTypeMain.setText(movies.get(position).getType());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageButton imageButtonRemoveFromFavorite;
        ImageView imageViewMovieCoverMain;
        TextView textViewMovieTitleMain;
        TextView textViewYearMain;
        TextView textViewTypeMain;

        public ViewHolder(View itemView) {
            super(itemView);
            imageButtonRemoveFromFavorite = (ImageButton) itemView.findViewById(R.id.imageButtonClickRemoveFromFavorite);
            imageViewMovieCoverMain = (ImageView) itemView.findViewById(R.id.imageViewMovieCoverMain);
            textViewMovieTitleMain = (TextView) itemView.findViewById(R.id.textViewMovieTitleMain);
            textViewYearMain = (TextView) itemView.findViewById(R.id.textViewYearMain);
            textViewTypeMain  = (TextView) itemView.findViewById(R.id.textViewTypeMain);
            imageButtonRemoveFromFavorite.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == imageButtonRemoveFromFavorite) {
                if (myClickToRemoveFromFavoriteListener != null) {
                    myClickToRemoveFromFavoriteListener.onClickToRemoveFromFavoriteListener(v, getAdapterPosition());
                }
            }
            if (v == itemView) {
                if (mOpenClickListener != null) {
                    mOpenClickListener.onClickListener(v, getAdapterPosition());
                }
            }
        }

    }

    public interface openClickListener {
        void onClickListener(View view, int position);
    }

    public interface ClickToRemoveFromFavoriteListener {
        void onClickToRemoveFromFavoriteListener(View view, int position);
    }

    public void setMyOpenClickListener(openClickListener mOpenClickListener) {
        this.mOpenClickListener = mOpenClickListener;
    }

    public void setMyClickToRemoveFromFavoriteListener(ClickToRemoveFromFavoriteListener myClickToRemoveFromFavoriteListener) {
        this.myClickToRemoveFromFavoriteListener = myClickToRemoveFromFavoriteListener;
    }

}