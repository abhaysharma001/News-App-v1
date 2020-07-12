package com.superior.labs.hindbhashi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitArrayApi {

    @GET("/wp-json/wp/v2/posts?per_page=60&_fields[]=date&_fields[]=id&_fields[]=content&_fields[]=title&_fields[]=_links&_fields[]=categories")
    Call<List<WPPost>>  getPostInfo();
}
