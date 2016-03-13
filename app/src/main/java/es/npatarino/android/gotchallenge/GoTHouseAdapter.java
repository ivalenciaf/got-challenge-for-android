package es.npatarino.android.gotchallenge;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import es.npatarino.android.gotchallenge.net.GoTRestClient;

/**
 * Adapter for houses.
 */
public class GoTHouseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<GoTCharacter.GoTHouse> gcs;
    private Activity a;
    private GoTRestClient client;

    public GoTHouseAdapter(Activity activity) {
        this.gcs = new ArrayList<>();
        a = activity;
        client = GoTRestClient.getInstance(a);
    }

    void addAll(Collection<GoTCharacter.GoTHouse> collection) {
        for (int i = 0; i < collection.size(); i++) {
            gcs.add((GoTCharacter.GoTHouse) collection.toArray()[i]);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GotCharacterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.got_house_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        GotCharacterViewHolder gotCharacterViewHolder = (GotCharacterViewHolder) holder;
        gotCharacterViewHolder.render(gcs.get(position));
    }

    @Override
    public int getItemCount() {
        return gcs.size();
    }

    class GotCharacterViewHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "GotCharacterViewHolder";
        ImageView imp;

        public GotCharacterViewHolder(View itemView) {
            super(itemView);
            imp = (ImageView) itemView.findViewById(R.id.ivBackground);
        }

        public void render(final GoTCharacter.GoTHouse goTHouse) {
            client.getImage(goTHouse.u, new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    Log.w(TAG, "Exception downloading image of house", e);
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    if (response.isSuccessful()) {
                        final Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                        a.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imp.setImageBitmap(bmp);
                            }
                        });
                    }
                }
            });
        }
    }

}
