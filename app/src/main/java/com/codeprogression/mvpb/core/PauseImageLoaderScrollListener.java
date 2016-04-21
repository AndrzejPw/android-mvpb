package com.codeprogression.mvpb.core;

import android.support.v7.widget.RecyclerView;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by andrzej.biernacki on 2016-04-21.
 */
public class PauseImageLoaderScrollListener extends RecyclerView.OnScrollListener {

    private final ImageLoader imageLoader;
    private final boolean pauseOnScroll;
    private final boolean pauseOnFling;

    public PauseImageLoaderScrollListener(ImageLoader imageLoader, boolean pauseOnScroll, boolean pauseOnFling) {
        this.imageLoader = imageLoader;
        this.pauseOnScroll = pauseOnScroll;
        this.pauseOnFling = pauseOnFling;
    }

    @Override public void onScrollStateChanged(final RecyclerView recyclerView, final int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        switch (newState) {

        case RecyclerView.SCROLL_STATE_IDLE:
            imageLoader.resume();
            break;
        case RecyclerView.SCROLL_STATE_DRAGGING:
            if (pauseOnScroll) {
                imageLoader.pause();
            }
            break;
        case RecyclerView.SCROLL_STATE_SETTLING:
            if (pauseOnFling) {
                imageLoader.pause();
            }
            break;
        }
    }

    @Override public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }
}
