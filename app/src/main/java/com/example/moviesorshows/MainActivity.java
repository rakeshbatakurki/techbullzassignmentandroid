package com.example.moviesorshows;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.moviesorshows.commons.MovieOrShow;
import com.example.moviesorshows.commons.MovieOrShowList;
import com.example.moviesorshows.commons.RecyclerViewAdapter;
import com.example.moviesorshows.network.RetrofitUtil;
import com.example.moviesorshows.network.ServiceMoviesOrShows;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Retrofit retrofit;
    ServiceMoviesOrShows service = null;
    Call<MovieOrShowList> repos = null;
    SearchManager searchManager;
    SearchView searchView = null;
    RecyclerViewAdapter adapter;

    ProgressBar progressBarRevolving;
    private RecyclerView recyclerView;

    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Movies and Shows");

        progressBarRevolving = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        initModel();
        progressBarRevolving.setVisibility(View.VISIBLE);
        getMoviesAndShows();
    }

    private void initModel() {
        retrofit = RetrofitUtil.getRetrofitObject();
        service = retrofit.create(ServiceMoviesOrShows.class);
        repos = service.listRepos("batman", "2e2008fd");
    }

    private void getMoviesAndShows() {
        repos.enqueue(new Callback<MovieOrShowList>() {
            @Override
            public void onResponse(Call<MovieOrShowList> call, Response<MovieOrShowList> response) {

                Toast.makeText(getApplicationContext(), String.format("OK"), Toast.LENGTH_SHORT).show();
                MovieOrShowList allRepos = response.body();
                Log.e("====>", "onResponse()" + allRepos.getSearch());
                Log.e("====>", "onResponse(): " + call.request().url());

                adapter = new RecyclerViewAdapter(allRepos.getSearch(), MainActivity.this);
                recyclerView.setAdapter(adapter);
                progressBarRevolving.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieOrShowList> call, Throwable t) {

                Toast.makeText(getApplicationContext(), String.format("Something went wrong please try after some time"), Toast.LENGTH_SHORT).show();
                Log.e("====>", "onFailure: Failed :()" + t.getMessage());
                Log.e("====>", "onResponseFailed: " + call.request().url());
                progressBarRevolving.setVisibility(View.GONE);
            }

        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dashboard, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchItem = menu.findItem(R.drawable.ic_baseline_search_24);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressBarRevolving.setVisibility(View.VISIBLE);
                try{
                    callSearch(query);
                }
                catch(Exception e)
                {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            public void callSearch(String query) {
                repos = service.listRepos(query, "2e2008fd");
                repos.enqueue(new Callback<MovieOrShowList>() {
                    @Override
                    public void onResponse(Call<MovieOrShowList> call, Response<MovieOrShowList> response) {

                        Toast.makeText(getApplicationContext(), String.format("OK"), Toast.LENGTH_SHORT).show();
                        MovieOrShowList allRepos = response.body();
                        Log.e("====>", "onResponse()" + allRepos.getSearch());
                        Log.e("====>", "onResponse(): " + call.request().url());

                        adapter.addAll(allRepos.getSearch());

                        progressBarRevolving.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<MovieOrShowList> call, Throwable t) {

                        Toast.makeText(getApplicationContext(), String.format("Unable to fetch data..! try after some time"), Toast.LENGTH_SHORT).show();
                        Log.e("====>", "onFailure: Failed :()" + t.getMessage());
                        Log.e("====>", "onResponseFailed: " + call.request().url());
                        progressBarRevolving.setVisibility(View.GONE);
                    }

                });
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}