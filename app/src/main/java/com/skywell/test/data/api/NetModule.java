package com.skywell.test.data.api;

import com.skywell.test.data.VkService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    @Provides
    @Singleton
    public OkHttpClient getOkhttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        return httpClient.build();
    }

    @Provides
    @Singleton
    public Retrofit getRetrofit(OkHttpClient client){
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.create();
        return new Retrofit.Builder()
                .baseUrl("https://api.vk.com/method/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(rxAdapter)
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    public VkService getVkService(Retrofit retrofit){
        return retrofit.create(VkService.class);
    }

    @Provides
    @Singleton
    public RequestManager getUserManager(VkService service){
        return new RequestManager(service);
    }
}
