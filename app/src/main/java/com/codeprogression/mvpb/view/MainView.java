package com.codeprogression.mvpb.view;

import javax.inject.Inject;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import com.codeprogression.mvpb.core.MyApplication;
import com.codeprogression.mvpb.core.MyRecyclerView;
import com.codeprogression.mvpb.core.RecylerViewAdapter;
import com.codeprogression.mvpb.databinding.MainViewBinding;
import com.codeprogression.mvpb.model.MainPresenter;
import com.codeprogression.mvpb.viewModel.ListItemViewModel;
import com.codeprogression.mvpb.viewModel.ListViewModel;

public class MainView extends RelativeLayout {

    @Inject
    MainPresenter presenter;
    private MainViewBinding binding;
    private ListViewModel viewModel;

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) { return; }
        MyApplication.get(getContext()).getAppComponent().inject(this);
        initViewModel();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isInEditMode()) { return; }

        binding = DataBindingUtil.bind(this);
        binding.setViewModel(viewModel);
        binding.setListener(this);

        final RecylerViewAdapter<ListItemViewModel> adapter = new RecylerViewAdapter<>(viewModel.listItemViewModels);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setOnScrolledToEndOfListListener(new MyRecyclerView.OnScrolledToEndOfListListener() {
            @Override public void onScrolledToBottomEnd(final MyRecyclerView myRecyclerView) {
                if (!adapter.isNewItemsLoadingInBackground() && viewModel.hasMore.get()){
                    adapter.setNewItemsLoadingInBackground(true);
                    loadMore();
                }

            }
        });

        presenter.attach(viewModel);
    }


    private void initViewModel() {
        if (viewModel == null) {
            viewModel = new ListViewModel();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.detach();
        binding.unbind();
    }

    public void performSearch(View v) {
        // Use presenter for domain operations
        presenter.searchIMDB(binding.queryEditText.getText().toString());
    }

    public void loadMore() {
        presenter.searchImdb(binding.queryEditText.getText().toString(), viewModel.pagesLoaded + 1);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        SavedState superState = new SavedState(super.onSaveInstanceState());

        /* Save view-specific data (e.g., repeating view positions)
         * Save viewModel defaults
         */

        return superState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        // update viewModel
        // update view-specific state
        viewModel = new ListViewModel();
    }

    static class SavedState extends View.BaseSavedState {

        public int number;

        SavedState(Parcelable superState) {
            super(superState);

        }

        private SavedState(Parcel in) {
            super(in);
            number = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(number);
        }

        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }

}
