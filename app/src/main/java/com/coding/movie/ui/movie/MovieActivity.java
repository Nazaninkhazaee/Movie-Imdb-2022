package com.coding.movie.ui.movie;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.coding.movie.R;
import com.coding.movie.base.BaseActivity;
import com.coding.movie.data.FavDB;
import com.coding.movie.data.MovieFavItem;
import com.coding.movie.model.ErrorWebModel;
import com.coding.movie.model.search.SearchMoviesWebModels;
import com.coding.movie.ui.imdb.ImdbActivity;
import com.coding.movie.utils.WebServiceHelper;
import com.orhanobut.hawk.Hawk;

import butterknife.BindView;
import butterknife.OnClick;

public class MovieActivity extends BaseActivity implements MovieContract.View {
    MovieContract.Presenter presenter;
    private String movieName;
    //#region BindView
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.btn_add_favorite)
    ImageButton btnAddFavorite;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.iv_movie_poster)
    ImageView ivMoviePoster;
    @BindView(R.id.tv_movie_title)
    TextView tvMovieTitle;
    @BindView(R.id.tv_movie_rating)
    TextView tvMovieRating;
    @BindView(R.id.tv_movie_description)
    TextView tvMovieDescription;
    private FavDB favDB;
    private String imdbId = "";
    private String poster = "";
    private Boolean favStatus = false;;


    @Override
    protected void initializePresenter() {
        presenter = new MoviePresenter();
        presenter.attachView(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_movie;
    }

    @Override
    protected void init() {
        btnAddFavorite.setEnabled(false);
        favDB = new FavDB(mContext);
        //create table on first
        SharedPreferences prefs = mContext.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart) {
            createTableOnFirstStart();
        }
        movieName = getIntent().getStringExtra("Movie");
        Log.i("name", movieName);
        presenter.search(movieName);

    }

    //#region click
    @OnClick(R.id.btn_back)
    void setBtnBack() {
        finish();
    }

    @OnClick(R.id.btn_add_favorite)
    void setBtnAddFavorite() {
        if (favStatus){
            btnAddFavorite.setImageResource(R.drawable.ic_favorite_withe);
            favStatus = false;
            Hawk.delete(imdbId);
        }else{
            btnAddFavorite.setImageResource(R.drawable.ic_favorite_red);
            favStatus = true;
            Hawk.put("MovieId",imdbId);
        }
    }

    @Override
    public void onSearchResult(SearchMoviesWebModels searchMoviesWebModels) {
        btnAddFavorite.setEnabled(true);
        if (searchMoviesWebModels.getImdbID()!= null){
            imdbId = searchMoviesWebModels.getImdbID();
        }
        if (searchMoviesWebModels.getPoster()!= null){
            poster = searchMoviesWebModels.getPoster();
        }
        if (poster.length() > 10) {
            Glide.with(mContext)
                    .load(searchMoviesWebModels.getPoster())
                    .into(ivMoviePoster);
        }
        tvMovieTitle.setText(searchMoviesWebModels.getTitle());
        tvMovieRating.setText(searchMoviesWebModels.getImdbRating());
        tvMovieDescription.setText(searchMoviesWebModels.getPlot());
        String movieId = Hawk.get("MovieId","");
        if (movieId != null && movieId.equals(imdbId)) {
            btnAddFavorite.setImageResource(R.drawable.ic_favorite_red);
            favStatus = true;
        } else {
            btnAddFavorite.setImageResource(R.drawable.ic_favorite_withe);
            favStatus = false;
        }

    }

    @Override
    public void showLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!mProgress.isShowing()) {
                    mProgress.show();
                }
            }
        });
    }

    @Override
    public void hideLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgress.isShowing()) {
                    mProgress.dismiss();
                }

            }
        });
    }

    @Override
    public void onWebServiceError() {
        WebServiceHelper.showDialogError(getResources().getString(R.string.error_message_server)
                , mActivity, mContext);
    }

    @Override
    public void onWebResponseError(ErrorWebModel error) {
        WebServiceHelper.showDialogError(error.getMessage(), mActivity, mContext);
    }

    private void createTableOnFirstStart() {
        favDB.insertEmpty();

        SharedPreferences prefs = mContext.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

//    private void readCursorData(MovieFavItem favItem, String title) {
//        Cursor cursor = favDB.read_all_data(favItem.getKey_id());
//        SQLiteDatabase db = favDB.getReadableDatabase();
//        try {
//            while (cursor.moveToNext()) {
//                String item_fav_status = cursor.getString(cursor.getColumnIndex(FavDB.FAVORITE_STATUS));
//                coffeeItem.setFavStatus(item_fav_status);
//
//                //check fav status
//                if (item_fav_status != null && item_fav_status.equals("1")) {
//                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
//                } else if (item_fav_status != null && item_fav_status.equals("0")) {
//                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp);
//                }
//            }
//        } finally {
//            if (cursor != null && cursor.isClosed())
//                cursor.close();
//            db.close();
//        }
//
//    }

//    // like click
//    private void likeClick (MovieFavItem favItem, Button favBtn, final TextView textLike) {
//        DatabaseReference refLike = FirebaseDatabase.getInstance().getReference().child("likes");
//        final DatabaseReference upvotesRefLike = refLike.child(coffeeItem.getKey_id());
//
//        if (favItem.getFavStatus().equals("0")) {
//
//            favItem.setFavStatus("1");
//
//            favDB.insertIntoTheDatabase(favItem.getTitle(), favItem.getImageResourse(),
//                    favItem.getKey_id(), favItem.getRating(),favItem.getDesc(), favItem.getFavStatus());
//
//            favBtn.setBackgroundResource(R.drawable.ic_favorite_red);
//            favBtn.setSelected(true);
//
//            upvotesRefLike.runTransaction(new Transaction.Handler() {
//                @NonNull
//                @Override
//                public Transaction.Result doTransaction(@NonNull final MutableData mutableData) {
//                    try {
//                        Integer currentValue = mutableData.getValue(Integer.class);
//                        if (currentValue == null) {
//                            mutableData.setValue(1);
//                        } else {
//                            mutableData.setValue(currentValue + 1);
//                            new Handler(Looper.getMainLooper()).post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    textLike.setText(mutableData.getValue().toString());
//                                }
//                            });
//                        }
//                    } catch (Exception e) {
//                        throw e;
//                    }
//                    return Transaction.success(mutableData);
//                }
//
//                @Override
//                public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
//                    System.out.println("Transaction completed");
//                }
//            });
//
//
//        } else if (favItem.getFavStatus().equals("1")) {
//            favItem.setFavStatus("0");
//            favDB.remove_fav(favItem.getKey_id());
//            favBtn.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp);
//            favBtn.setSelected(false);
//
//            upvotesRefLike.runTransaction(new Transaction.Handler() {
//                @NonNull
//                @Override
//                public Transaction.Result doTransaction(@NonNull final MutableData mutableData) {
//                    try {
//                        Integer currentValue = mutableData.getValue(Integer.class);
//                        if (currentValue == null) {
//                            mutableData.setValue(1);
//                        } else {
//                            mutableData.setValue(currentValue - 1);
//                            new Handler(Looper.getMainLooper()).post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    textLike.setText(mutableData.getValue().toString());
//                                }
//                            });
//                        }
//                    } catch (Exception e) {
//                        throw e;
//                    }
//                    return Transaction.success(mutableData);
//                }
//
//                @Override
//                public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
//                    System.out.println("Transaction completed");
//                }
//            });
//        }
//    }

}