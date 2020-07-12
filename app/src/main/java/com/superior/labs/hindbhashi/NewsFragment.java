package com.superior.labs.hindbhashi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tuyenmonkey.mkloader.MKLoader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {


    private ImageView ivNews;
    private String que="";
    private MKLoader loader;
    private String baseURL = "https://app.hindbhashi.com";
    TextView tvTitle,tvContent,tvAuthor;

    public NewsFragment() {
        // Required empty public constructor
    }

    /**
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_news, container, false);


        ivNews = root.findViewById(R.id.newsHomeImage);
        tvContent = root.findViewById(R.id.contentHome);
        tvTitle = root.findViewById(R.id.HeadingHome);
        tvAuthor = root.findViewById(R.id.authorHome);
        loader = root.findViewById(R.id.loadingNewsFrag);
        Bundle bundle = getArguments();
        que = bundle.getString("image");
        tvTitle.setText(bundle.getString("heading"));
        tvContent.setText(bundle.getString("content"));
        String date = bundle.getString("date");

        String time = date.substring(11,16)+" am";
        int t = Integer.parseInt(time.substring(0,2));
        if (t>12)
        {
            t=t-12;
            time=t+time.substring(2,6)+" pm";
        }

        date = date.substring(0,10);


        tvAuthor.setText("Posted by admin, "+date+" at "+time);

        que = que.substring(27);
        getImageRetrofit();


        return root;




    }

    public void getImageRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitImageApi service = retrofit.create(RetrofitImageApi.class);
        Call<WPImage> call = service.getImageInfo(que);
        call.enqueue(new Callback<WPImage>() {
            @Override
            public void onResponse(Call<WPImage> call, Response<WPImage> response) {
                loader.setVisibility(View.GONE);
                WPImage wpImage = response.body();
                String image = wpImage.getSourceUrl();
                Picasso.get().load(image).into(ivNews);
            }
            @Override
            public void onFailure(Call<WPImage> call, Throwable t) {

            }
        });

    }


}
