package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.testapp.adapter.ImageAdapter;
import com.example.testapp.api.ApiUtils;
import com.example.testapp.model.ApiResponse;
import com.example.testapp.model.ImageModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ImageAdapter imageAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<ImageModel> list;

    private int start = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvData);
        progressBar = findViewById(R.id.pbLoader);
        linearLayoutManager = new LinearLayoutManager(this);
        list = new ArrayList<>();
        imageAdapter = new ImageAdapter(this, list);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(imageAdapter);

        loadImages(start);

        // scroll and pagination.

        // Pagination listener
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView rv, int dx, int dy) {
                if (!rv.canScrollVertically(1) && !isLoading && !isLastPage) {
                    progressBar.setVisibility(View.VISIBLE);
                    loadImages(start);
                }
            }
        });;

    }
    private void loadImages(int startParam) {
        isLoading = true;
        Log.d("API", "Loading from start=" + startParam);

        ApiUtils.getApiInterface().getImages(startParam)
                .enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        Log.d("API", "api url: " + call.request().url());
                        isLoading = false;
                        if (response.isSuccessful() && response.body() != null) {
                            ApiResponse apiResponse = response.body();
                            Log.d("API", "api response: " + new Gson().toJson(response.body()));
                            if (apiResponse.getData() == null || apiResponse.getData().isEmpty()) {
                                isLastPage = true;
                                return;
                            }
                            list.addAll(apiResponse.getData());
                            imageAdapter.notifyDataSetChanged();
                            start = list.size();
                            Log.d("API", "Loaded items: " + apiResponse.getData().size());
                        } else {
                            Log.d("API", "error response: " + "response.errorBody()");
                            Toast.makeText(MainActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        isLoading = false;
                        Toast.makeText(MainActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}