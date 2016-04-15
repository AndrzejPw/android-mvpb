package com.codeprogression.mvpb.viewModel;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableInt;

public class ListViewModel extends BaseObservable {
    public final ObservableArrayList<ListItemViewModel> listItemViewModels = new ObservableArrayList<>();

    public final ObservableInt number = new ObservableInt();

}
