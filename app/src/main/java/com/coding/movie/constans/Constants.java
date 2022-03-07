package com.coding.movie.constans;

import com.coding.movie.services.WebServiceInterface;
import com.coding.movie.utils.RetrofitGenerator;

public class Constants {
    public static String BASE_URL = "http://www.omdbapi.com" ;


    public static String OMDB_ApiKey = "d0135a7f" ;
    public static WebServiceInterface webInterface = RetrofitGenerator.create(WebServiceInterface.class);
}
