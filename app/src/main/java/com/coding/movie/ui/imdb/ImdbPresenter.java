package com.coding.movie.ui.imdb;

import com.coding.movie.model.ErrorWebModel;
import com.coding.movie.model.search.SearchMoviesWebModels;

public class ImdbPresenter  implements ImdbContract.Presenter{
    private ImdbContract.View view;
    ImdbContract.Model model = new ImdbModel();
    @Override
    public void attachView(ImdbContract.View view) {
        this.view = view;
        model.attachPresenter(this);
    }

    @Override
    public void onWebServiceError() {
        view.onWebServiceError();
    }

    @Override
    public void onWebResponseError(ErrorWebModel error) {
        view.onWebResponseError(error);
    }
}
