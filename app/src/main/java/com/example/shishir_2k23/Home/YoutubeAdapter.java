package com.example.shishir_2k23.Home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shishir_2k23.R;

import java.util.ArrayList;

public class YoutubeAdapter extends RecyclerView.Adapter<YoutubeAdapter.YoutuveViewHolder> {

    ArrayList<YoutubeDataModel> arrayList;
    Context context;

    public YoutubeAdapter(ArrayList<YoutubeDataModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public YoutuveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.youtube_item,parent,false);
        return new YoutuveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YoutuveViewHolder holder, int position) {
        YoutubeDataModel model = arrayList.get(position);
        holder.webView.loadUrl(model.getLink());
        holder.youtubeapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(model.getLink()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class YoutuveViewHolder extends RecyclerView.ViewHolder{
        WebView webView;
        ImageView youtubeapp;
        public YoutuveViewHolder(View view){
            super(view);
            webView = view.findViewById(R.id.youtube_view);
            youtubeapp = view.findViewById(R.id.youtube_app);
            webView.setWebViewClient(new WebViewClient());
            webView.setWebChromeClient(new WebChromeClient());
            webView.getSettings().setJavaScriptEnabled(true);
        }
    }
}
