package com.vk.sdk.api.methods;

import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;

/**
 * Created by admin on 16.11.2015.
 */
public class VKApiAccount extends VKApiBase {

    @Override
    protected String getMethodsGroup() {
        return "account";
    }

    public VKRequest setOnline (VKParameters params) {
        return prepareRequest("setOnline",params);
    }

    public VKRequest setOffline (VKParameters params) {
        return prepareRequest("setOffline",params);
    }
}
