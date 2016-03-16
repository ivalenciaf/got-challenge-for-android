package es.npatarino.android.gotchallenge.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import es.npatarino.android.gotchallenge.model.GoTCharacter;
import es.npatarino.android.gotchallenge.model.GoTCharacterTypeAdapter;
import retrofit.Converter;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Builder of the GoT services client.
 *
 * @version 1.0
 */
public class ServiceClientBuilder {
    private static final String TAG = "ServiceClientBuilder";

    //private static final HttpLoggingInterceptor.Level LOGGING_LEVEL = HttpLoggingInterceptor.Level.BODY;
    private static final HttpLoggingInterceptor.Level LOGGING_LEVEL = HttpLoggingInterceptor.Level.BASIC;
    private static final long CONNECT_TIMEOUT = 5000;
    private static final long READ_TIMEOUT = 5000;
    private static final String BASE_URL = "http://ec2-52-18-202-124.eu-west-1.compute.amazonaws.com:3000/";

    private String baseUrl = BASE_URL;
    private OkHttpClient okHttpClient;
    private HttpLoggingInterceptor logging;
    private Converter.Factory converterFactory;
    private long connectTimeout = CONNECT_TIMEOUT;
    private long readTimeout = READ_TIMEOUT;

    public ServiceClientBuilder(Context context) {
        setupHttpClient(new OkHttpClient());
        setupLogging();
        setupCache(context);

        Type charactersType = new TypeToken<ArrayList<GoTCharacter>>() {
        }.getType();
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(GoTCharacter.class, new GoTCharacterTypeAdapter());
        converterFactory = GsonConverterFactory.create(builder.create());
    }

    private void setupHttpClient(OkHttpClient httpClient) {
        okHttpClient = httpClient;
        okHttpClient.setConnectTimeout(connectTimeout, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(readTimeout, TimeUnit.MILLISECONDS);
    }

    private void setupLogging() {
        logging = new HttpLoggingInterceptor();
        logging.setLevel(LOGGING_LEVEL);
        okHttpClient.interceptors().add(logging);
    }

    private void setupCache(final Context context) {
        if (context != null) {
            File httpCacheDirectory = new File(context.getCacheDir(), "responses");
            Cache httpResponseCache = new Cache(httpCacheDirectory, 20 * 1024 * 1024);
            okHttpClient.setCache(httpResponseCache);

            RequestCacheInterceptor cacheInterceptor = new RequestCacheInterceptor(context);

            okHttpClient.networkInterceptors().add(cacheInterceptor);
            okHttpClient.interceptors().add(cacheInterceptor);
        }
    }

    public ServiceClientBuilder baseUrl(String url) {
        this.baseUrl = url;

        return this;
    }

    public ServiceClientBuilder httpClient(OkHttpClient httpClient) {
        okHttpClient = httpClient;
        okHttpClient.interceptors().add(logging);

        return this;
    }

    public ServiceClientBuilder loggingLevel(HttpLoggingInterceptor.Level level) {
        logging.setLevel(level);

        return this;
    }

    public ServiceClientBuilder connectTimeout(long timeout) {
        this.connectTimeout = timeout;
        okHttpClient.setConnectTimeout(connectTimeout, TimeUnit.MILLISECONDS);

        return this;
    }

    public ServiceClientBuilder readTimeout(long timeout) {
        this.readTimeout = timeout;
        okHttpClient.setReadTimeout(readTimeout, TimeUnit.MILLISECONDS);

        return this;
    }

    public ServiceClientBuilder addConverterFactory(Converter.Factory converterFactory) {
        this.converterFactory = converterFactory;

        return this;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    /**
     * Builds a new implementation of the API service.
     *
     * @param service the service interface class to implement
     * @param <T>     the type of the service interface
     * @return the API service implementation object
     */
    public <T> T build(java.lang.Class<T> service) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(this.baseUrl)
                .client(okHttpClient)
                .addConverterFactory(converterFactory)
                .build();

        return retrofit.create(service);
    }

    /**
     * Checks if the device is connected to a network.
     *
     * @param context the context
     * @return true if the device is connected, otherwise false
     */
    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = manager.getActiveNetworkInfo();

        return (netInfo != null && netInfo.isConnected());
    }

    /**
     * OkHttp interceptor of requests that inject cache headers so that the request is saved in disk
     */
    private class RequestCacheInterceptor implements Interceptor {
        private Context context;

        RequestCacheInterceptor(Context context) {
            this.context = context;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            String cacheHeaderValue = isNetworkAvailable(context)
                    ? "public, max-age=2419200"
                    : "public, only-if-cached, max-stale=2419200";
            Request request = originalRequest.newBuilder().build();
            Response response = chain.proceed(request);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", cacheHeaderValue)
                    .build();
        }
    }
}
