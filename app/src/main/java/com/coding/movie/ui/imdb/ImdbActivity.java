package com.coding.movie.ui.imdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.coding.movie.R;
import com.coding.movie.base.BaseActivity;
import com.coding.movie.model.ErrorWebModel;
import com.coding.movie.model.search.SearchMoviesWebModels;
import com.coding.movie.ui.movie.MovieActivity;
import com.coding.movie.utils.WebServiceHelper;

import butterknife.BindView;
import butterknife.OnClick;

public class ImdbActivity extends BaseActivity implements ImdbContract.View{
    //#region BindView
    @BindView(R.id.btn_search)
    ImageView btnSearch;
    @BindView(R.id.tv_toolbar)
    TextView tvToolbar;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.rv_movie_list)
    RecyclerView rvMovieList;
    @BindView(R.id.et_search)
    EditText etSearch;
    ImdbContract.Presenter presenter;

    @Override
    protected void initializePresenter() {
        presenter = new ImdbPresenter();
        presenter.attachView(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_imdb;
    }

    @Override
    protected void init() {
    }


    //#region click
    @OnClick(R.id.btn_search)
    void searchMovie() {
        String name = etSearch.getText().toString();
        if (name.length()!= 0){
            Intent intent = new Intent(mContext, MovieActivity.class);
            intent.putExtra("Movie", name);
            startActivity(intent);
        }
        else {
            tvToolbar.setVisibility(View.GONE);
            etSearch.setVisibility(View.VISIBLE);
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
                ,mActivity,mContext);
    }

    @Override
    public void onWebResponseError(ErrorWebModel error) {
        WebServiceHelper.showDialogError(error.getMessage(), mActivity, mContext);
    }
}