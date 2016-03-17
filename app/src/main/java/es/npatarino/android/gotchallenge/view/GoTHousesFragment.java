package es.npatarino.android.gotchallenge.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.adapter.GoTHouseAdapter;
import es.npatarino.android.gotchallenge.model.GoTCharacter;
import es.npatarino.android.gotchallenge.model.GoTHouse;
import es.npatarino.android.gotchallenge.net.GoTRestClient;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Fragment with the list of houses
 */
public class GoTHousesFragment extends Fragment {

    private static final String TAG = "GoTHousesListFragment";
    private GoTHouseAdapter mAdapter;
    private ContentLoadingProgressBar mProgress;
    private Callback<List<GoTCharacter>> callback = new Callback<List<GoTCharacter>>() {
        @Override
        public void onResponse(Response<List<GoTCharacter>> response, Retrofit retrofit) {
            List<GoTCharacter> characters = response.body();

            Log.d(TAG, "Characters: " + characters);

            Set<GoTHouse> hs = new TreeSet<>();
            for (GoTCharacter character : characters) {
                if (character.getHouse() != null) {
                    hs.add(character.getHouse());
                }
            }
            mAdapter.addAll(hs);
            mAdapter.notifyDataSetChanged();
            mProgress.hide();
        }

        @Override
        public void onFailure(Throwable t) {
            Log.e(TAG, "Exception calling characters service.", t);
            t.printStackTrace();
        }
    };

    public GoTHousesFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.houses_list, container, false);
        mProgress = (ContentLoadingProgressBar) rootView.findViewById(R.id.pb);
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.houses);

        mAdapter = new GoTHouseAdapter(getActivity());
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);
        rv.setAdapter(mAdapter);

        GoTRestClient.getInstance(getContext()).characters(callback);

        return rootView;
    }
}
