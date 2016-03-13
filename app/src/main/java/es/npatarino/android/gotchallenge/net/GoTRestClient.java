package es.npatarino.android.gotchallenge.net;

import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import es.npatarino.android.gotchallenge.GoTCharacter;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Client of the GoT rest services.
 */
public class GoTRestClient {
    private static GoTRestClient instance;
    private OkHttpClient mHttpClient;

    private GoTService service;

    private GoTRestClient(Context context) {
        ServiceClientBuilder builder = new ServiceClientBuilder(context);

        service = builder.build(GoTService.class);
        mHttpClient = builder.getOkHttpClient();
    }

    public static GoTRestClient getInstance(Context context) {
        if (instance == null) {
            instance = new GoTRestClient(context);
        }

        return instance;
    }

    /**
     * Get the list of characters of GoT asynchronously
     *
     * @param callback the callback function to call after receiving the response from the rest service.
     */
    public void characters(Callback<List<GoTCharacter>> callback) {
        service.characters().enqueue(callback);
    }

    /**
     * Get the list of characters of GoT synchronously
     */
    public List<GoTCharacter> characters() throws IOException {
        List<GoTCharacter> characters = null;

        Call<List<GoTCharacter>> call = service.characters();

        Response<List<GoTCharacter>> response = call.execute();

        if (response.isSuccess()) {
            characters = response.body();
        } else {
            Log.w("GoTRestClient", "Response of characters with error: " + response.errorBody());
        }

        return characters;
    }

    /**
     * Download an image from the GoT service synchronously
     *
     * @param imageUrl the url of the image to download
     * @param callback the callback function to call after receiving the response
     */
    public void getImage(String imageUrl, com.squareup.okhttp.Callback callback) {
        Request request = new Request.Builder()
                .url(imageUrl)
                .build();

        mHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * Download an image from the GoT service synchronously
     *
     * @param imageUrl the url of the image to download
     * @return the stream with the image
     * @throws IOException in case of exception
     */
    public InputStream getImage(String imageUrl) throws IOException {
        InputStream image = null;

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Request request = new Request.Builder()
                    .url(imageUrl)
                    .build();

            com.squareup.okhttp.Response response = mHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                image = response.body().byteStream();
            } else {
                Log.w("GoTRestClient", "Exception downloading image '" + imageUrl + "'" + response.message());
            }
        }

        return image;
    }
}
