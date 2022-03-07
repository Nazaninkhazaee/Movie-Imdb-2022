package com.coding.movie.ui.movie;

import com.coding.movie.model.ErrorWebModel;
import com.coding.movie.model.search.SearchMoviesWebModels;

public interface MovieContract {
    interface View{
        void onSearchResult(SearchMoviesWebModels searchMoviesWebModels);
        void onWebServiceError();
        void onWebResponseError(ErrorWebModel error);
        void showLoading() ;
        void hideLoading() ;

    }


    interface Presenter{
        void attachView(View view);
        void search(String movieName);
        void onSearchResult(SearchMoviesWebModels searchMoviesWebModels);
        void onWebServiceError();
        void onWebResponseError(ErrorWebModel error);

    }

    interface Model {
        void attachPresenter(Presenter presenter);
        void search(String movieName);
    }
}
