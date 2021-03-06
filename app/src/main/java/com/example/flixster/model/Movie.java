package com.example.flixster.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {
    String backdrop_path;
    String posterPath;
    String title;
    String overview;
    double rating;
    int movieID;
    public  Movie(){}


    public int getMovieID() {
        return movieID;
    }

    public Movie(JSONObject jsonObject) throws JSONException {
        backdrop_path = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        rating = jsonObject.getDouble("vote_average");
        movieID = jsonObject.getInt("id");
    }

    public static List<Movie> fromJsonArry(JSONArray movieJsonArry)throws JSONException{
        List<Movie> movies = new ArrayList<>();
        int i;
        for(i=0; i < movieJsonArry.length(); i++)
        {
            movies.add(new Movie(movieJsonArry.getJSONObject((i))));

        }
        return movies;
    }

    public String getBackdrop_path() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",backdrop_path);
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",posterPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getRating() {
        return rating;
    }
}
