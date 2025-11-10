package com.example.testapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import java.util.concurrent.TimeUnit;

public class ApiUtils {

    private static final String BASE_URL = "https://www.vervemobi.com/";
    private static final String USERNAME = "android_test";
    private static final String PASSWORD = "3p2mIuS0N0";

    private static Retrofit retrofit = null;

    public static ApiInterface getApiInterface() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(chain -> {
                        Request original = chain.request();
                        String credential = Credentials.basic(USERNAME, PASSWORD);

                        Request request = original.newBuilder()
                                .header("Authorization", credential)
                                .method(original.method(), original.body())
                                .build();

                        return chain.proceed(request);
                    })
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }
}
