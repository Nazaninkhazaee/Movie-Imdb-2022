package com.coding.movie.ui.imdb;

import static com.coding.movie.data.FavDB.KEY_ID;
import static com.coding.movie.data.FavDB.TABLE_NAME;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.coding.movie.R;
import com.coding.movie.base.BaseActivity;
import com.coding.movie.data.FavDB;
import com.coding.movie.data.MovieFavItem;
import com.coding.movie.model.ErrorWebModel;
import com.coding.movie.ui.movie.MovieActivity;
import com.coding.movie.utils.WebServiceHelper;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class ImdbActivity extends BaseActivity implements ImdbContract.View {
    //#region BindView
    @BindView(R.id.btn_search)
    ImageView btnSearch;
    @BindView(R.id.tv_toolbar)
    TextView tvToolbar;
    @BindView(R.id.tv_favorite_movies)
    TextView tvFavoriteMovies;
    @BindView(R.id.rv_movie_list)
    RecyclerView rvMovieList;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    ImdbContract.Presenter presenter;
    private FavDB favDB;
    private FavAdapter adapter;
    private ArrayList<MovieFavItem> movieFavItemArrayList;
    //endregion


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
        rvMovieList.setHasFixedSize(true);
        rvMovieList.setLayoutManager(new LinearLayoutManager(mContext));
        favDB = new FavDB(mContext);
        movieFavItemArrayList = new ArrayList<>();
        movieFavItemArrayList = favDB.readCourses();
        int size = movieFavItemArrayList.size();
        if (size == 0) {
            rvMovieList.setVisibility(View.INVISIBLE);
            tvFavoriteMovies.setVisibility(View.INVISIBLE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.INVISIBLE);
            tvFavoriteMovies.setVisibility(View.VISIBLE);
            rvMovieList.setVisibility(View.VISIBLE);
        }
        adapter = new FavAdapter(movieFavItemArrayList, mContext, new FavAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MovieFavItem movieFavItemArrayList) {
                Intent intent = new Intent(mContext, MovieActivity.class);
                intent.putExtra("Movie", movieFavItemArrayList.getTitle());
                startActivity(intent);
                finish();
            }
        });
        rvMovieList.setHasFixedSize(true);
        rvMovieList.setLayoutManager(new GridLayoutManager(mContext, 2));
        rvMovieList.setAdapter(adapter);
    }


    //#region click
    @OnClick(R.id.btn_search)
    void searchMovie() {
        String name = etSearch.getText().toString();
        if (name.length() != 0) {
            Intent intent = new Intent(mContext, MovieActivity.class);
            intent.putExtra("Movie", name);
            startActivity(intent);
            finish();
        } else {
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
        WebServiceHelper.dialogBox(getResources().getString(R.string.error_message_server)
                , mActivity, mContext);

    }

    @Override
    public void onWebResponseError(ErrorWebModel error) {
        WebServiceHelper.dialogBox(error.getMessage(), mActivity, mContext);
    }

}