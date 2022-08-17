package com.example.android.movies.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.movies.R;
import com.example.android.movies.data.MovieAdapter;
import com.example.android.movies.model.Movie;
import com.example.android.movies.utils.Util;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class MovieActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    String url;
    Intent intent;
    ImageView posterImageView;
    TextView titleTextView;
    TextView yearTextView;
    TextView releasedTextView;
    TextView runtimeTextView;
    TextView genreTextView;
    TextView directorTextView;
    TextView writerTextView;
    TextView actorsTextView;
    TextView plotTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        posterImageView = findViewById(R.id.posterImageView);
        titleTextView = findViewById(R.id.titleTextView);
        yearTextView = findViewById(R.id.yearTextView);
        releasedTextView = findViewById(R.id.releasedTextView);
        runtimeTextView = findViewById(R.id.runtimeTextView);
        genreTextView = findViewById(R.id.genreTextView);
        directorTextView = findViewById(R.id.directorTextView);
        writerTextView = findViewById(R.id.writerTextView);
        actorsTextView = findViewById(R.id.actorsTextView);
        plotTextView = findViewById(R.id.plotTextView);

        intent = getIntent();

        requestQueue = Volley.newRequestQueue(MovieActivity.this);

        getCurrentMovie();

    }

    private void getCurrentMovie() {

        if (intent !=null) {
            url = Util.URL_KEY_ID.concat(intent.getStringExtra("imdbID"));
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url, null, response -> {
            try {


                String title = response.getString("Title");
                String year = response.getString("Year");
                String released = response.getString("Released");
                String runtime = response.getString("Runtime");
                String genre = response.getString("Genre");
                String director = response.getString("Director");
                String writer = response.getString("Writer");
                String actors = response.getString("Actors");
                String plot = response.getString("Plot");

                titleTextView.setText("Title: " + title);
                yearTextView.setText("Year: " + year);
                releasedTextView.setText("Released: " + released);
                runtimeTextView.setText("Runtime: " + runtime);
                genreTextView.setText("Genre: " + genre);
                directorTextView.setText("Director: " + director);
                writerTextView.setText("Writer: " + writer);
                actorsTextView.setText("Actors: " + actors);
                plotTextView.setText("About movie: " + plot);

                String posterUrl = response.getString("Poster");

                Movie movie = new Movie();
                movie.setPosterUrl(posterUrl);
                Picasso.get().load(posterUrl).fit().centerInside().into(posterImageView);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);

        requestQueue.add(request);
    }
}