package com.codeprogression.mvpb.core;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * This class adds OnScrolledToEndOfListListener and pauses ImageLoader during scrolling list (improved performance)
 */
public class MyRecyclerView extends RecyclerView {

    private OnScrollListener onScrollHelperListener;

    public MyRecyclerView(final Context context) {
        this(context, null);
    }

    public MyRecyclerView(final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    int pastVisiblesItems, visibleItemCount, totalItemCount;
    OnScrolledToEndOfListListener onScrolledToEndOfListListener;

    @Override public void setLayoutManager(final LayoutManager layout) {
        throw new UnsupportedOperationException("LinearLayoutManager is used in this custom view and it cannot be changed");
    }

    public MyRecyclerView(final Context context, @Nullable final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        super.setLayoutManager(layoutManager);
        onScrollHelperListener = new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        dispachScrolledToBottomEnd();
                        //Do pagination.. i.e. fetch new data
                    }
                }
            }
        };

        boolean pauseOnScroll = true;
        boolean pauseOnFling = false;
        PauseImageLoaderScrollListener listener = new PauseImageLoaderScrollListener(ImageLoader.getInstance(), pauseOnScroll,
                pauseOnFling);
        addOnScrollListener(listener);
    }

    private void dispachScrolledToBottomEnd() {
        if (onScrolledToEndOfListListener != null) {
            onScrolledToEndOfListListener.onScrolledToBottomEnd(this);
        }
    }

    public void setOnScrolledToEndOfListListener(
            final OnScrolledToEndOfListListener onScrolledToEndOfListListener) {
        this.onScrolledToEndOfListListener = onScrolledToEndOfListListener;
        if (onScrolledToEndOfListListener != null){
            addOnScrollListener(onScrollHelperListener);
        } else {
            removeOnScrollListener(onScrollHelperListener);
        }

    }

    public interface OnScrolledToEndOfListListener {

        void onScrolledToBottomEnd(MyRecyclerView myRecyclerView);
    }

    /**
     * Created by andrzej.biernacki on 2016-04-21.
     */
    public static class PauseImageLoaderScrollListener extends OnScrollListener {

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

            case SCROLL_STATE_IDLE:
                imageLoader.resume();
                break;
            case SCROLL_STATE_DRAGGING:
                if (pauseOnScroll) {
                    imageLoader.pause();
                }
                break;
            case SCROLL_STATE_SETTLING:
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
}
