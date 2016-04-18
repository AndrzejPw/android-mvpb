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

    public RecylerViewAdapter(final ObservableList<VM> listItemViewModels) {
        this.listItemViewModels = listItemViewModels;
        listItemViewModels.addOnListChangedCallback(new OnListChangedLister());
        setHasStableIds(true);
    }

    @Override public BindingViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final ViewDataBinding viewDataBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_view, parent, false);
        return new BindingViewHolder(viewDataBinding.getRoot());
    }

    @Override public void onBindViewHolder(final BindingViewHolder holder, final int position) {
        holder.getBinding().setVariable(com.codeprogression.mvpb.BR.viewModel, listItemViewModels.get(position));
        holder.getBinding().executePendingBindings();
    }

    @Override public int getItemCount() {
        return listItemViewModels.size();
    }

    private class OnListChangedLister extends ObservableList.OnListChangedCallback<ObservableList<VM>> {
        @Override public void onChanged(final ObservableList<VM> sender) {
            notifyDataSetChanged();
        }

        @Override public void onItemRangeChanged(final ObservableList<VM> sender, final int positionStart, final int itemCount) {
            for (int i = 0; i < itemCount; i++) {
                notifyItemChanged(i + positionStart);
            }
        }

        @Override public void onItemRangeInserted(final ObservableList<VM> sender, final int positionStart, final int itemCount) {
            for (int i = 0; i < itemCount; i++) {
                notifyItemInserted(positionStart + i);
            }
        }

        @Override public void onItemRangeMoved(final ObservableList<VM> sender, final int fromPosition, final int toPosition,
                final int itemCount) {
            for (int i = 0; i < itemCount; i++) {
                notifyItemMoved(i + fromPosition, i + toPosition);
            }
        }

        @Override public void onItemRangeRemoved(final ObservableList<VM> sender, final int positionStart, final int itemCount) {
            for (int i = 0; i < itemCount; i++) {
                notifyItemRemoved(i + positionStart);
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
