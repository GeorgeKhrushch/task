package com.skywell.test.ui.presenters;

import android.content.res.Resources;
import android.widget.Toast;

import com.skywell.test.R;
import com.skywell.test.data.NetCheck;
import com.skywell.test.data.OwnerDataItem;
import com.skywell.test.data.api.RequestManager;
import com.skywell.test.data.data_base.DBHelper;
import com.skywell.test.ui.fragments.FragmentProfile;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKUsersArray;

import java.util.ArrayList;

import rx.Subscriber;

public class PresenterFragmentProfile {

    private FragmentProfile mFragmentProfile;
    private RequestManager mRequestManager;
    private VKApiUserFull mOwner;
    private DBHelper mDBHelper;
    private ArrayList<OwnerDataItem> mOwnerData;

    public PresenterFragmentProfile(FragmentProfile fragmentProfile,
                                    RequestManager requestManager, DBHelper helper) {

        mFragmentProfile = fragmentProfile;
        mRequestManager = requestManager;
        mDBHelper = helper;
    }

    public void startLoadingProfileInfo(){
        if(!NetCheck.networkChecking(mFragmentProfile.getContext())){
            Toast.makeText(mFragmentProfile.getContext(),
                    R.string.internet_problem, Toast.LENGTH_SHORT).show();
            mOwner = mDBHelper.getUserById(Integer.parseInt(VKSdk.getAccessToken().userId));
            if(mOwner != null)
                setInfoToFragment();
            return;
        }

            mRequestManager
                    .getOwnerInfo()
                    .subscribe(new Subscriber<VKUsersArray>() {

                @Override
                public void onCompleted() {}

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onNext(VKUsersArray vkApiUserFulls) {
                    try {
                        mOwner = vkApiUserFulls.get(0);
                        mDBHelper.putOwner(mOwner);
                        setInfoToFragment();
                    }catch (IndexOutOfBoundsException | NullPointerException e){
                        e.printStackTrace();
                    }
                }
            });
    }

    private void setInfoToFragment(){
        fillInOwnerData();
        mFragmentProfile.setAdapter(mOwnerData);
        mFragmentProfile.setOwnerInfoInNavigation(mOwner);
    }

    private void fillInOwnerData(){
        mOwnerData = new ArrayList<>();
        Resources resources = mFragmentProfile.getResources();
        mOwnerData.add(new OwnerDataItem("photo_url", mOwner.photo_200));
        mOwnerData.add(new OwnerDataItem(resources.getString(R.string.owner_name), mOwner.name));

        addWithEmptyCheck(resources.getString(R.string.owner_status), mOwner.activity);
        addWithEmptyCheck(resources.getString(R.string.owner_birth), mOwner.bdate);

        if(mOwner.counters != null) {
            addWithEmptyCheck(resources.getString(R.string.owner_friends), mOwner.counters.friends);
            addWithEmptyCheck(resources.getString(R.string.owner_followers), mOwner.counters.followers);
            addWithEmptyCheck(resources.getString(R.string.owner_groups), mOwner.counters.groups);
            addWithEmptyCheck(resources.getString(R.string.owner_photos), mOwner.counters.photos);
        }

        addWithEmptyCheck(resources.getString(R.string.education), mOwner.university.name);
    }

    private void addWithEmptyCheck(String name, String value){
        if(!value.equals("")){
            mOwnerData.add(new OwnerDataItem(name, value));
        }
    }

    private void addWithEmptyCheck(String name, int counter){
        if(counter != 0)
            mOwnerData.add(new OwnerDataItem(name, String.valueOf(counter)));
    }
}
