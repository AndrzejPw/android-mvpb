package com.codeprogression.mvpb.core;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.codeprogression.mvpb.R;

/**
 * Created by andrzej.biernacki on 2016-04-12.
 */
public class RecylerViewAdapter<VM> extends RecyclerView.Adapter<BindingViewHolder> {

    private final ObservableList<VM> listItemViewModels;
    private boolean newItemsLoadingInBackground;
    private int VIEW_TYPE_REGULAR = 0;
    private int VIEW_TYPE_LOADER = 1;

    public RecylerViewAdapter(final ObservableList<VM> listItemViewModels) {
        this.listItemViewModels = listItemViewModels;
        listItemViewModels.addOnListChangedCallback(new OnListChangedLister());
    }

    @Override public BindingViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        if (viewType == VIEW_TYPE_REGULAR) {
            final ViewDataBinding viewDataBinding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_view, parent, false);
            return new BindingViewHolder(viewDataBinding.getRoot());
        } else {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loader_row_view, parent, false);
            return new BindingViewHolder(view);
        }
    }

    @Override public void onBindViewHolder(final BindingViewHolder holder, final int position) {
        if (getItemViewType(position) == VIEW_TYPE_REGULAR) {
            holder.getBinding().setVariable(com.codeprogression.mvpb.BR.viewModel, listItemViewModels.get(position));
        }
    }

    @Override public int getItemViewType(final int position) {
        if (newItemsLoadingInBackground && position == listItemViewModels.size()) {
            return VIEW_TYPE_LOADER;
        }
        return VIEW_TYPE_REGULAR;
    }

    @Override public int getItemCount() {
        int numberOfLoaderRows = newItemsLoadingInBackground ? 1 : 0;
        return listItemViewModels.size() + numberOfLoaderRows;
    }

    public void setNewItemsLoadingInBackground(final boolean newItemsLoadingInBackground) {
        this.newItemsLoadingInBackground = newItemsLoadingInBackground;
        notifyItemInserted(listItemViewModels.size());
    }

    public boolean isNewItemsLoadingInBackground() {
        return newItemsLoadingInBackground;
    }

    private class OnListChangedLister extends ObservableList.OnListChangedCallback<ObservableList<VM>> {
        @Override public void onChanged(final ObservableList<VM> sender) {
            setNewItemsLoadingInBackground(false);
            notifyDataSetChanged();
        }

        @Override public void onItemRangeChanged(final ObservableList<VM> sender, final int positionStart, final int itemCount) {
            setNewItemsLoadingInBackground(false);
            for (int i = 0; i < itemCount; i++) {
                notifyItemChanged(i + positionStart);
            }
        }

        @Override public void onItemRangeInserted(final ObservableList<VM> sender, final int positionStart, final int itemCount) {
            setNewItemsLoadingInBackground(false);
            if (itemCount == 1) {
                notifyItemInserted(positionStart);
            } else {
                notifyDataSetChanged();
            }
        }

        @Override public void onItemRangeMoved(final ObservableList<VM> sender, final int fromPosition, final int toPosition,
                final int itemCount) {
            setNewItemsLoadingInBackground(false);
            if (itemCount == 1) {
                notifyItemMoved(fromPosition, toPosition);
            } else {
                notifyDataSetChanged();
            }
        }

        @Override public void onItemRangeRemoved(final ObservableList<VM> sender, final int positionStart, final int itemCount) {
            setNewItemsLoadingInBackground(false);
            if (itemCount == 1) {
                notifyItemRemoved(positionStart);
            } else {
                notifyDataSetChanged();
            }
        }
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
