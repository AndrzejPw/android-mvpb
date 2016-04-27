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
        getViewModel().reset();
        searchImdb(query, 0);
    }

    void processIMDBResponse(IMDBResponse imdbResponse){
        final ArrayList<ListItemViewModel> tmpList = Lists.newArrayList();
        for (ImdbRecord record : imdbResponse.Search) {
            tmpList.add(new ListItemViewModel(record.Title, record.Poster));
        }
        getViewModel().listItemViewModels.addAll(tmpList);
        if (imdbResponse.totalResults > getViewModel().listItemViewModels.size()) {
            getViewModel().hasMore.set(true);
        } else {
            getViewModel().hasMore.set(false);
        }
        getViewModel().pagesLoaded++;
    }

    public void searchImdb(final String query, final int pageNumber) {
        if (Strings.isNullOrEmpty(query) || query.length() < 3) {
            getViewModel().listItemViewModels.clear();
            return;
        }
        Call<IMDBResponse> call;
        if (pageNumber == 0) {
            call = imdbService.searchIMDB(query);
        } else {
            call = imdbService.searchIMDB(query, pageNumber);
        }

        call.enqueue(new Callback<IMDBResponse>() {
            @Override public void onResponse(final Call<IMDBResponse> call, final Response<IMDBResponse> response) {
                processIMDBResponse(response.body());
            }

            @Override public void onFailure(final Call<IMDBResponse> call, final Throwable t) {
                Timber.e("call to imdb with parameter query=%s, page=%s failed", query, pageNumber);
            }
        });
        Timber.d("IMDB called with parameter query=%s, page=%s", query, pageNumber);


    }

}