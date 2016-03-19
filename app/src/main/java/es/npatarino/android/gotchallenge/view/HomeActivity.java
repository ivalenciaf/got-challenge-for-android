package es.npatarino.android.gotchallenge.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.parceler.Parcels;

import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.model.GoTCharacter;
import es.npatarino.android.gotchallenge.model.GoTHouse;

public class HomeActivity extends AppCompatActivity implements HousesFragment.HouseContract, CharactersFragment.CharacterContract {
    private TabLayout mTabLayout;
    private int mSelectedTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager vp = (ViewPager) findViewById(R.id.container);
        vp.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager()));

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(vp);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mTabLayout != null) {
            mTabLayout.getTabAt(mSelectedTab).select();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mTabLayout != null) {
            mSelectedTab = mTabLayout.getSelectedTabPosition();
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new CharactersFragment();
            } else {
                return new HousesFragment();
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

    /**
     * Event called when a House is clicked.
     *
     * @param house         the house that has been clicked
     * @param sharedElement the shared element view to use for the transition
     */
    @Override
    public void onHouseClick(GoTHouse house, View sharedElement) {
        Intent intent = new Intent(this, HouseDetailActivity.class);
        intent.putExtra(HouseDetailActivity.EXTRA_HOUSE, Parcels.wrap(house));

        launchActivity(intent, sharedElement, getString(R.string.transition_shared_house));
    }

    /**
     * Event called when a Character is clicked.
     *
     * @param character     the character that has been clicked
     * @param sharedElement the shared element view to use for the transition
     */
    @Override
    public void onCharacterClick(GoTCharacter character, View sharedElement) {
        Intent intent = new Intent(this, CharacterDetailActivity.class);
        intent.putExtra(CharacterDetailActivity.EXTRA_CHARACTER, Parcels.wrap(character));

        launchActivity(intent, sharedElement, getString(R.string.transition_shared_character));
    }

    private void launchActivity(Intent intent, View sharedElement, String transitionName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    sharedElement,
                    transitionName);

            ActivityCompat.startActivity(this, intent, options.toBundle());
        } else {
            this.startActivity(intent);
        }
    }
}
