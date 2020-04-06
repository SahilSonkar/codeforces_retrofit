package com.example.codeforces_retrofit;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface CodeforcesAPI {

    String BASE_URL="http://starlord.hackerearth.com/";

//    @Headers("Content-Type: application/json")
    @GET("studio")
    Call<ArrayList<data>> getdata();

}


