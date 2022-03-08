package com.coding.movie.ui.imdb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.coding.movie.R;
import com.coding.movie.data.FavDB;
import com.coding.movie.data.MovieFavItem;

import java.util.ArrayList;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {

    private ArrayList<MovieFavItem> movieFavItemArrayList;
    private Context context;


    public FavAdapter(ArrayList<MovieFavItem> movieFavItemArrayList, Context context) {
        this.movieFavItemArrayList = movieFavItemArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.favorite_list_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavAdapter.ViewHolder holder, int i) {
        MovieFavItem fl=movieFavItemArrayList.get(i);
        String poster = fl.getImageResourse();
        String title = fl.getTitle();
        String rate = fl.getRating();
        String id = fl.getKey_id();
        String desc = fl.getDesc();
        holder.tvTitle.setText(title);
        holder.tvRating.setText(rate);
            Glide.with(context)
                    .load(poster)
                    .into(holder.ivPoster);
        }

//

    @Override
    public int getItemCount() {
        return movieFavItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvTitle;
        TextView tvRating;

        public ViewHolder(@android.support.annotation.NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.iv_movie_poster);
            tvTitle = itemView.findViewById(R.id.tv_movie_title);
            tvRating = itemView.findViewById(R.id.tv_movie_rating);
        }
    }

}
