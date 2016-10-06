package com.example.rafaelmeyer.mymovies.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rafaelmeyer.mymovies.R;
import com.example.rafaelmeyer.mymovies.model.MovieRealm;
import com.squareup.picasso.Picasso;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MovieActivity extends AppCompatActivity {

    private ImageView imageViewMovieCover;
    private TextView textViewMovieTitle;
    private TextView textViewYear;
    private TextView textViewType;

    Realm myRealm;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        imageViewMovieCover = (ImageView) findViewById(R.id.imageViewMovieCover);
        textViewMovieTitle = (TextView) findViewById(R.id.textViewMovieTitle);
        textViewYear = (TextView) findViewById(R.id.textViewYear);
        textViewType = (TextView) findViewById(R.id.textViewType);

        position = getIntent().getExtras().getInt("position");

        myRealm = Realm.getInstance(this);
        RealmQuery query = myRealm.where(MovieRealm.class);
        RealmResults<MovieRealm> results = query.findAll();

        String title  = results.get(position).getTitle();
        String poster = results.get(position).getPoster();
        String imdbID = results.get(position).getImdbID();
        String type   = results.get(position).getType();
        String year   = results.get(position).getYear();

        Picasso.with(getApplicationContext()).load(poster).into(imageViewMovieCover);
        textViewMovieTitle.setText(title);
        textViewType.setText(type);
        textViewYear.setText(year);


    }
}
