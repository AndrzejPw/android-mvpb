package com.codeprogression.mvpb.viewModel;

import android.support.annotation.Nullable;

/**
 * Created by andrzej.biernacki on 2016-04-12.
 */
public class ListItemViewModel {
    public final String title;
    @Nullable
    public final String imageUri;

    public ListItemViewModel(final String title, final String imageUri) {
        this.title = title;
        this.imageUri = imageUri;
    }
}
