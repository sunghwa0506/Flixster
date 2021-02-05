package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.model.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;
import org.w3c.dom.Text;

import okhttp3.Headers;

public class detailActivity extends YouTubeBaseActivity {
    private static final String YOUTUBE_API_KEy = "AIzaSyB1KuT62nlR-zupOXOEH8Ixkw70rdgQapA";
    public  static final String VIDEOS_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    YouTubePlayerView youTubePlayerView;
    TextView tvTitle_detail;
    TextView detail_overview;
    RatingBar ratingBar6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        tvTitle_detail = findViewById(R.id.tvTitle_detail);
        detail_overview = findViewById(R.id.detail_overview);
        ratingBar6 = findViewById(R.id.ratingBar6);
        youTubePlayerView = findViewById(R.id.player);
        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        tvTitle_detail.setText(movie.getTitle());
        detail_overview.setText(movie.getOverview());
        ratingBar6.setRating((float)movie.getRating());

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(String.format(VIDEOS_URL, movie.getMovieID()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");

                    if(results.length() == 0){
                        return;
                    }
                    String youtube_key = results.getJSONObject(0).getString("key");
                    Log.d("detailActivity",youtube_key);
                    initializeYoutube(youtube_key);
                } catch (JSONException e) {
                    Log.e("detailActivity","Failed to parse JSON",e);
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {

            }
        });



    }

    private void initializeYoutube(final String youtube_key) {
        youTubePlayerView.initialize(YOUTUBE_API_KEy,
                new YouTubePlayer.OnInitializedListener() {

                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.cueVideo(youtube_key);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });

    }
}