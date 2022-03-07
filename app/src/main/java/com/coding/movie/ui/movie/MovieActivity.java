package com.coding.movie.ui.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.coding.movie.R;
import com.coding.movie.base.BaseActivity;
import com.coding.movie.model.ErrorWebModel;
import com.coding.movie.model.search.SearchMoviesWebModels;
import com.coding.movie.ui.imdb.ImdbContract;
import com.coding.movie.ui.imdb.ImdbPresenter;
import com.coding.movie.utils.WebServiceHelper;

import butterknife.BindView;

public class MovieActivity extends BaseActivity implements MovieContract.View {
    MovieContract.Presenter presenter;
    private String movieName;
    //#region BindView
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_toolbar)
    TextView tvToolbar;


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

    @Override
    public void onSearchResult(SearchMoviesWebModels searchMoviesWebModels) {
        tvToolbar.setText(searchMoviesWebModels.getTitle());
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