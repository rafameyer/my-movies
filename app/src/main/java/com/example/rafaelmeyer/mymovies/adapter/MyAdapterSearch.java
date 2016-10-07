package com.example.rafaelmeyer.mymovies.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rafaelmeyer.mymovies.R;
import com.example.rafaelmeyer.mymovies.model.Movie;
import com.example.rafaelmeyer.mymovies.model.MovieRealm;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.realm.Realm;

/**
 * Created by rafael.meyer on 10/3/16.
 */
public class MyAdapterSearch extends RecyclerView.Adapter<MyAdapterSearch.ViewHolder>{
    private List<Movie> movies;
    public AddClickListener myAddClickListener;
    public ClickToViewMovieListener myClickToViewMovieListener;

    public MyAdapterSearch(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(holder.itemView.getContext()).load(movies.get(position).getPoster()).into(holder.imageViewMovieCoverSearch);
        holder.textViewMovieTitleSearch.setText(movies.get(position).getTitle());
        holder.textViewYearSearch.setText(movies.get(position).getYear());
        holder.textViewTypeSearch.setText(movies.get(position).getType());
        String imdbID = movies.get(position).getImdbID();
        Realm myRealm = Realm.getInstance(holder.itemView.getContext());
        MovieRealm movieRealmModel = myRealm.where(MovieRealm.class).equalTo("imdbID", imdbID).findFirst();
        if (movieRealmModel != null) {
            holder.buttonAddFavorite.setImageResource(R.drawable.ic_star_black_24dp);
        } else {
            holder.buttonAddFavorite.setImageResource(R.drawable.ic_star_border_black_24dp);
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageButton buttonAddFavorite;
        ImageView imageViewMovieCoverSearch;
        TextView textViewMovieTitleSearch;
        TextView textViewYearSearch;
        TextView textViewTypeSearch;

        public ViewHolder(View itemView) {
            super(itemView);
            buttonAddFavorite = (ImageButton) itemView.findViewById(R.id.buttonAddFavorite);
            imageViewMovieCoverSearch = (ImageView) itemView.findViewById(R.id.imageViewMovieCoverSearch);
            textViewMovieTitleSearch = (TextView) itemView.findViewById(R.id.textViewMovieTitleSearch);
            textViewYearSearch = (TextView) itemView.findViewById(R.id.textViewYearSearch);
            textViewTypeSearch  = (TextView) itemView.findViewById(R.id.textViewTypeSearch);
            buttonAddFavorite.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == buttonAddFavorite) {
                if (myAddClickListener != null) {
                    Movie movie = movies.get(getAdapterPosition());
                    myAddClickListener.onClickListener(v, movie);
                }
            }
            if (v == itemView) {
                if (myClickToViewMovieListener != null) {
                    Movie movie = movies.get(getAdapterPosition());
                    myClickToViewMovieListener.onClickToViewMovieListener(v, movie);
                }
            }
        }

    }

    public interface AddClickListener {
        void onClickListener(View view, Movie movie);
    }

    public interface ClickToViewMovieListener {
        void onClickToViewMovieListener(View view, Movie model);
    }

    public void setMyAddClickListener(AddClickListener myAddClickListener) {
        this.myAddClickListener = myAddClickListener;
    }

    public void setMyClickToViewMovieListener(ClickToViewMovieListener myClickToViewMovieListener) {
        this.myClickToViewMovieListener = myClickToViewMovieListener;
    }
}
