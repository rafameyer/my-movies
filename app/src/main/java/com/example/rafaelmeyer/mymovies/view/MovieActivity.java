package com.example.rafaelmeyer.mymovies.view;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rafaelmeyer.mymovies.R;
import com.example.rafaelmeyer.mymovies.model.Movie;
import com.example.rafaelmeyer.mymovies.model.MovieRealm;
import com.squareup.picasso.Picasso;

import io.realm.Realm;

public class MovieActivity extends AppCompatActivity {

    private ImageView imageViewMovieCover;
    private TextView textViewMovieTitle;
    private TextView textViewYear;
    private TextView textViewType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        imageViewMovieCover = (ImageView) findViewById(R.id.imageViewMovieCover);
        textViewMovieTitle = (TextView) findViewById(R.id.textViewMovieTitle);
        textViewYear = (TextView) findViewById(R.id.textViewYear);
        textViewType = (TextView) findViewById(R.id.textViewType);

        Movie model = getIntent().getParcelableExtra("object");

        String imdbID = model.getImdbID();

        Realm myRealm = Realm.getInstance(this);
        MovieRealm movieRealmModel = myRealm.where(MovieRealm.class).equalTo("imdbID", imdbID).findFirst();

        String title  = movieRealmModel.getTitle();
        String poster = movieRealmModel.getPoster();
        String type   = movieRealmModel.getType();
        String year   = movieRealmModel.getYear();

        Picasso.with(getApplicationContext()).load(poster).into(imageViewMovieCover);
        textViewMovieTitle.setText(title);
        textViewType.setText(type);
        textViewYear.setText(year);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
