package com.superior.labs.hindbhashi;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tuyenmonkey.mkloader.MKLoader;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeNewsFragment extends Fragment {


    private VerticalViewPager viewPager;
    private ArrayList<Model> wpList;
    private MKLoader loader;
    private ProgressBar progressBar;
    private PagesAdapter adapter;
    private String que="",image="";
    private String baseURL = "https://app.hindbhashi.com";


    public HomeNewsFragment() {
        // Required empty public constructor
    }
    public static HomeNewsFragment newInstance(String param1, String param2) {
        HomeNewsFragment fragment = new HomeNewsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_home_news, container, false);

        //progressBar = root.findViewById(R.id.myProgressBar);
        //    db = FirebaseDatabase.getInstance().getReference().child("News").child(loadLocale());
        viewPager = root.findViewById(R.id.news_pager);
        //  getNews();


        loader = root.findViewById(R.id.loadingNews);
        wpList = new ArrayList<>();


        adapter = new PagesAdapter(getFragmentManager(),wpList);
        viewPager.setAdapter(adapter);
        getRetrofit();
        return root;
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


                    String date = response.body().get(i).getDate().toString();
                    //Toast.makeText(getContext(), ""+date, Toast.LENGTH_SHORT).show();
                    que = response.body().get(i).getLinks().getWpFeaturedmedia().get(0).getHref();
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


                    wpList.add(new Model(Model.IMAGE_TYPE,response.body().get(i).getTitle().getRendered(),
                            tempDetatils,
                            que,date));


                    adapter.notifyDataSetChanged();
                }


                loader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<WPPost>> call, Throwable t) {

            }
        });

    }

    private String loadLocale(){
        SharedPreferences preferences = getActivity().getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String lang = preferences.getString("My_lang","");
        String newsLang = "English";
        if (lang.equals("hi"))
            newsLang="हिन्दी";
        else if (lang.equals("en"))
            newsLang="English";
        return newsLang;
    }
}
