package com.codeprogression.mvpb.viewModel;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;

public class ListViewModel extends BaseObservable {
    public final ObservableArrayList<ListItemViewModel> listItemViewModels = new ObservableArrayList<>();

    public final ObservableBoolean hasMore = new ObservableBoolean();
    public int pagesLoaded;

    public void reset() {
        pagesLoaded = 0;
        hasMore.set(false);
        listItemViewModels.clear();
    }
}
