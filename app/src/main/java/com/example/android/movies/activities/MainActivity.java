package com.example.android.movies.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private ArrayList <Movie> movies;
    private RequestQueue requestQueue;
    EditText editTextSearchMovie;
    ImageButton button;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextSearchMovie = findViewById(R.id.editTextSearchMovie);
        button = findViewById(R.id.button);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        movies = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);

        button.setOnClickListener(this);

        getMovies();
    }

    private void getMovies() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {


                    JSONArray jsonArray = response.getJSONArray("Search");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String title = jsonObject.getString("Title");
                        String year = jsonObject.getString("Year");
                        String posterUrl = jsonObject.getString("Poster");
                        String imdbID = jsonObject.getString("imdbID");

                        Movie movie = new Movie();
                        movie.setTitle(title);
                        movie.setYear(year);
                        movie.setPosterUrl(posterUrl);
                        movie.setImdbID(imdbID);

                        movies.add(movie);

                    }

                    movieAdapter = new MovieAdapter(MainActivity.this, movies);

                    recyclerView.setAdapter(movieAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);

    }

    @Override
    public void onClick(View view) {
        String titleSearchingMovie = String.valueOf(editTextSearchMovie.getText());
        url = Util.URL_KEY + "&s=" + titleSearchingMovie;
        movies.clear();
        getMovies();
    }
}