package com.codeprogression.mvpb;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by andrzej.biernacki on 2016-04-12.
 */
public class Adapter extends RecyclerView.Adapter<BindingViewHolder> {

    private final ObservableArrayList<ListItemViewModel> listItemViewModels;

    public Adapter(final ObservableArrayList<ListItemViewModel> listItemViewModels) {
        this.listItemViewModels = listItemViewModels;
    }

    @Override public BindingViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final ViewDataBinding viewDataBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_view, parent, false);
        return new BindingViewHolder(viewDataBinding.getRoot());
    }

    @Override public void onBindViewHolder(final BindingViewHolder holder, final int position) {
        holder.getBinding().setVariable(com.codeprogression.mvpb.BR.viewModel, listItemViewModels.get(position));
    }

    @Override public int getItemCount() {
        return listItemViewModels.size();
    }
}

class BindingViewHolder extends RecyclerView.ViewHolder {
    public BindingViewHolder(View itemView) {
        super(itemView);
    }

    public ViewDataBinding getBinding() {
        return DataBindingUtil.getBinding(itemView);
    }
}
