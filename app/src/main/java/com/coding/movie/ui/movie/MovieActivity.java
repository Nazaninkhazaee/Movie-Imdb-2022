package com.coding.movie.ui.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.coding.movie.R;
import com.coding.movie.base.BaseActivity;
import com.coding.movie.model.ErrorWebModel;
import com.coding.movie.model.search.SearchMoviesWebModels;
import com.coding.movie.ui.imdb.ImdbContract;
import com.coding.movie.ui.imdb.ImdbPresenter;
import com.coding.movie.utils.WebServiceHelper;

import butterknife.BindView;
import butterknife.OnClick;

public class MovieActivity extends BaseActivity implements MovieContract.View {
    MovieContract.Presenter presenter;
    private String movieName;
    //#region BindView
    @BindView(R.id.btn_back)
    ImageView btnBack;
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
        Log.i("name",movieName);
        presenter.search(movieName);
    }

    //#region click
    @OnClick(R.id.btn_back)
    void setBtnBack() {
       finish();
    }

    @Override
    public void onSearchResult(SearchMoviesWebModels searchMoviesWebModels) {
        String poster = searchMoviesWebModels.getPoster().toString();
        if (poster.length()>10){
            Glide.with(mContext)
                    .load(searchMoviesWebModels.getPoster())
                    .into(ivMoviePoster);
        }
        tvMovieTitle.setText(searchMoviesWebModels.getTitle());
        tvMovieRating.setText(searchMoviesWebModels.getImdbRating());
        tvMovieDescription.setText(searchMoviesWebModels.getPlot());

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
                ,mActivity,mContext);
    }

    @Override
    public void onWebResponseError(ErrorWebModel error) {
        WebServiceHelper.showDialogError(error.getMessage(), mActivity, mContext);
    }
}