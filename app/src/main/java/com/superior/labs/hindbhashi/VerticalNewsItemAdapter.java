package com.superior.labs.hindbhashi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;
import com.tuyenmonkey.mkloader.MKLoader;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VerticalNewsItemAdapter extends RecyclerView.Adapter<VerticalNewsItemAdapter.VerticalNewsItemViewHolder>{


    private Context mContext;
    private ArrayList<NewsModel> list;
    private String que;
    private ImageView imageView;
    private String baseURL = "https://app.hindbhashi.com";

    public VerticalNewsItemAdapter(Context mContext, ArrayList<NewsModel> list) {
        this.mContext = mContext;
        this.list = list;
    }


    @NonNull
    @Override
    public VerticalNewsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.vertical_list_item,parent,false);

        return new VerticalNewsItemAdapter.VerticalNewsItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final VerticalNewsItemViewHolder holder, final int position) {



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitImageApi service = retrofit.create(RetrofitImageApi.class);
        Call<WPImage> call = service.getImageInfo(list.get(position).getImage());
        call.enqueue(new Callback<WPImage>() {
            @Override
            public void onResponse(Call<WPImage> call, Response<WPImage> response) {
                WPImage wpImage = response.body();
                String img = wpImage.getSourceUrl();
                Picasso.get().load(img).into(holder.ivNews);
                holder.loader.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<WPImage> call, Throwable t) {

            }
        });


        //      getImageRetrofit();
        holder.tvHead.setText(list.get(position).getHeading());
        holder.ivNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ViewPostActivity.class);
                intent.putExtra("id",list.get(position).getId());
                mContext.startActivity(intent);
            }
        });
        holder.tvHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ViewPostActivity.class);
                intent.putExtra("id",list.get(position).getId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VerticalNewsItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivNews;
        private TextView tvHead;
        private MKLoader loader;
        public VerticalNewsItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivNews = itemView.findViewById(R.id.newsItemVImage);
            tvHead = itemView.findViewById(R.id.newsItemVText);
            loader = itemView.findViewById(R.id.loadingNewsVItem);


        }
    }
}
