package com.superior.labs.hindbhashi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;
import com.tuyenmonkey.mkloader.MKLoader;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewPostActivity extends AppCompatActivity {

    private ImageView ivNews;
    private ArrayList<Model> wpList;
    private MKLoader loader,loader2;
    private String baseURL = "https://app.hindbhashi.com",img;
    private TextView tvHeading,tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        FirebaseMessaging.getInstance().subscribeToTopic("SHORT_NEWS");

        ivNews = findViewById(R.id.newsViewImage);
        tvHeading = findViewById(R.id.HeadingView);
        loader = findViewById(R.id.loadingViewItem);
        loader2 = findViewById(R.id.loadingViewImage);
        tvContent = findViewById(R.id.contentView);

        //String id = getIntent().getExtras().get("id").toString();
        //Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        getRetrofit();

    }

    public void getImageRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitImageApi service = retrofit.create(RetrofitImageApi.class);
        Call<WPImage> call = service.getImageInfo(img);
        call.enqueue(new Callback<WPImage>() {
            @Override
            public void onResponse(Call<WPImage> call, Response<WPImage> response) {
                WPImage wpImage = response.body();
                String image = wpImage.getSourceUrl();
                Picasso.get().load(image).into(ivNews);
                loader2.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<WPImage> call, Throwable t) {

            }
        });

    }



    public void getRetrofit(){
        String id = getIntent().getExtras().get("id").toString();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitSinglePostApi service = retrofit.create(RetrofitSinglePostApi.class);
        Call<WPPost> call = service.getSinglePost("/wp-json/wp/v2/posts/"+id);
        call.enqueue(new Callback<WPPost>() {
            @Override
            public void onResponse(Call<WPPost> call, Response<WPPost> response) {
                loader.setVisibility(View.GONE);
                img = response.body().getLinks().getWpFeaturedmedia().get(0).getHref();
                //getImageRetrofit();
                Log.e("MainActivity","Response"+response.body().getTitle());

                String tempDetatils = response.body().getContent().getRendered().toString();
                tempDetatils = tempDetatils.replace("<p>","");
                tempDetatils = tempDetatils.replace("</p>","");
                tempDetatils = tempDetatils.replace("<br/>","");
                tempDetatils = tempDetatils.replace("<strong>","");
                tempDetatils = tempDetatils.replace("</strong>","");
                tempDetatils = tempDetatils.replace("<br />","");
                tempDetatils = tempDetatils.replace("<br>","");
                tempDetatils = tempDetatils.replace("[&hellip;]","");


                String heading = response.body().getTitle().getRendered();


                tvContent.setText(tempDetatils);
                tvHeading.setText(heading);
                getImageRetrofit();

            }

            @Override
            public void onFailure(Call<WPPost> call, Throwable t) {

            }
        });

    }


}
