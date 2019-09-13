package com.example.imagegallery.model;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;


public class ImgurAPI {
    private static final String API = "https://api.imgur.com";
    private static PrivateApi privateApi;

    static {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(API)
                .client(client)
                .build();

        privateApi = retrofit.create(PrivateApi.class);

    }
    public static Observable<ImageModel> getFactsByImage() {
        return privateApi.getFactsByImage();
    }


    public interface PrivateApi {
        @GET("/3/upload")
        Observable<ImageModel> getFactsByImage();
    }

}
