package com.macaria.app.utilities;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.macaria.app.R;

public class ImageHelper {

    public static void loadImage(Context context, String url, int holder, ImageView imageView) {
        imageView.setClipToOutline(true);
        if (url != null && !url.equals("")) {
            try {
                Glide.with(context)
                        .load( url)
                        .placeholder(R.drawable.custom_progress_bar)
                        .error(holder)
                        .into(imageView);
            } catch (Exception e) {
                Log.d("glide_load_image", "loadeImage: " + e);
            }
        } else {
            imageView.setImageResource(holder);
        }
    }
}
