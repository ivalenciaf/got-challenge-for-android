package es.npatarino.android.gotchallenge.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.adapter.CharacterAdapter;
import es.npatarino.android.gotchallenge.model.GoTCharacter;
import es.npatarino.android.gotchallenge.net.GoTRestClient;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Fragment with the list of characters
 */
public class CharactersFragment extends Fragment implements SearchView.OnQueryTextListener {

    private static final String TAG = "CharactersFragment";

    private RecyclerView mRecyclerView;
    private CharacterAdapter mAdapter;
    private ContentLoadingProgressBar mProgress;

    private final Callback<List<GoTCharacter>> mCallback = new Callback<List<GoTCharacter>>() {
        @Override
        public void onResponse(Response<List<GoTCharacter>> response, Retrofit retrofit) {
            List<GoTCharacter> mCharacters = response.body();

            Log.d(TAG, "Characters: " + mCharacters);

            // Do a precache of all the images of the characters to make posible view them in offline mode
            GoTRestClient.getInstance(getContext()).precacheImagesInDisk(mCharacters);

            mAdapter.addAll(mCharacters);
            ((CharacterSearchFilter) mAdapter.getFilter()).setCharacters(mCharacters);
            mAdapter.notifyDataSetChanged();
            mProgress.hide();
        }

        @Override
        public void onFailure(Throwable t) {
            Log.e(TAG, "Exception calling characters service.", t);
        }
    };

    public CharactersFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        // Informs this fragment has menus so that the search view is available
        setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.characters_list, container, false);
        mProgress = (ContentLoadingProgressBar) rootView.findViewById(R.id.pb);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.characters);

        mAdapter = new CharacterAdapter(getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        GoTRestClient.getInstance(getContext()).characters(mCallback);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflates the menu with the search view
        inflater.inflate(R.menu.menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getString(R.string.action_search));
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextChange(String query) {
        mAdapter.getFilter().filter(query);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(0);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    /**
     * Contract of characters
     */
    public interface CharacterContract {
        void onCharacterClick(GoTCharacter character, View sharedElement);
    }
}
