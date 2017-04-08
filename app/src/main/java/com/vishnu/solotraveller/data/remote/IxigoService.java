package com.vishnu.solotraveller.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vishnu.solotraveller.data.model.Example;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

public interface IxigoService {

    String ENDPOINT = "http://build2.ixigo.com/api/v2/";




    @GET("widgets/brand/inspire?product=1&apiKey=ixicode!2$")
    Observable<Example>  getRecommendedDestinations();

    /******** Helper class that sets up a new services *******/
    class Creator {

        public static IxigoService newRibotsService() {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(IxigoService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(IxigoService.class);
        }
    }
}
