package es.npatarino.android.gotchallenge;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Adapter of characters
 */
public class GoTAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private List<GoTCharacter> gcs;
    private Activity a;
    private Filter mFilter;

    public GoTAdapter(Activity activity) {
        this.gcs = new ArrayList<>();
        a = activity;
    }

    public void addAll(Collection<GoTCharacter> collection) {
        for (int i = 0; i < collection.size(); i++) {
            gcs.add((GoTCharacter) collection.toArray()[i]);
        }
    }

    public void clear() {
        if (gcs != null) {
            this.gcs.clear();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GotCharacterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.got_character_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        GotCharacterViewHolder gotCharacterViewHolder = (GotCharacterViewHolder) holder;
        gotCharacterViewHolder.render(gcs.get(position));
        ((GotCharacterViewHolder) holder).imp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(((GotCharacterViewHolder) holder).itemView.getContext(), DetailActivity.class);
                intent.putExtra("description", gcs.get(position).d);
                intent.putExtra("name", gcs.get(position).n);
                intent.putExtra("imageUrl", gcs.get(position).iu);
                ((GotCharacterViewHolder) holder).itemView.getContext().startActivity(intent);
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
            mFilter = new FilterCharacterSearch(this, gcs);
        }

        return mFilter;
    }

    class GotCharacterViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView imp;
        TextView tvn;

        public GotCharacterViewHolder(View itemView) {
            super(itemView);
            imp = (SimpleDraweeView) itemView.findViewById(R.id.ivBackground);
            tvn = (TextView) itemView.findViewById(R.id.tv_name);
        }

        public void render(final GoTCharacter goTCharacter) {
            tvn.setText(goTCharacter.n);
            imp.setImageURI(Uri.parse(goTCharacter.iu));
        }
    }

}
