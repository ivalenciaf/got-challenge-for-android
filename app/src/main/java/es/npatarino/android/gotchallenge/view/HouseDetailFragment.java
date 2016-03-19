package es.npatarino.android.gotchallenge.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import java.util.List;

import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.adapter.CharacterAdapter;
import es.npatarino.android.gotchallenge.model.GoTCharacter;
import es.npatarino.android.gotchallenge.model.GoTHouse;
import es.npatarino.android.gotchallenge.net.GoTRestClient;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Fragment with the detail of a house
 */
public class HouseDetailFragment extends Fragment {

    private CharacterAdapter mAdapter;
    private GoTHouse mHouse;

    protected static HouseDetailFragment newInstance(GoTHouse house) {
        HouseDetailFragment f = new HouseDetailFragment();

        Bundle args = new Bundle();

        args.putParcelable(HouseDetailActivity.EXTRA_HOUSE, Parcels.wrap(house));
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.characters_list, container, false);

        mHouse = Parcels.unwrap(getArguments().getParcelable(HouseDetailActivity.EXTRA_HOUSE));

        mAdapter = new CharacterAdapter(getActivity());

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.characters);
        rv.setAdapter(mAdapter);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));

        final ContentLoadingProgressBar pb = (ContentLoadingProgressBar) rootView.findViewById(R.id.pb);

        GoTRestClient client = GoTRestClient.getInstance(getContext());

        client.characters(new Callback<List<GoTCharacter>>() {
            @Override
            public void onResponse(Response<List<GoTCharacter>> response, Retrofit retrofit) {
                mAdapter.addAll(GoTHouse.charactersOfHouse(mHouse.getId(), response.body()));
                mAdapter.notifyDataSetChanged();
                pb.hide();
            }

            @Override
            public void onFailure(Throwable t) {
                // Trace the error
                pb.hide();
            }
        });

        return rootView;
    }
}
