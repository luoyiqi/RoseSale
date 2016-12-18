package com.wpl.rosesale.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by 培龙 on 2016/9/12.
 */
public class VolleyImageLoader {

    /**
     * 加载网络图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void setImageView(Context context, String url, final ImageView imageView) {
        RequestQueue queue = Volley.newRequestQueue(context);
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue.add(imageRequest);
    }

    public static void setBitmap(Context context, String url, final ImageView imageView) {
        RequestQueue queue = Volley.newRequestQueue(context);
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                TransitionDrawable td = new TransitionDrawable(
                        new Drawable[]{
                                new ColorDrawable(android.R.color.transparent),
                                new BitmapDrawable(bitmap)});
                td.setCrossFadeEnabled(true);
                imageView.setImageDrawable(td);
                td.startTransition(500);
//                imageView.setImageBitmap(bitmap);
            }
        }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        });
        queue.add(imageRequest);
    }

}
