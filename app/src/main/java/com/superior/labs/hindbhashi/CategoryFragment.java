package com.superior.labs.hindbhashi;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryFragment extends Fragment {

    private ArrayList<NewsModel> wpList,coronaList,enterList,techList;

    private ShimmerFrameLayout shimmerContainer,shimmerContainer2;
    private CardView cardShimmer,cardShimmer2,cardTech,cardEntertainment,cardBusiness,cardPolitics,cardSports,cardScience,cardUttarakhand;
    private int TECH=5,SCIENCE=4,POLITICS=7,CORONA=3,WORLD=10,miscellaneous=11,ENTERTAINMENT=9,BUSINESS=6,SPORTS=12;
    private RecyclerView trendingRecyclerView,coronaRecyclerView,entertainmentRecyclerView,techRecyclerView;
    private NewsItemAdapter newsItemAdapter,coronaAdapter;
    private VerticalNewsItemAdapter entertainmentAdapter,techAdapter;
    private String baseURL = "https://app.hindbhashi.com";


    public CategoryFragment() {
        // Required empty public constructor
    }
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category, container, false);


        shimmerContainer = view.findViewById(R.id.shimmer_view_container);
        shimmerContainer2 = view.findViewById(R.id.shimmer_view_container2);
        trendingRecyclerView = view.findViewById(R.id.trendingRecyclerView);
        entertainmentRecyclerView = view.findViewById(R.id.entertainmentRecyclerView);
        coronaRecyclerView = view.findViewById(R.id.coronaRecyclerView);
        techRecyclerView = view.findViewById(R.id.techRecyclerView);
        cardBusiness = view.findViewById(R.id.cardBusiness);
        cardTech = view.findViewById(R.id.cardTech);
        cardScience = view.findViewById(R.id.cardScience);
        cardSports = view.findViewById(R.id.cardSports);
        cardEntertainment = view.findViewById(R.id.cardEntertainment);
        cardPolitics = view.findViewById(R.id.cardPolitics);
        cardUttarakhand = view.findViewById(R.id.cardUttarakhand);






        wpList = new ArrayList<>();
        coronaList = new ArrayList<>();
        enterList = new ArrayList<>();
        techList = new ArrayList<>();

        cardShimmer = view.findViewById(R.id.cardShimmer);
        cardShimmer2 = view.findViewById(R.id.cardShimmer2);

        YouTubePlayerView youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        newsItemAdapter = new NewsItemAdapter(getContext(),wpList);
        LinearLayoutManager layoutManager  =new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        trendingRecyclerView.setLayoutManager(layoutManager);
        trendingRecyclerView.setAdapter(newsItemAdapter);

        coronaAdapter = new NewsItemAdapter(getContext(),coronaList);
        LinearLayoutManager layoutManager2  =new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        coronaRecyclerView.setLayoutManager(layoutManager2);
        coronaRecyclerView.setAdapter(coronaAdapter);

        entertainmentAdapter = new VerticalNewsItemAdapter(getContext(),enterList);
        LinearLayoutManager entertainLayout  =new LinearLayoutManager(getActivity());
        entertainmentRecyclerView.setLayoutManager(entertainLayout);
        entertainmentRecyclerView.setAdapter(entertainmentAdapter);


        techAdapter = new VerticalNewsItemAdapter(getContext(),techList);
        LinearLayoutManager techLayout  =new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        techRecyclerView.setLayoutManager(techLayout);
        techRecyclerView.setAdapter(techAdapter);


        CardView cvCorona = view.findViewById(R.id.cardCorona);
        cvCorona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),ViewCatActivity.class);
                i.putExtra("cat",CORONA);
                startActivity(i);
            }
        });
        cardPolitics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),ViewCatActivity.class);
                i.putExtra("cat",POLITICS);
                startActivity(i);

            }
        });

        cardTech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),ViewCatActivity.class);
                i.putExtra("cat",TECH);
                startActivity(i);

            }
        });

        cardSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),ViewCatActivity.class);
                i.putExtra("cat",SPORTS);
                startActivity(i);

            }
        });

        cardBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),ViewCatActivity.class);
                i.putExtra("cat",BUSINESS);
                startActivity(i);

            }
        });


        cardEntertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),ViewCatActivity.class);
                i.putExtra("cat",ENTERTAINMENT);
                startActivity(i);

            }
        });


        cardScience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),ViewCatActivity.class);
                i.putExtra("cat",SCIENCE);
                startActivity(i);

            }
        });

        getRetrofit();



        return view;
    }

    public void getRetrofit(){

        shimmerContainer.startShimmer();
        shimmerContainer2.startShimmer();

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
                shimmerContainer.stopShimmer();
                shimmerContainer.hideShimmer();
                cardShimmer.setVisibility(View.GONE);

                shimmerContainer2.stopShimmer();
                shimmerContainer2.hideShimmer();
                cardShimmer2.setVisibility(View.GONE);
                Log.e("MainActivity","Response"+response.body());
                for (int i=0;i<response.body().size();i++){

                    String que = response.body().get(i).getLinks().getWpFeaturedmedia().get(0).getHref();
                    String id = response.body().get(i).getId().toString();
                    //getImageRetrofit();
                    Log.e("MainActivity","Response"+response.body().get(i).getTitle());

                    if (Integer.parseInt(response.body().get(i).getCategories().get(0).toString())==ENTERTAINMENT) {
                        enterList.add(new NewsModel(que, response.body().get(i).getTitle().getRendered().toString(),id));
                    }else if (Integer.parseInt(response.body().get(i).getCategories().get(0).toString())==CORONA) {
                        coronaList.add(new NewsModel(que, response.body().get(i).getTitle().getRendered().toString(),id));
                    }else if (Integer.parseInt(response.body().get(i).getCategories().get(0).toString())==miscellaneous) {
                        wpList.add(new NewsModel(que, response.body().get(i).getTitle().getRendered().toString(),id));
                    }else if (Integer.parseInt(response.body().get(i).getCategories().get(0).toString())==TECH) {
                        techList.add(new NewsModel(que, response.body().get(i).getTitle().getRendered().toString(),id));
                    }
                }


                newsItemAdapter.notifyDataSetChanged();
                coronaAdapter.notifyDataSetChanged();
                entertainmentAdapter.notifyDataSetChanged();
                techAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<WPPost>> call, Throwable t) {

            }
        });

    }


}
