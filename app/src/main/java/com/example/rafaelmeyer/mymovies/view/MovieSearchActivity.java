package com.example.rafaelmeyer.mymovies.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rafaelmeyer.mymovies.R;
import com.example.rafaelmeyer.mymovies.api.MyOmdbApi;
import com.example.rafaelmeyer.mymovies.model.Movie;
import com.example.rafaelmeyer.mymovies.model.MovieDetails;
import com.example.rafaelmeyer.mymovies.model.Search;
import com.squareup.picasso.Picasso;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MovieSearchActivity extends AppCompatActivity {

    private static final String TAG = "MovieSearchActivity";
    private ImageView imageViewMovieCover;
    private TextView textViewMovieTitle;
    private TextView textViewYear;
    private TextView textViewType;
    private TextView textViewPlotSearchView;

    private MovieSearchActivity self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);

        imageViewMovieCover = (ImageView) findViewById(R.id.imageViewMovieCoverMovieSearch);
        textViewMovieTitle = (TextView) findViewById(R.id.textViewMovieTitleMovieSearch);
        textViewYear = (TextView) findViewById(R.id.textViewYearMovieSearch);
        textViewType = (TextView) findViewById(R.id.textViewTypeMovieSearch);
        textViewPlotSearchView = (TextView) findViewById(R.id.textViewPlotSearchView);

        Movie movie = getIntent().getParcelableExtra(SearchActivity.MOVIESEARCH);
        String imdbID = movie.getImdbID();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SearchActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final MyOmdbApi service = retrofit.create(MyOmdbApi.class);
        Call<MovieDetails> call = service.getMovieByID(imdbID);

        self = this;

        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Response<MovieDetails> response, Retrofit retrofit) {
                Log.d(TAG, "Status Code : " + response.code());

                MovieDetails movieDetails = response.body();
                String title  = movieDetails.getTitle();
                String poster = movieDetails.getPoster();
                String type   = movieDetails.getType();
                String year   = movieDetails.getYear();
                String plot   = movieDetails.getPlot();
                if (movieDetails.getPoster().equals("N/A")){
                    poster = "http://i.imgur.com/DvpvklR.png";
                }

                Picasso.with(self).load(poster).into(imageViewMovieCover);
                textViewMovieTitle.setText(title);

                textViewType.setText(type);
                textViewYear.setText(year);
                textViewPlotSearchView.setText(plot);

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }
}
