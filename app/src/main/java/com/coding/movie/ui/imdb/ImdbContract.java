package com.coding.movie.ui.imdb;

import com.coding.movie.model.ErrorWebModel;
import com.coding.movie.model.search.SearchMoviesWebModels;

public interface ImdbContract {
    interface View{
        void showLoading() ;
        void hideLoading() ;
        void onWebServiceError();
        void onWebResponseError(ErrorWebModel error);

    }


    interface Presenter{
        void attachView(View view);
        void onWebServiceError();
        void onWebResponseError(ErrorWebModel error);

    }

    interface Model {
        void attachPresenter(Presenter presenter);
    }
}
