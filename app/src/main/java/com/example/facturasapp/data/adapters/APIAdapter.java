package com.example.facturasapp.data.adapters;

import com.example.facturasapp.network.FacturaService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIAdapter {
    //Se hace la llamada a internet y coge los datos
    private static FacturaService service;

    public static FacturaService getService() {
        // Creamos un interceptor y le indicamos el log level a usar
        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Asociamos el interceptor a las peticiones
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        if (service == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://viewnextandroid2.wiremockapi.cloud/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build()) // <-- set log level
                    .build();

            service = retrofit.create(FacturaService.class);
        }

        return service;
    }
}
