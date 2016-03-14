package es.npatarino.android.gotchallenge.net;

import android.content.Context;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.request.ImageRequest;

import java.io.IOException;
import java.util.List;

import es.npatarino.android.gotchallenge.GoTCharacter;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Client of the GoT rest services.
 */
public class GoTRestClient {
    private static final String TAG = "GoTRestClient";

    private static GoTRestClient instance;
    private Context mContext;
    private boolean mImagesPrecached = false;


    private GoTService service;

    private GoTRestClient(Context context) {
        mContext = context;

        ServiceClientBuilder builder = new ServiceClientBuilder(context);

        service = builder.build(GoTService.class);
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
     * Precache the images of the characters and houses in disk to make them available in offline mode.
     * The client controls it's done only the first time the method is called.
     *
     * @param characters the list of characters
     */
    public void precacheImagesInDisk(final List<GoTCharacter> characters) {
        precacheImagesInDisk(characters, false);
    }

    /**
     * Precache the images of the characters and houses in disk to make them available in offline mode.
     * The client controls it's done only the first time the method is called.
     *
     * @param characters the list of characters
     * @param force if true forces to do the precache even if is not the first time the method is called
     */
    public void precacheImagesInDisk(final List<GoTCharacter> characters, boolean force) {
        if ((!mImagesPrecached || force) && characters != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "Precaching images to disk");
                    ImagePipeline pipeline = Fresco.getImagePipeline();
                    for (GoTCharacter character : characters) {
                        pipeline.prefetchToDiskCache(ImageRequest.fromUri(character.getIu()), mContext);
                        pipeline.prefetchToDiskCache(ImageRequest.fromUri(character.getHu()), mContext);
                    }

                    mImagesPrecached = true;
                    Log.d(TAG, "Precaching done");
                }
            }).start();
        }
    }
}
