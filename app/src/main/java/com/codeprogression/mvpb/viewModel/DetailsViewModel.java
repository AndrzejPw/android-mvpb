package com.codeprogression.mvpb.viewModel;

import android.databinding.BaseObservable;
import android.databinding.ObservableInt;

/**
 * Created by andrzej.biernacki on 2016-04-12.
 */
public class DetailsViewModel extends BaseObservable {
    public final ObservableInt number = new ObservableInt();
}
