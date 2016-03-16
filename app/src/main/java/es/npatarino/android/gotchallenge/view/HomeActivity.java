package es.npatarino.android.gotchallenge.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.adapter.GoTAdapter;
import es.npatarino.android.gotchallenge.adapter.GoTHouseAdapter;
import es.npatarino.android.gotchallenge.model.GoTCharacter;
import es.npatarino.android.gotchallenge.model.GoTHouse;
import es.npatarino.android.gotchallenge.net.GoTRestClient;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class HomeActivity extends AppCompatActivity {

    SectionsPagerAdapter spa;
    ViewPager vp;
    Toolbar toolbar;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSpa(new SectionsPagerAdapter(getSupportFragmentManager()));

        setVp((ViewPager) findViewById(R.id.container));
        getVp().setAdapter(getSpa());

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(getVp());
    }

    public SectionsPagerAdapter getSpa() {
        return spa;
    }

    public void setSpa(SectionsPagerAdapter spa) {
        this.spa = spa;
    }

    public ViewPager getVp() {
        return vp;
    }

    public void setVp(ViewPager vp) {
        this.vp = vp;
    }

    public static class GoTListFragment extends Fragment implements SearchView.OnQueryTextListener {

        private static final String TAG = "GoTListFragment";

        private RecyclerView rv;
        private List<GoTCharacter> mCharacters;
        private GoTAdapter adp;
        private ContentLoadingProgressBar pb;
        private Callback<List<GoTCharacter>> callback = new Callback<List<GoTCharacter>>() {
            @Override
            public void onResponse(Response<List<GoTCharacter>> response, Retrofit retrofit) {
                mCharacters = response.body();

                Log.d(TAG, "Characters: " + mCharacters);

                // Do a precache of all the images of the characters to make posible view them in offline mode
                GoTRestClient.getInstance(getContext()).precacheImagesInDisk(mCharacters);

                adp.addAll(mCharacters);
                ((FilterCharacterSearch) adp.getFilter()).setCharacters(mCharacters);
                adp.notifyDataSetChanged();
                pb.hide();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "Exception calling characters service.", t);
            }
        };

        public GoTListFragment() {
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
            // Informs this fragment has menus so that the search view is available
            setHasOptionsMenu(true);

            View rootView = inflater.inflate(R.layout.fragment_list, container, false);
            pb = (ContentLoadingProgressBar) rootView.findViewById(R.id.pb);
            rv = (RecyclerView) rootView.findViewById(R.id.rv);

            adp = new GoTAdapter();
            rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            rv.setHasFixedSize(true);
            rv.setAdapter(adp);

            GoTRestClient.getInstance(getContext()).characters(callback);

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
            adp.getFilter().filter(query);
            adp.notifyDataSetChanged();
            rv.scrollToPosition(0);
            return true;
        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }
    }

    public static class GoTHousesListFragment extends Fragment {

        private static final String TAG = "GoTHousesListFragment";
        private GoTHouseAdapter adp;
        private ContentLoadingProgressBar pb;
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
                adp.addAll(hs);
                adp.notifyDataSetChanged();
                pb.hide();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "Exception calling characters service.", t);
                t.printStackTrace();
            }
        };

        public GoTHousesListFragment() {
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_list, container, false);
            pb = (ContentLoadingProgressBar) rootView.findViewById(R.id.pb);
            RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv);

            adp = new GoTHouseAdapter();
            rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            rv.setHasFixedSize(true);
            rv.setAdapter(adp);

            GoTRestClient.getInstance(getContext()).characters(callback);

            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new GoTListFragment();
            } else {
                return new GoTHousesListFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Characters";
                case 1:
                    return "Houses";
            }
            return null;
        }
    }
}
