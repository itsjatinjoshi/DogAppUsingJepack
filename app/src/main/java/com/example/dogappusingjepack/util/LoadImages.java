package com.example.dogappusingjepack.util;

import android.content.Context;
import android.media.Image;
import android.widget.ImageView;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dogappusingjepack.R;

public class LoadImages {

    public static void loadImages(ImageView image, String url, CircularProgressDrawable progressDrawable) {
        RequestOptions option = new RequestOptions()
                .placeholder(progressDrawable)
                .error(R.mipmap.dog_icon);
        Glide.with(image.getContext()).setDefaultRequestOptions(option).load(url).into(image);
    }


    public static CircularProgressDrawable getCircularProgressDrawable(Context context) {
        CircularProgressDrawable cpd = new CircularProgressDrawable(context);
        cpd.setStrokeWidth(10f);
        cpd.setCenterRadius(50f);
        cpd.start();
        return cpd;
    }
}
