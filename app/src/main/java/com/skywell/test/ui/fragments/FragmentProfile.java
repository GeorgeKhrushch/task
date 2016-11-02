package com.skywell.test.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.skywell.test.MainActivity;
import com.skywell.test.R;
import com.skywell.test.RxApplication;
import com.skywell.test.data.OwnerDataItem;
import com.skywell.test.di.modules.FragmentProfileModule;
import com.skywell.test.ui.adapters.OwnerInfoAdapter;
import com.skywell.test.ui.presenters.PresenterFragmentProfile;
import com.skywell.test.ui.views.RecycleDecorations;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.model.VKApiUserFull;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentProfile extends FragmentModel {

    @Inject
    PresenterFragmentProfile mPresenterFragmentProfile;

    @BindView(R.id.recyclerViewMain)
    RecyclerView mRecyclerView;

    @BindView(R.id.progressBarLoading)
    ProgressBar mProgressBar;

    @Override
    void initiateInject() {
        RxApplication.get(getContext())
                .getAppComponent()
                .plus(new FragmentProfileModule(this))
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, v);
        startLoading();
        return v;
    }

    @Override
    public void startLoading() {
        if(VKSdk.isLoggedIn())
            mPresenterFragmentProfile.startLoadingProfileInfo();
    }

    public void setAdapter(ArrayList<OwnerDataItem> ownerDataItems){
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new RecycleDecorations.DividerItemDecoration(getContext()));
        mRecyclerView.setAdapter(new OwnerInfoAdapter(ownerDataItems));
    }

    public void setOwnerInfoInNavigation(VKApiUserFull owner){
        ((MainActivity)getActivity()).setUpOwnerInfo(owner);
    }
}
