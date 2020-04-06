package com.example.codeforces_retrofit;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ViewHolder>{


    public static ProgressBar progressBar;
    private static final String TAG = "ListItemAdapter";

    private ArrayList<data> arrayList= new ArrayList<data>();
    private Context context;
    private MediaPlayer mediaPlayer;
    private OnNotelistener monNotelistener;





    public ListItemAdapter(ArrayList<data> arrayList,Context context,OnNotelistener onNotelistener) {
        this.arrayList = arrayList;
        this.context=context;
        this.monNotelistener=onNotelistener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem,parent,false);
        return new ViewHolder(view,monNotelistener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.artistName.setText(arrayList.get(position).getArtists());


        Glide.with(context)
                .asBitmap()
                .load(arrayList.get(position).getCover_image())
                .into(holder.albumthumbnail);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView albumthumbnail;
        TextView artistName;
        RelativeLayout parent_layout;
        OnNotelistener onNotelistener;



        public ViewHolder(View itemView,OnNotelistener onNotelistener) {
            super(itemView);
            artistName = itemView.findViewById(R.id.artistName);
            albumthumbnail = itemView.findViewById(R.id.albumThumbnail);
            parent_layout = itemView.findViewById(R.id.parent_layout);
            progressBar=itemView.findViewById(R.id.progress);
            this.onNotelistener=onNotelistener;
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {

            Log.i(TAG, "onClick: ENTERED IN ON_CLICK------------");
            onNotelistener.OnnoteClick(getAdapterPosition(),itemView);
        }
    }

       public interface OnNotelistener{
            void OnnoteClick(int position,View itemView);
       }


}


