package com.skywell.test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.skywell.test.data.api.Authorization;
import com.skywell.test.data.data_base.DBHelper;
import com.skywell.test.di.modules.MainActivityModule;
import com.skywell.test.ui.fragments.FragmentModel;
import com.skywell.test.ui.fragments.FragmentNews;
import com.skywell.test.ui.fragments.FragmentProfile;
import com.skywell.test.ui.images.LoadListViewImage;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.model.VKApiUserFull;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Inject
    Authorization mAuthorization;

    @Inject
    DBHelper mDBHelper;

    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setTitle(R.string.profile);
        setUpNavigation(toolbar);

        RxApplication.get(this)
                .getAppComponent()
                .plus(new MainActivityModule(this))
                .inject(this);
    }

    private void setUpNavigation(Toolbar toolbar) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        setStartFragment();
    }

    public void setUpOwnerInfo(VKApiUserFull owner){
        new Handler().postDelayed(() -> {
            ((TextView)navigationView.findViewById(R.id.textViewOwnerName)).setText(owner.name);
            ((TextView)navigationView.findViewById(R.id.textViewOwnerStatus)).setText(owner.activity);
            LoadListViewImage.validLoad(((ImageView) navigationView.findViewById(R.id.imageViewAvatar)),
                    owner.photo_100);
        }, 500);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        String tag = "";
        int sectionNameId = 0;

        switch (id){
            case R.id.nav_profile:{
                fragment = new FragmentProfile();
                tag = Constants.FRAGMENT_PROFILE_TAG;
                sectionNameId = R.string.profile;
                break;
            }

            case R.id.nav_news:{
                fragment = new FragmentNews();
                tag = Constants.FRAGMENT_NEWS_TAG;
                sectionNameId = R.string.news;
                break;
            }

            case R.id.nav_logout:{
                logout();
                break;
            }
        }

        if(fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment, tag)
                    .commit();
            toolbar.setTitle(sectionNameId);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout(){
        mDBHelper.clearAll();
        VKSdk.logout();
        mAuthorization.startAuthentication();
        new Handler().postDelayed(() -> setStartFragment(), 400);
    }

    private void setStartFragment(){
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                Log.d("Logged in", res.userId + " id");
                startLoadingProfile();
            }

            @Override
            public void onError(VKError error) {
                MainActivity.this.finish();
                Log.d("ERROR",  error.errorCode + " code");
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void startLoadingProfile() {
        FragmentModel fragment = (FragmentModel) getSupportFragmentManager()
                .findFragmentByTag(Constants.FRAGMENT_PROFILE_TAG);
        if(fragment != null)
            fragment.startLoading();
    }
}
