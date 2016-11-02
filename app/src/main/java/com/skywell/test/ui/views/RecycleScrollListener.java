package com.skywell.test.ui.views;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.squareup.picasso.Picasso;

public abstract class RecycleScrollListener extends RecyclerView.OnScrollListener {

    public static final Object sPicassoTag = new Object();
    private int mLastVisibleItemPosition;
    private int mAdapterCount;
    private final ScrollBoundaryAction mScrollBoundaryAction;

    private final boolean mPauseWhenScroll;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean mRecyclerIsEmpty;
    public boolean mIsScrollingDown = true;

    public RecycleScrollListener(ScrollBoundaryAction scrollBoundaryAction,
                                 boolean scrollPause) {
        this.mScrollBoundaryAction = scrollBoundaryAction;
        mPauseWhenScroll = scrollPause;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if(!(recyclerView.getLayoutManager() instanceof LinearLayoutManager))
            return;
        mIsScrollingDown = dy > 0;
        if(mRecyclerView == null) {
            mRecyclerView = recyclerView;
            mLinearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            mRecyclerIsEmpty = recyclerView.getAdapter().getItemCount() == 0;
        }

        if(dy < 0 && isFirstItemDisplaying(recyclerView)){
            Log.d("Scroll", "TOP");
            mScrollBoundaryAction.topAction();
        }

        if(isLastItemDisplaying(recyclerView)){
            Log.d("Scroll", "BOTTOM");
            mScrollBoundaryAction.bottomAction();
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
        if(!mPauseWhenScroll)
            return;

        try{
            if (scrollState == RecyclerView.SCROLL_STATE_IDLE){
                Picasso.with(mRecyclerView.getContext()).resumeTag(sPicassoTag);
            } else {
                Picasso.with(mRecyclerView.getContext()).pauseTag(sPicassoTag);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (!mRecyclerIsEmpty) {
            int lastVisibleItemPosition = mLinearLayoutManager
                    .findLastVisibleItemPosition();
            if (lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1
                    && lastVisibleItemPosition != mLastVisibleItemPosition) {
                mLastVisibleItemPosition = lastVisibleItemPosition;
                Log.d("Scroll", "last position " + mLastVisibleItemPosition);
                return true;
            }
        }
        return false;
    }

    private boolean isFirstItemDisplaying(RecyclerView recyclerView){
        if(!mRecyclerIsEmpty){
            int firstVisiblePosition = mLinearLayoutManager.findFirstCompletelyVisibleItemPosition();
            if(firstVisiblePosition == 0
                    && recyclerView.getAdapter().getItemCount() != mAdapterCount){
                mAdapterCount = recyclerView.getAdapter().getItemCount();
                return true;
            }
        }
        return false;
    }
}
