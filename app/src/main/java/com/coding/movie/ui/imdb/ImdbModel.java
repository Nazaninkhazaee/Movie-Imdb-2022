package com.coding.movie.ui.imdb;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.coding.movie.constans.Constants.OMDB_ApiKey;

import android.util.Log;

import com.coding.movie.constans.Constants;
import com.coding.movie.model.ErrorWebModel;
import com.coding.movie.model.search.SearchMoviesWebModels;
import com.coding.movie.utils.ErrorHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImdbModel implements ImdbContract.Model{
    ImdbContract.Presenter presenter;

    @Override
    public void attachPresenter(ImdbContract.Presenter presenter) {
        this.presenter = presenter;
    }

}
