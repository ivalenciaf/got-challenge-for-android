package es.npatarino.android.gotchallenge.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.model.GoTHouse;
import es.npatarino.android.gotchallenge.view.HouseDetailActivity;

/**
 * Adapter for houses.
 */
public class GoTHouseAdapter extends RecyclerView.Adapter<GoTHouseAdapter.GotCharacterViewHolder> {

    private final List<GoTHouse> gcs;

    public GoTHouseAdapter() {
        this.gcs = new ArrayList<>();
    }

    public void addAll(Collection<GoTHouse> collection) {
        gcs.addAll(collection);
    }

    @Override
    public GoTHouseAdapter.GotCharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GotCharacterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.got_house_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final GoTHouseAdapter.GotCharacterViewHolder holder, final int position) {
        holder.render(gcs.get(position));
        holder.imp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), HouseDetailActivity.class);
                intent.putExtra(HouseDetailActivity.EXTRA_HOUSE, Parcels.wrap(holder.house));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gcs.size();
    }

    public static class GotCharacterViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView imp;
        GoTHouse house;

        public GotCharacterViewHolder(View itemView) {
            super(itemView);
            imp = (SimpleDraweeView) itemView.findViewById(R.id.ivBackground);
        }

        public void render(final GoTHouse house) {
            this.house = house;
            imp.setImageURI(Uri.parse(house.getImageUrl()));
        }
    }

}
