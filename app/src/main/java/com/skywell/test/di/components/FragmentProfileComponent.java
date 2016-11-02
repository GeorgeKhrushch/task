package com.skywell.test.di.components;

import com.skywell.test.di.modules.FragmentProfileModule;
import com.skywell.test.di.scopes.FragmentProfileScope;
import com.skywell.test.ui.fragments.FragmentProfile;

import dagger.Subcomponent;

@FragmentProfileScope
@Subcomponent(modules = FragmentProfileModule.class)

public interface FragmentProfileComponent {
    void inject(FragmentProfile fragmentProfile);
}