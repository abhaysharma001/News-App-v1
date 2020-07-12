package com.superior.labs.hindbhashi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitSinglePostApi {
    @GET
    Call<WPPost> getSinglePost(@Url String url);
}
