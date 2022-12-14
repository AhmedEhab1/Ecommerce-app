package com.macaria.app.utilities;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.macaria.app.R;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ImageHelper {

    public static void loadImage(Context context, String url, int holder, ImageView imageView) {
        imageView.setClipToOutline(true);
        if (url != null && !url.equals("")) {
            try {
                CircularProgressDrawable drawable = new CircularProgressDrawable(context);
                drawable.setColorSchemeColors(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
                drawable.setCenterRadius(30f);
                drawable.setStrokeWidth(5f);
                drawable.start();

                Glide.with(context)
                        .load( url)
                        .placeholder(drawable)
                        .error(holder)
                        .into(imageView);
            } catch (Exception e) {
                Log.d("glide_load_image", "loadeImage: " + e);
            }
        } else {
            imageView.setImageResource(holder);
        }
    }

    public static MultipartBody.Part getMultipartBodyImage(File imageFile, String key) {
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData(key, imageFile.getName(), reqFile);
        return body;
    }
}
