package es.npatarino.android.gotchallenge.adapter;

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

import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.model.GoTHouse;
import es.npatarino.android.gotchallenge.view.HousesFragment;

/**
 * Adapter for houses.
 */
public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.GotHouseViewHolder> {

    private final Activity activity;
    private final List<GoTHouse> gcs;

    public HouseAdapter(Activity activity) {
        this.activity = activity;
        this.gcs = new ArrayList<>();
    }

    public void addAll(Collection<GoTHouse> collection) {
        gcs.addAll(collection);
    }

    @Override
    public GotHouseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GotHouseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.got_house_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final GotHouseViewHolder holder, final int position) {
        holder.render(gcs.get(position));
        holder.imp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity instanceof HousesFragment.HouseContract) {
                    ((HousesFragment.HouseContract) activity).onHouseClick(holder.house, holder.imp);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return gcs.size();
    }

    public static class GotHouseViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView imp;
        GoTHouse house;

        public GotHouseViewHolder(View itemView) {
            super(itemView);
            imp = (SimpleDraweeView) itemView.findViewById(R.id.ivBackground);
        }

        public void render(final GoTHouse house) {
            this.house = house;
            imp.setImageURI(Uri.parse(house.getImageUrl()));
        }
    }

}
