package com.coding.movie.ui.movie;

import com.coding.movie.model.ErrorWebModel;
import com.coding.movie.model.search.SearchMoviesWebModels;
import com.coding.movie.ui.imdb.ImdbContract;
import com.coding.movie.ui.imdb.ImdbModel;

public class MoviePresenter implements MovieContract.Presenter{
    private MovieContract.View view;
    MovieContract.Model model = new MovieModel();

    @Override
    public void attachView(MovieContract.View view) {
        this.view = view;
        model.attachPresenter(this);
    }

    @Override
    public void search(String movieName) {
        model.search(movieName);
        view.showLoading();
    }

    @Override
    public void onSearchResult(SearchMoviesWebModels searchMoviesWebModels) {
        view.onSearchResult(searchMoviesWebModels);
        view.hideLoading();
    }


    @Override
    public void onWebServiceError() {
        view.onWebServiceError();
        view.hideLoading();
    }

    @Override
    public void onWebResponseError(ErrorWebModel error) {
        view.onWebResponseError(error);
        view.hideLoading();
    }
}
