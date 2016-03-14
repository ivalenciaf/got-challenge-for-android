package es.npatarino.android.gotchallenge;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Adapter for houses.
 */
public class GoTHouseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<GoTCharacter.GoTHouse> gcs;
    private Activity a;

    public GoTHouseAdapter(Activity activity) {
        this.gcs = new ArrayList<>();
        a = activity;
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
        SimpleDraweeView imp;

        public GotCharacterViewHolder(View itemView) {
            super(itemView);
            imp = (SimpleDraweeView) itemView.findViewById(R.id.ivBackground);
        }

        public void render(final GoTCharacter.GoTHouse goTHouse) {
            imp.setImageURI(Uri.parse(goTHouse.u));
        }
    }

}
