//package com.coding.movie.ui.imdb;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.coding.movie.R;
//
//import java.util.List;
//
//public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
//    Context context;
//
//    public FavoriteAdapter(List<FavoriteList> favoriteLists, Context context) {
//        this.favoriteLists = favoriteLists;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.favorite_list_layout,viewGroup,false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
//        FavoriteList fl=favoriteLists.get(i);
//        String poster = fl.getImage();
//        if (poster.length()>10){
//            Glide.with(context)
//                    .load(poster)
//                    .into(viewHolder.ivPoster);
//        }
//        viewHolder.tvTitle.setText(fl.getTitle());
//        viewHolder.tvRating.setText(fl.getRating());
//    }
//
//    @Override
//    public int getItemCount() {
//        return favoriteLists.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder{
//        ImageView ivPoster;
//        TextView tvTitle;
//        TextView tvRating;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            ivPoster=(ImageView)itemView.findViewById(R.id.iv_movie_poster);
//            tvTitle=(TextView)itemView.findViewById(R.id.tv_movie_title);
//            tvRating=(TextView)itemView.findViewById(R.id.tv_movie_rating);
//        }
//    }
//}