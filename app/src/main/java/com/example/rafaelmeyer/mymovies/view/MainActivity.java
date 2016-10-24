package com.example.rafaelmeyer.mymovies.view;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.rafaelmeyer.mymovies.R;
import com.example.rafaelmeyer.mymovies.adapter.MyAdapterMain;
import com.example.rafaelmeyer.mymovies.model.Movie;
import com.example.rafaelmeyer.mymovies.model.MovieRealm;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements MyAdapterMain.OpenClickListener, MyAdapterMain.ClickToRemoveFromFavoriteListener {

    private static final String TAG = "MainActivity";
    private static final String KEY = "key";

    private List<Movie> movies = new ArrayList<>();
    private RealmResults<MovieRealm> results;
    private Realm myRealm;

    private RecyclerView.LayoutManager myLayoutManager;
    private RecyclerView myRecyclerView;
    private MyAdapterMain myAdapter;

    private GridLayoutManager myGridLayoutManager;

    private FloatingActionButton myFloatingActionButton;

    private TextView textViewNoResult;

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        loadRealmObjectToMemory();
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
//        myLayoutManager = new LinearLayoutManager(this);
//        myRecyclerView.setLayoutManager(myLayoutManager);
        myGridLayoutManager = new GridLayoutManager(this, 2);
        myRecyclerView.setLayoutManager(myGridLayoutManager);

        myAdapter = new MyAdapterMain(movies);
        myRecyclerView.setAdapter(myAdapter);
        myAdapter.setMyOpenClickListener(this);
        myAdapter.setMyClickToRemoveFromFavoriteListener(this);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewNoResult = (TextView) findViewById(R.id.textViewNoResult);

        myFloatingActionButton = (FloatingActionButton) findViewById(R.id.floatActionButton);

        myFloatingActionButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                        startActivity(intent);
                    }
                }
        );

        myRealm = Realm.getInstance(this);
        results = myRealm.where(MovieRealm.class).findAll();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    private void loadRealmObjectToMemory() {
        movies.clear();
        myRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewMain);

        for (int i=0; i < results.size(); i++) {
            String title  = results.get(i).getTitle();
            String year   = results.get(i).getYear();
            String type   = results.get(i).getType();
            String poster = results.get(i).getPoster();
            String imdbID = results.get(i).getImdbID();
            Movie movieModel = new Movie(title, year, imdbID, type, poster);
            movies.add(movieModel);
        }

        if (movies.size() != 0) {
            textViewNoResult.setVisibility(View.INVISIBLE);
        } else {
            textViewNoResult.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClickListener(View view, MovieRealm model) {
        Log.d(TAG, "click to item");

        Intent intent = new Intent(MainActivity.this, MovieActivity.class);

        String title  = model.getTitle();
        String year   = model.getYear();
        String type   = model.getType();
        String poster = model.getPoster();
        String imdbID = model.getImdbID();
        Movie movieModel = new Movie(title, year, imdbID, type, poster);

        String transition = getString(R.string.transition_string);
        View viewStart = view.findViewById(R.id.imageViewMovieCoverMain);

        ActivityOptionsCompat optionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, viewStart, transition);

        intent.putExtra("object", movieModel);
        ActivityCompat.startActivity(this, intent, optionsCompat.toBundle());
    }

    @Override
    public void onClickToRemoveFromFavoriteListener(View view, MovieRealm model, Movie movie) {
        Log.d(TAG, "Click to Remove");
        try {
            myRealm.beginTransaction();
            model.removeFromRealm();
            myRealm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
        movies.remove(movie);
        myAdapter.notifyDataSetChanged();
    }

}
