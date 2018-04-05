package com.example.jason.newsportal.CustomImage;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.jason.newsportal.Util.AppUtil;

/**
 * Created by jason on 10/08/2017.
 */

public class CustomImageView extends ImageView {
    private Context context;
    public CustomImageView(Context context) {
        super(context);
        this.context=context;
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;

    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context=context;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width= AppUtil.getWidth(context);
        int requiredHeight=width*9/16;
//        super.onMeasure(widthMeasureSpec, requiredHeight);
        setMeasuredDimension(AppUtil.getWidth(context),requiredHeight);
    }


}
