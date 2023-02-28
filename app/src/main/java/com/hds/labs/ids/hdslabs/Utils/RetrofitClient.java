package com.hds.labs.ids.hdslabs.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hds.labs.ids.hdslabs.Interface.ApiCall;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://admin.hds-labs.com/";
    private static RetrofitClient client;
    private static Retrofit retrofit = null;
    private static final int TIME_OUT = 3000;



    private RetrofitClient() {
        if (retrofit == null) {
            OkHttpClient.Builder c = new OkHttpClient.Builder();
            c.callTimeout(2, TimeUnit.MINUTES);
            c.connectTimeout(3, TimeUnit.MINUTES);
            c.readTimeout(3, TimeUnit.MINUTES);
            c.writeTimeout(3, TimeUnit.MINUTES);

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(BASE_URL)
                    .build();
        }
    }

    public static synchronized RetrofitClient getInstance() {
        if (client == null) {
            client = new RetrofitClient();
        }
        return client;
    }



    public ApiCall api() {
        return retrofit.create(ApiCall.class);
    }
}
