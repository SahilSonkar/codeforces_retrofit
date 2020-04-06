package com.example.codeforces_retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.security.CodeSource;
import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.View.VISIBLE;

public class MainActivity<mediaPlayer, playcontrol> extends AppCompatActivity implements ListItemAdapter.OnNotelistener {


    ArrayList<data> arrayList;
    private static final String TAG = "MainActivity";
    private static final String BASE_URL = "http://starlord.hackerearth.com/";

    private TextView album_name;
    private TextView artists_name;
    private ImageView album_image;
    private ImageView showplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        album_name = findViewById(R.id.album_name);
        artists_name = findViewById(R.id.artists_name);
        album_image = findViewById(R.id.album_image);
        showplay=findViewById(R.id.control);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CodeforcesAPI codeforcesAPI = retrofit.create(CodeforcesAPI.class);
        Call<ArrayList<data>> call = codeforcesAPI.getdata();  //doubt in this line

        call.enqueue(new Callback<ArrayList<data>>() {
            @Override
            public void onResponse(Call<ArrayList<data>> call, Response<ArrayList<data>> response) {

                arrayList = response.body();


                for (int i = 0; i < arrayList.size(); i++) {
                    Log.i(TAG, "onResponse:\n"
                            + "song " + arrayList.get(i).getSong() + "" +
                            "\n" + "url :" + arrayList.get(i).getUrl() +
                            "\n" + "artists :" + arrayList.get(i).getArtists() +
                            "\n" + "cover :" + arrayList.get(i).getCover_image());
                }

                RecyclerView recyclerView = findViewById(R.id.recycler_view);
                ListItemAdapter listItemAdapter = new ListItemAdapter(arrayList, MainActivity.this, MainActivity.this::OnnoteClick);
                recyclerView.setAdapter(listItemAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }

            @Override
            public void onFailure(Call<ArrayList<data>> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


    MediaPlayer mediaPlayer = new MediaPlayer();
    MediaController mc;
    Uri prevUri = null;
    Uri uri = null;
    int c=0,x=0;



    @Override
    public void OnnoteClick(int position,View itemView) {

        ProgressBar progressBar = itemView.findViewById(R.id.progress);
        //test case

        if(mediaPlayer!=null)
            mediaPlayer.pause();


        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.i(TAG, "onPreExecute: enterd---------------");
                        progressBar.setVisibility(VISIBLE);
                        album_name.setText(arrayList.get(position).getSong());
                        artists_name.setText(arrayList.get(position).getArtists());
                        Glide.with(MainActivity.this)
                                .load(arrayList.get(position).cover_image)
                                .into(album_image);
                showplay.setVisibility(VISIBLE);

//                album_image.animate().rotation(180).start();
                  album_image.animate().rotation(1000f).setDuration(10000).start();

                showplay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(x==0){
                        showplay.setImageResource(R.drawable.pause);
                        mediaPlayer.pause();
                        album_image.animate().rotation(1000f).setDuration(10000).cancel();
                        x=1;}
                        else
                        {
                            x=0;
                            showplay.setImageResource(R.drawable.play);
                            album_image.animate().rotation(1000f).setDuration(10000).start();
                            mediaPlayer.start();
                        }
                    }
                });

            }

            @Override
            protected Void doInBackground(Void... voids) {

                
//                progressBar.setVisibility(View.VISIBLE);

                Log.i(TAG, "doInBackground: Asynch Entered");
                uri = Uri.parse(arrayList.get(position).getUrl());
                if (mediaPlayer != null && c==1)
                {
                    mediaPlayer.pause();
                    Log.i(TAG, "doInBackground: PUSED");
                }
                    Log.i(TAG, "doInBackground: Mediaplayer_start"+uri.toString());
                  mediaPlayer = MediaPlayer.create(MainActivity.this, uri);
//                try {
//                    mediaPlayer.setDataSource(uri.toString());
//                    mediaPlayer.prepare();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                mediaPlayer.start();

                return null;
        }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Log.d(TAG, "onPostExecute: Enters in postexecute");
                progressBar.setVisibility(View.INVISIBLE);
                Log.d(TAG, "onPostExecute: After that");
            }
        }.execute();



                

           

        }

//        public void click(View view)
//        {
//            if(x==0)
//            mediaPlayer.pause();
//            else
//                mediaPlayer.start();
//            x=1;
//        }
    
        @Override
        protected void onStop () {
            super.onStop();
            mediaPlayer.release();
        }

        @Override
        protected void onPause () {
            super.onPause();
            mediaPlayer.pause();
        }

        @Override
        protected void onStart () {
            super.onStart();
            mediaPlayer.reset();
        }

}
