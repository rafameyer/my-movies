package com.example.rafaelmeyer.mymovies.view;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rafaelmeyer.mymovies.R;
import com.example.rafaelmeyer.mymovies.adapter.MyAdapterMain;
import com.example.rafaelmeyer.mymovies.model.Movie;
import com.example.rafaelmeyer.mymovies.model.MovieRealm;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements MyAdapterMain.openClickListener, MyAdapterMain.ClickToRemoveFromFavoriteListener {

    private static final String TAG = "MainActivity";
    private List<Movie> movies = new ArrayList<>();
    private RealmResults<MovieRealm> results;
    private Realm myRealm;
    private RecyclerView myRecyclerView;
    private FloatingActionButton myFloatingActionButton;
    private MyAdapterMain myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;

    private TextView textViewNoResult;
    int location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewNoResult = (TextView) findViewById(R.id.textViewNoResult);

        myRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewMain);
        myFloatingActionButton = (FloatingActionButton) findViewById(R.id.floatActionButton);

        myFloatingActionButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );

        loadRealmObjectToMemory();

        myLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(myLayoutManager);
        myAdapter = new MyAdapterMain(movies);
        myRecyclerView.setAdapter(myAdapter);
        myAdapter.setMyOpenClickListener(this);
        myAdapter.setMyClickToRemoveFromFavoriteListener(this);
        myAdapter.notifyDataSetChanged();

    }

    private void loadRealmObjectToMemory() {
        myRealm = Realm.getInstance(this);
        results = myRealm.where(MovieRealm.class).findAll();

        if (results.size() == 0) {
            textViewNoResult.setVisibility(View.VISIBLE);
        }
        for (int i=0; i < results.size(); i++) {
            String title = results.get(i).getTitle();
            String year = results.get(i).getYear();
            String type = results.get(i).getType();
            String poster = results.get(i).getPoster();
            if (poster.equals("N/A")){
                poster = "http://i.imgur.com/DvpvklR.png";
            }

            Movie movieModel = new Movie(title, year, type, poster);
            movies.add(movieModel);
        }
    }

    @Override
    public void onClickListener(View view, int position) {
        Log.d(TAG, "click to item");

        Intent intent = new Intent(MainActivity.this, MovieActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public void onClickToRemoveFromFavoriteListener(View view, int position) {
        Log.d(TAG, "Click to Remove");
        myAdapter.notifyDataSetChanged();

        try {
            myRealm.beginTransaction();
            RealmQuery query = myRealm.where(MovieRealm.class);
            RealmResults results = query.findAll();
            results.get(position).removeFromRealm();
            myRealm.commitTransaction();
            if (results.size() == 0) {
                textViewNoResult.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.imageButtonClickRemoveFromFavorite);
        imageButton.setImageResource(R.drawable.ic_star_border_black_24dp);

        movies.remove(position);

        Handler myHandler = new Handler();
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                myAdapter.notifyDataSetChanged();
            }
        }, 500);

    }
}
