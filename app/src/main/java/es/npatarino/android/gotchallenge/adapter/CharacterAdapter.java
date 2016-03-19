package es.npatarino.android.gotchallenge.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.model.GoTCharacter;
import es.npatarino.android.gotchallenge.view.CharacterDetailActivity;
import es.npatarino.android.gotchallenge.view.CharacterSearchFilter;
import es.npatarino.android.gotchallenge.view.CharactersFragment;

/**
 * Adapter of characters
 */
public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.GotCharacterViewHolder> implements Filterable {

    private final Activity activity;
    private List<GoTCharacter> gcs;
    private Filter mFilter;

    public CharacterAdapter(Activity activity) {
        this.activity = activity;
        this.gcs = new ArrayList<>();
    }

    public void addAll(Collection<GoTCharacter> collection) {
        gcs.addAll(collection);
    }

    public void clear() {
        if (gcs != null) {
            this.gcs.clear();
        }
    }

    @Override
    public CharacterAdapter.GotCharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GotCharacterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.got_character_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final CharacterAdapter.GotCharacterViewHolder holder, final int position) {
        final GoTCharacter character = gcs.get(position);

        holder.render(character);
        holder.imp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (activity instanceof CharactersFragment.CharacterContract) {
                    ((CharactersFragment.CharacterContract) activity).onCharacterClick(character, holder.imp);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return gcs.size();
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new CharacterSearchFilter(this, gcs);
        }

        return mFilter;
    }

    public static class GotCharacterViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView imp;
        TextView tvn;

        public GotCharacterViewHolder(View itemView) {
            super(itemView);
            imp = (SimpleDraweeView) itemView.findViewById(R.id.ivBackground);
            tvn = (TextView) itemView.findViewById(R.id.tv_name);
        }

        public void render(final GoTCharacter character) {
            tvn.setText(character.getName());
            imp.setImageURI(Uri.parse(character.getImageUrl()));
        }
    }

}
