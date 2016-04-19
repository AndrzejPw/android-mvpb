package com.codeprogression.mvpb.model;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import javax.inject.Inject;
import java.util.ArrayList;

import android.content.Context;
import com.codeprogression.mvpb.core.BaseBindingPresenter;
import com.codeprogression.mvpb.viewModel.ListItemViewModel;
import com.codeprogression.mvpb.viewModel.ListViewModel;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

public class MainPresenter extends BaseBindingPresenter<ListViewModel> {


    IMDBService imdbService;
    Context context;

    @Inject
    public MainPresenter(IMDBService imdbService, Context context) {
        this.imdbService = imdbService;
        this.context = context;
    }

    @Override
    protected void load() {
    }


    public void searchIMDB(final String query) {
        if (Strings.isNullOrEmpty(query) || query.length() < 3) {
            getViewModel().listItemViewModels.clear();
            return;
        }
        Call<IMDBResponse> call = imdbService.searchIMDB(query);

        call.enqueue(new Callback<IMDBResponse>() {
            @Override public void onResponse(final Call<IMDBResponse> call, final Response<IMDBResponse> response) {
                processIMDBResponse(response.body());
            }

            @Override public void onFailure(final Call<IMDBResponse> call, final Throwable t) {
                Timber.e("call to imdb with parameter %s failed", query);
            }
        });

        Timber.d("IMDB called");
    }

    void processIMDBResponse(IMDBResponse imdbResponse){
        //        getViewModel().listItemViewModels.clear();
        final ArrayList<ListItemViewModel> tmpList = Lists.newArrayList();
        for (ImdbRecord record : imdbResponse.Search) {
            tmpList.add(new ListItemViewModel(record.Title));
        }
        getViewModel().listItemViewModels.addAll(tmpList);
        if (imdbResponse.totalResults > getViewModel().listItemViewModels.size()) {
            getViewModel().hasMore.set(true);
        }
    }

    public void loadMore() {

    }
}