package com.example.rafaelmeyer.mymovies.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rafaelmeyer.mymovies.R;
import com.example.rafaelmeyer.mymovies.adapter.MyAdapterSearch;
import com.example.rafaelmeyer.mymovies.api.MyOmdbApi;
import com.example.rafaelmeyer.mymovies.model.Movie;
import com.example.rafaelmeyer.mymovies.model.MovieRealm;
import com.example.rafaelmeyer.mymovies.model.Search;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class SearchActivity extends AppCompatActivity implements MyAdapterSearch.AddClickListener, MyAdapterSearch.ClickToViewMovieListener {

    private static final String TAG = "MainActivity";
    private static final String BASE_URL = "http://omdbapi.com/";

    private List<Movie> movies;
    private RecyclerView myRecyclerView;
    private MyAdapterSearch myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;

    private String buttonSearchResult;
    private ImageButton myButtonSearch;
    private EditText myEditSearch;
    private ProgressBar myProgressBar;
    private TextView textMovidAddListSearch;

    private SearchActivity self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        myProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        textMovidAddListSearch = (TextView) findViewById(R.id.textMovidAddListSearch);

        myButtonSearch = (ImageButton) findViewById(R.id.imageButtonSearch);
        myEditSearch = (EditText) findViewById(R.id.editViewSearch);

        myRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewSearch);

        myLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(myLayoutManager);

        myButtonSearch.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadSearchButton();
                        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(myEditSearch.getWindowToken(), InputMethodManager.SHOW_IMPLICIT);
                        loadRetrofit();
                    }
                }
        );

    }

    private void loadSearchButton() {
        buttonSearchResult = myEditSearch.getText().toString();
        Log.d(TAG, buttonSearchResult);
    }

    private void loadRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final MyOmdbApi service = retrofit.create(MyOmdbApi.class);
        Call<Search> call = service.getListSearchCall(buttonSearchResult);

        self = this;

        call.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Response<Search> response, Retrofit retrofit) {
                Log.d(TAG, "Status Code : " + response.code());

                movies = new ArrayList<>();

                List<Movie> movieList = response.body().getSearch();
                if (movieList == null || buttonSearchResult.length() == 1) {
                    Toast.makeText(getApplicationContext(), "not enough for search", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i=0; i < movieList.size(); i++) {
                        Log.d(TAG, movieList.get(i).getTitle());
                        if (movieList.get(i).getPoster().equals("N/A")){
                            String poster = "http://i.imgur.com/DvpvklR.png";
                            movieList.get(i).setPoster(poster);
                        }
                        movies.add(movieList.get(i));
                    }
                }


                myAdapter = new MyAdapterSearch(movies);
                myAdapter.notifyDataSetChanged();
                myRecyclerView.setAdapter(myAdapter);
                myAdapter.setMyAddClickListener(self);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
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
                startActivity(new Intent(this, MainActivity.class));
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickListener(View view, int position) {

/*        String title = movies.get(position).getTitle();
        String type = movies.get(position).getType();
        String year = movies.get(position).getYear();
        String poster = movies.get(position).getPoster();
        String imdbID = movies.get(position).getImdbID();

        Movie movie = new Movie(title, year, type, type, poster);

        Intent intent = new Intent(SearchActivity.this, MovieActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);*/

        Log.d(TAG, "item movie was click");
        Realm myRealm = Realm.getInstance(getApplicationContext());
        RealmList<MovieRealm> movieRealms = new RealmList<>();

        String title = movies.get(position).getTitle();
        String type = movies.get(position).getType();
        String year = movies.get(position).getYear();
        String poster = movies.get(position).getPoster();
        String imdbID = movies.get(position).getImdbID();
        if (poster.equals("N/A")){
            poster = "http://i.imgur.com/DvpvklR.png";
        }
        myRealm.beginTransaction();
        MovieRealm movieRealm = myRealm.createObject(MovieRealm.class);
        movieRealm.setTitle(title);
        movieRealm.setType(type);
        movieRealm.setYear(year);
        movieRealm.setPoster(poster);
        movieRealm.setImdbID(imdbID);
        //movieRealm.setSalve(true);
        myRealm.commitTransaction();

        movieRealms.add(movieRealm);
    }

    @Override
    public void onClickToViewMovieListener(View view, int position) {

    }
}
