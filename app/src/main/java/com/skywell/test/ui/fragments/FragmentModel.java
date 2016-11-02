package com.skywell.test.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

public abstract class FragmentModel extends Fragment {

    abstract void initiateInject();
    public void startLoading(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initiateInject();
    }

    protected void log(String message){
        Log.d(getClass().getSimpleName(), message);
    }
}
