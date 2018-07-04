package com.persi.noteapp;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

//this interface is required for retrofit
public interface Api {
    String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @GET("todos")
    Call<ArrayList<NoteDataModel>> getJson();
}
