package com.skywell.test.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dinuscxj.refresh.RecyclerRefreshLayout;
import com.skywell.test.R;
import com.skywell.test.RxApplication;
import com.skywell.test.data.NewsInfo;
import com.skywell.test.di.modules.FragmentNewsModule;
import com.skywell.test.ui.adapters.NewsAdapter;
import com.skywell.test.ui.presenters.PresenterFragmentNews;
import com.skywell.test.ui.views.RecycleScrollListener;
import com.skywell.test.ui.views.ScrollBoundaryAction;
import com.vk.sdk.VKSdk;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentNews extends FragmentModel implements ScrollBoundaryAction{

    @Inject
    PresenterFragmentNews mPresenterFragmentNews;

    @BindView(R.id.recyclerViewNews)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayoutNews)
    RecyclerRefreshLayout mRecyclerRefreshLayout;

    @Override
    void initiateInject() {
        RxApplication.get(getContext())
                .getAppComponent()
                .plus(new FragmentNewsModule(this))
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, v);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerRefreshLayout.setOnRefreshListener(() -> loadNewsInfo(true));
        startLoading();
        return v;
    }

    @Override
    public void startLoading() {
        loadNewsInfo(false);
    }

    private void loadNewsInfo(boolean refreshing) {
        if(VKSdk.isLoggedIn())
            mPresenterFragmentNews.startLoadingNews(refreshing);
    }

    public void setAdapter(NewsInfo newsInfo, boolean refreshing){
        mRecyclerRefreshLayout.setRefreshing(false);
        if(mRecyclerView.getAdapter() == null || refreshing) {
            RecycleScrollListener listener = new RecycleScrollListener(this, true) {};
            mRecyclerView.setOnScrollListener(listener);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setAdapter(new NewsAdapter(getContext(), newsInfo, listener));
        }else{
            ((NewsAdapter)mRecyclerView.getAdapter()).setNewsInfo(newsInfo);
            mRecyclerView.getAdapter().notifyDataSetChanged();
            mRecyclerView.smoothScrollBy(0, 50);
        }
    }

    @Override
    public void topAction() {
        log("TOP");
    }

    @Override
    public void bottomAction() {
        log("BOTTOM");
        loadNewsInfo(false);
    }
}
