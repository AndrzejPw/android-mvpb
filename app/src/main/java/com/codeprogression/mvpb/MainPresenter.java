package com.codeprogression.mvpb;

import javax.inject.Inject;

import com.codeprogression.mvpb.core.BaseBindingPresenter;
import com.codeprogression.mvpb.core.PerActivity;

@PerActivity
public class MainPresenter extends BaseBindingPresenter<ListViewModel> {

    @Inject
    public MainPresenter() {
    }

    @Override
    protected void load() {
        if (getViewModel().number.get() < 10) {
            getViewModel().number.set(10);
        }

    }


    public void add(){
        int number = getViewModel().number.get() + 1;
        getViewModel().number.set(number);
    }

}