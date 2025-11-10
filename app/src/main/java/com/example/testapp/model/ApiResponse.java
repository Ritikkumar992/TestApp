package com.example.testapp.model;
import java.util.List;

public class ApiResponse {
    private String status;
    private List<ImageModel> data;

    public String getStatus() {
        return status;
    }

    public List<ImageModel> getData() {
        return data;
    }
}
