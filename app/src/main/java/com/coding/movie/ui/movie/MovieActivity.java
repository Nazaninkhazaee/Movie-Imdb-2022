package com.coding.movie.ui.movie;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MovieActivity extends BaseActivity implements MovieContract.View {

    //#region BindView
    MovieContract.Presenter presenter;
    private String movieName;
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
    private String imdbId, poster;
    private Boolean favStatus = false;
    private MovieFavItem favItem;
    private ArrayList<MovieFavItem> movieFavItemArrayList;
    //endregion


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
        movieName = getIntent().getStringExtra("Movie");
        btnAddFavorite.setEnabled(false);
        favDB = new FavDB(mContext);
        presenter.search(movieName);

    }

    //#region click
    @OnClick(R.id.btn_back)
    void setBtnBack() {
        Intent intent = new Intent(mContext, ImdbActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btn_add_favorite)
    void setBtnAddFavorite() {
        if (favStatus) {
            btnAddFavorite.setImageResource(R.drawable.ic_favorite_withe);
            favStatus = false;
            favDB.remove_fav(favItem.getKey_id());

        } else {
            btnAddFavorite.setImageResource(R.drawable.ic_favorite_red);
            favStatus = true;
            favDB.insertIntoTheDatabase(
                    favItem.getImageResourse(),
                    favItem.getTitle(),
                    favItem.getKey_id(),
                    favItem.getFavStatus(),
                    favItem.getRating(),
                    favItem.getDesc());
        }
    }

    @Override
    public void onSearchResult(SearchMoviesWebModels searchMoviesWebModels) {
        btnAddFavorite.setEnabled(true);
        if (searchMoviesWebModels.getImdbID() != null) {
            imdbId = searchMoviesWebModels.getImdbID();
        }
        if (searchMoviesWebModels.getPoster() != null) {
            poster = searchMoviesWebModels.getPoster();
        }
        if (poster.length() > 10) {
            Glide.with(mContext)
                    .load(searchMoviesWebModels.getPoster())
                    .into(ivMoviePoster);
        }
        String title = searchMoviesWebModels.getTitle();
        tvMovieTitle.setText(title);
        String rating = searchMoviesWebModels.getImdbRating();
        tvMovieRating.setText(rating);
        String desc = searchMoviesWebModels.getPlot();
        tvMovieDescription.setText(desc);
        movieFavItemArrayList = new ArrayList<>();
        movieFavItemArrayList = favDB.search(imdbId);

        if (movieFavItemArrayList!=null) {
            int s = movieFavItemArrayList.size();

        for (int i = 0; i < s; i++) {
            String idSearch = movieFavItemArrayList.get(i).getKey_id();
            if (idSearch.equals(imdbId)) {
                btnAddFavorite.setImageResource(R.drawable.ic_favorite_red);
                favStatus = true;
            } else {
                btnAddFavorite.setImageResource(R.drawable.ic_favorite_withe);
                favStatus = false;
            }
        }
        }
        favItem = new MovieFavItem(poster, title, imdbId, favStatus.toString(), rating, desc);
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

}