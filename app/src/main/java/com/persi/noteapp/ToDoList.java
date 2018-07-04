package com.persi.noteapp;

import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ToDoList extends AppCompatActivity {

    private ArrayList<NoteDataModel> mDataArray;
    private DividerItemDecoration mDividerItemDecoration;
    private TextView mUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        mUsername=findViewById(R.id.to_do_list_user);
        SharedPreferences prefs = getSharedPreferences("UserDetail", MODE_PRIVATE);
        mUsername.setText(prefs.getString("name",null));
        NetworkingCall();


    }

    //http request will be made using this method
    private void NetworkingCall(){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api=retrofit.create(Api.class);
        Call<ArrayList<NoteDataModel>> call=api.getJson();
        call.enqueue(new Callback<ArrayList<NoteDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<NoteDataModel>> call, Response<ArrayList<NoteDataModel>> response) {
                mDataArray=response.body();
                makeList();

            }

            @Override
            public void onFailure(Call<ArrayList<NoteDataModel>> call, Throwable t) {
                Log.e("Note","fail"+t.toString());
                Toast.makeText(ToDoList.this,"Request failed",Toast.LENGTH_SHORT).show();

            }
        });
    }

    //this method will put data in recycler view
    private void makeList(){

        RecyclerView recyclerView=findViewById(R.id.to_do_list);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(ToDoList.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);
        AdapterClass adapterClass=new AdapterClass(mDataArray,ToDoList.this);
        recyclerView.setAdapter(adapterClass);

    }


}
