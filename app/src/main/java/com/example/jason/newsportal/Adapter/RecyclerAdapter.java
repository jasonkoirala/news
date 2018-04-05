package com.example.jason.newsportal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jason.newsportal.WebView.NewViewWebsite;
import com.example.jason.newsportal.Models.NewsModelDTO;
import com.example.jason.newsportal.R;
import com.example.jason.newsportal.listener.OnRecyclerviewItemClickListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import static android.view.View.GONE;

/**
 * Created by jason on 07/08/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<NewsModelDTO> newsModelDTOList;
    private ImageLoader imageLoader;
    private DisplayImageOptions displayImageOptions;
    private OnRecyclerviewItemClickListener itemClickListener;

    public RecyclerAdapter(Context context, List<NewsModelDTO> newsModelDTOList, OnRecyclerviewItemClickListener itemClickListener) {

        this.context = context;
        this.newsModelDTOList = newsModelDTOList;
        inflater = inflater.from(context);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        displayImageOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.news_pop, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final NewsModelDTO newsModelDTO = newsModelDTOList.get(position);
        holder.textViewNewsTitle.setText(newsModelDTO.getTitle());
        holder.textViewNewsSource.setText(newsModelDTO.getSource());
        imageLoader.displayImage(newsModelDTO.getImageURL(), holder.newsImage, displayImageOptions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.textViewNewsDescription.setText(Html.fromHtml(newsModelDTO.getDescription(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            holder.textViewNewsDescription.setText(Html.fromHtml(newsModelDTO.getDescription()));
        }
////        holder.expander.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
////                    if (holder.textViewNewsDescription.getMaxLines() == 2) {
////                        holder.textViewNewsDescription.setMaxLines(Integer.MAX_VALUE);
////                    } else {
////                        holder.textViewNewsDescription.setMaxLines(2);
////                    }
////                }
////            }
////        });

//
//        ViewTreeObserver viewTreeObserver = holder.textViewNewsDescription.getViewTreeObserver();
//        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                if (holder.textViewNewsDescription.getLineCount() < 3) {
//                    holder.expander.setVisibility(GONE);
//                } else {
//                    holder.expander.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//
//        holder.textViewNewsDescription.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                Intent in = new Intent(context, NewViewWebsite.class);
//                Bundle b = new Bundle();
//                b.putString("NewsSource", newsModelDTO.getArticleURL().toString());
//                in.putExtras(b);
//                context.startActivity(in);
//            }
//        });
        //holder.textViewDate.setText(newsModelDTO.getDate());
        final int tempPosition = position;
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClicked(tempPosition);
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return newsModelDTOList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        private TextView textViewNewsTitle, textViewNewsSource, textViewNewsDescription;

        private ImageView expander, newsImage;
        private View parent;

        public ViewHolder(View itemView) {

            super(itemView);
            parent = itemView;
            textViewNewsTitle = (TextView) itemView.findViewById(R.id.newsHeading);
            textViewNewsSource = (TextView) itemView.findViewById(R.id.newsSource);
            textViewNewsDescription = (TextView) itemView.findViewById(R.id.newsDescription);
            //expander = (ImageView) itemView.findViewById(R.id.expander);
            newsImage = (ImageView) itemView.findViewById(R.id.newsImage);
            //textViewDate = (TextView) itemView.findViewById(R.id.newsDate);

        }
    }


}
