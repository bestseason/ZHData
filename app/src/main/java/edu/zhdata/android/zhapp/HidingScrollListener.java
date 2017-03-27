package edu.zhdata.android.zhapp;

import android.widget.AbsListView;

/**
 * Created by pink2 on 2017/3/12.
 */

public abstract class HidingScrollListener implements AbsListView.OnScrollListener {
    private static final String TAG = "HidingScrollListener";
    private static final int HIDE_DISTANCE = 30;
    private boolean isToolbarVisible = true;
    private int firstVisibleItem;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
        if (isFirstItemVisible()) {
            onScrolled(0, 0);
        }
    }

    public void onScrolled(float dx,float dy){
        if (isFirstItemVisible()&& !isToolbarVisible) {
            onShow();
            isToolbarVisible = true;
        }else {
            if (dy > HIDE_DISTANCE && isToolbarVisible) {
                onHide();
                isToolbarVisible = false;
            }else if(dy < 0 && !isToolbarVisible){
                onShow();
                isToolbarVisible = true;
            }
        }
    }

    private boolean isFirstItemVisible(){
        return firstVisibleItem == 0;
    }

    public abstract void onHide();
    public abstract void onShow();
}
