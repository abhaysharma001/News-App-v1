package com.superior.labs.hindbhashi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tuyenmonkey.mkloader.MKLoader;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewCatActivity extends AppCompatActivity {

    private VerticalViewPager viewPager;
    private ArrayList<Model> wpList;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private PagesAdapter adapter;
    private ImageView ivCat;
    private TextView tvCat;
    private String que="";
    private MKLoader loader ;
    private int cat,TECH=5,SCIENCE=4,POLITICS=7,CORONA=3,WORLD=10,miscellaneous=11,ENTERTAINMENT=9,BUSINESS=6,SPORTS=12;
    private String baseURL = "https://app.hindbhashi.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cat);


        viewPager = findViewById(R.id.view_cat_pager);
        loader = findViewById(R.id.loadingViewCat);
        ivCat = findViewById(R.id.imgCat);
        tvCat = findViewById(R.id.textCat);

        toolbar = findViewById(R.id.viewPostToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);


        cat = Integer.parseInt(getIntent().getExtras().get("cat").toString());

        if (cat==TECH){
            ivCat.setImageResource(R.drawable.tech);
            tvCat.setText("प्रौद्योगिकी");
        }else if (cat==SCIENCE){
            ivCat.setImageResource(R.drawable.science);
            tvCat.setText("विज्ञान");
        }else if (cat==POLITICS){
            ivCat.setImageResource(R.drawable.politics);
            tvCat.setText("राजनीति");
        }else if (cat==CORONA){
            ivCat.setImageResource(R.drawable.virus);
            tvCat.setText("कोरोना");
        }else if (cat==ENTERTAINMENT){
            ivCat.setImageResource(R.drawable.entertainment);
            tvCat.setText("मनोरंजन");
        }else if (cat==BUSINESS){
            ivCat.setImageResource(R.drawable.buiness);
            tvCat.setText("व्यापार");
        }else if (cat==SPORTS){
            ivCat.setImageResource(R.drawable.sports);
            tvCat.setText("खेल");
        }


        wpList = new ArrayList<>();


        adapter = new PagesAdapter(getSupportFragmentManager(),wpList);
        viewPager.setAdapter(adapter);
        getRetrofit();

    }


    public void getRetrofit(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitArrayApi service = retrofit.create(RetrofitArrayApi.class);
        Call<List<WPPost>> call = service.getPostInfo();

        call.enqueue(new Callback<List<WPPost>>() {
            @Override
            public void onResponse(Call<List<WPPost>> call, Response<List<WPPost>> response) {
                //      progressBar.setVisibility(View.GONE);
                Log.e("MainActivity","Response"+response.body());
                for (int i=0;i<response.body().size();i++){
                    que = response.body().get(i).getLinks().getWpFeaturedmedia().get(0).getHref();
                    String date = response.body().get(i).getDate().toString();
                    //getImageRetrofit();
                    Log.e("MainActivity","Response"+response.body().get(i).getTitle());
                    String tempDetatils = response.body().get(i).getContent().getRendered().toString();
                    tempDetatils = tempDetatils.replace("<p>","");
                    tempDetatils = tempDetatils.replace("</p>","");
                    tempDetatils = tempDetatils.replace("<br/>","");
                    tempDetatils = tempDetatils.replace("<strong>","");
                    tempDetatils = tempDetatils.replace("</strong>","");
                    tempDetatils = tempDetatils.replace("<br />","");
                    tempDetatils = tempDetatils.replace("<br>","");
                    tempDetatils = tempDetatils.replace("[&hellip;]","");

                    if (Integer.parseInt(response.body().get(i).getCategories().get(0).toString())==cat) {
                        wpList.add(new Model(Model.IMAGE_TYPE, response.body().get(i).getTitle().getRendered(),
                                tempDetatils,
                                que,date));
                    }

                    adapter.notifyDataSetChanged();

                    loader.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<List<WPPost>> call, Throwable t) {

            }
        });

    }

}
