package com.skywell.test;

import android.widget.ImageView;

import com.skywell.test.data.NewsInfo;
import com.skywell.test.data.api.RequestManager;
import com.skywell.test.ui.adapters.NewsAdapter;
import com.skywell.test.ui.images.LoadInfo;
import com.skywell.test.ui.images.LoadListViewImage;
import com.skywell.test.ui.presenters.PresenterFragmentNews;

import org.junit.Test;

import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SkywellUnitTest {

    @Test
    public void checkedNullLinkOnImageLoading(){
        ImageView mockImage = mock(ImageView.class);
        LoadListViewImage.validLoad(mockImage, (LoadInfo) null);
        LoadListViewImage.validLoad(mockImage, anyString());
    }

    @Test
    public void getNullNewsDataFromResponse(){
        PresenterFragmentNews presenterFragmentNews = mock(PresenterFragmentNews.class);
        RequestManager requestManager = mock(RequestManager.class);
        presenterFragmentNews.setRequestManager(requestManager);
        when(requestManager.getNewsInfo(anyString())).thenReturn(null);
        presenterFragmentNews.startLoadingNews(anyBoolean());
    }

    @Test
    public void setNullDataToAdapter(){
        mock(NewsAdapter.class).getItemId(anyInt());
    }

    @Test
    public void setSmallDataToAdapter(){
        NewsInfo newsInfo = mock(NewsInfo.class);
        NewsAdapter adapter = mock(NewsAdapter.class);
        adapter.setNewsInfo(newsInfo);
        adapter.getItemId(anyInt());
    }
}