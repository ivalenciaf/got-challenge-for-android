package es.npatarino.android.gotchallenge.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;

import org.parceler.Parcels;

import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.model.GoTCharacter;
import es.npatarino.android.gotchallenge.model.GoTHouse;

public class HouseDetailActivity extends AppCompatActivity implements CharactersFragment.CharacterContract {
    public static final String EXTRA_HOUSE = "house";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_detail);

        final GoTHouse house = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_HOUSE));

        final SimpleDraweeView ivp = (com.facebook.drawee.view.SimpleDraweeView) findViewById(R.id.iv_photo);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivp.setTransitionName(getString(R.string.transition_shared_house));
        }


        final Toolbar toolbar = (Toolbar) findViewById(R.id.t);
        toolbar.setTitle(house.getName());
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ivp.setImageURI(Uri.parse(house.getImageUrl()));

        if (getSupportFragmentManager().findFragmentById(android.R.id.content) == null) {
            Fragment f = HouseDetailFragment.newInstance(house);
            getSupportFragmentManager().beginTransaction().replace(R.id.content, f).commit();
        }
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    sharedElement,
                    getString(R.string.transition_shared_character)
            );

            ActivityCompat.startActivity(this, intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }
}
