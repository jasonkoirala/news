package com.example.jason.newsportal.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jason.newsportal.R;
import com.example.jason.newsportal.Models.SportsNewsModel;
import com.example.jason.newsportal.listener.OnRecyclerviewItemClickListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by jason on 14/08/2017.
 */

public class RecylerSportsAdapter extends RecyclerView.Adapter<RecylerSportsAdapter.SportsViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private ImageLoader mImageLoader;
    List<SportsNewsModel> msportsNewsModelList;
    private DisplayImageOptions displayImageOptions;
    private OnRecyclerviewItemClickListener itemClickListener;
    public RecylerSportsAdapter(Context context, List<SportsNewsModel> msportsNewsModelList, OnRecyclerviewItemClickListener itemClickListener) {
        this.context = context;
        this.msportsNewsModelList = msportsNewsModelList;
        inflater = inflater.from(context);
        mImageLoader=ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(context));
        displayImageOptions=new DisplayImageOptions.Builder().build();
        this.itemClickListener = itemClickListener;

    }

    @Override
    public SportsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_one_sports_model,parent , false);
        return new SportsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SportsViewHolder holder, int position) {

        final SportsNewsModel sportsNewsModel = msportsNewsModelList.get(position);
        holder.textsportsNewsTitle.setText(sportsNewsModel.getSportsNewsHeading());
        holder.textsportsNewsSource.setText(sportsNewsModel.getSportsNewsSource());
        holder.textsportsNewsDesc.setText(sportsNewsModel.getSportNewsDescription());
        mImageLoader.displayImage(sportsNewsModel.getNewsSportsImageURL(), holder.newsSportsImage, displayImageOptions);
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
        return msportsNewsModelList.size();
    }

    class SportsViewHolder extends RecyclerView.ViewHolder {

        private TextView textsportsNewsTitle, textsportsNewsDesc, textsportsNewsSource;
        private ImageView newsSportsImage;
        private View parent;

        public SportsViewHolder(View itemView) {
            super(itemView);
            parent = itemView;
            textsportsNewsTitle = (TextView)itemView.findViewById(R.id.newsSportsHeading);
            textsportsNewsSource = (TextView)itemView.findViewById(R.id.newsSportsSource);
            textsportsNewsDesc = (TextView)itemView.findViewById(R.id.newsSportsDescription);
            newsSportsImage = (ImageView)itemView.findViewById(R.id.newsSportsImage);

        }
    }
}
