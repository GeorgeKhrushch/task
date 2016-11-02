package com.skywell.test.ui.presenters;

import android.widget.Toast;

import com.skywell.test.R;
import com.skywell.test.data.NetCheck;
import com.skywell.test.data.NewsInfo;
import com.skywell.test.data.api.RequestManager;
import com.skywell.test.data.data_base.DBHelper;
import com.skywell.test.data.data_base.NewsData;
import com.skywell.test.data.format.DataUtils;
import com.skywell.test.ui.fragments.FragmentNews;

import rx.Subscriber;

public class PresenterFragmentNews {

    private DBHelper mDBHelper;
    private FragmentNews mFragmentNews;
    private RequestManager mRequestManager;
    private NewsInfo mNewsInfo;

    public PresenterFragmentNews(FragmentNews fragmentNews,
                                 RequestManager requestManager, DBHelper helper) {
        mFragmentNews = fragmentNews;
        mRequestManager = requestManager;
        mDBHelper = helper;
    }

    public void startLoadingNews(boolean refreshing){
        if(refreshing)
            mNewsInfo = null;

        if(!NetCheck.networkChecking(mFragmentNews.getContext())){
            Toast.makeText(mFragmentNews.getContext(),
                    R.string.internet_problem, Toast.LENGTH_SHORT).show();

            mNewsInfo = NewsData.restoreNewsInfo(mDBHelper);
            if(mNewsInfo != null) {
                mFragmentNews.setAdapter(mNewsInfo, refreshing);
            }
            return;
        }

        mRequestManager
                .getNewsInfo(mNewsInfo == null ? "" : mNewsInfo.getNewsNextMark())
                .subscribe(new Subscriber<NewsInfo>() {

                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(mFragmentNews.getContext(),
                                "Failed to load", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onNext(NewsInfo newsInfo) {
                        if(mNewsInfo == null)
                            mNewsInfo = newsInfo;
                        else
                            DataUtils.mergeNews(mNewsInfo, newsInfo);

                        mNewsInfo.getUsersAndGroups().setUpMaps();
                        NewsData.saveNewsInfo(mNewsInfo, mDBHelper);
                        mFragmentNews.setAdapter(mNewsInfo, refreshing);
                    }
                });
    }

    public void setRequestManager(RequestManager requestManager) {
        mRequestManager = requestManager;
    }
}
