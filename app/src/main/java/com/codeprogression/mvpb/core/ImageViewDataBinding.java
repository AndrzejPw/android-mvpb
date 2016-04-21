package com.codeprogression.mvpb.core;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Created by andrzej.biernacki on 2016-04-21.
 */
public class ImageViewDataBinding {

    @BindingAdapter("imageUri")
    public static void setImageUri(ImageView view, String uri){
        view.setTag(uri);
        view.setImageResource(0);
        ImageLoader.getInstance().displayImage(uri, view, new SimpleImageLoadingListener() {

            @Override
            public void onLoadingComplete(final String imageUri, final View view, final Bitmap loadedImage) {
                if (view.getTag().equals(imageUri)) {
                    ((ImageView) view).setImageBitmap(loadedImage);
                }
            }
        });
    }

}
