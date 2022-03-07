package com.coding.movie.utils;

import com.coding.movie.constans.Constants;
import com.coding.movie.model.ErrorWebModel;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ErrorHelper {
    public static ErrorWebModel parseError(Response response) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        Converter<ResponseBody, ErrorWebModel> converter =
                retrofit.responseBodyConverter(ErrorWebModel.class, new Annotation[0]);
        ErrorWebModel error;
        try {
            assert response.errorBody() != null;
                error = converter.convert(response.errorBody());
        } catch (IOException e) {
           return new ErrorWebModel();
        }

        return error;
    }
}
