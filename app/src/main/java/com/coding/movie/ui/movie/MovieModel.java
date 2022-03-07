package com.coding.movie.ui.movie;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.coding.movie.constans.Constants.OMDB_ApiKey;

import android.util.Log;

import com.coding.movie.constans.Constants;
import com.coding.movie.model.ErrorWebModel;
import com.coding.movie.model.search.SearchMoviesWebModels;
import com.coding.movie.ui.imdb.ImdbContract;
import com.coding.movie.utils.ErrorHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieModel implements MovieContract.Model{
    MovieContract.Presenter presenter;

    @Override
    public void attachPresenter(MovieContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void search(String movieName) {

        Constants.webInterface.searchMovies(movieName,OMDB_ApiKey)
                .enqueue(new Callback<SearchMoviesWebModels>() {
                    @Override
                    public void onResponse(Call<SearchMoviesWebModels> call, Response<SearchMoviesWebModels> response) {
                        Log.d(TAG, "onResponse: " + response.code());
                        if (response.isSuccessful()) {
                            presenter.onSearchResult(response.body());
                        }else if (response.code() >= 500) {
                            presenter.onWebServiceError();
                        }else {
                            ErrorWebModel error = ErrorHelper.parseError(response);
                            presenter.onWebResponseError(error);
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchMoviesWebModels> call, Throwable t) {
                        Log.d(TAG, "onFailure: "+t.getMessage());
                        presenter.onWebServiceError();
                    }
                });
    }
}
