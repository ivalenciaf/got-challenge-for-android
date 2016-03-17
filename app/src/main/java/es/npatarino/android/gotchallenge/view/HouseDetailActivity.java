package es.npatarino.android.gotchallenge.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.model.GoTCharacter;
import es.npatarino.android.gotchallenge.model.GoTHouse;
import es.npatarino.android.gotchallenge.net.GoTRestClient;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class HouseDetailActivity extends AppCompatActivity {
    public static final String EXTRA_HOUSE = "house";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_detail);

        final SimpleDraweeView ivp = (com.facebook.drawee.view.SimpleDraweeView) findViewById(R.id.iv_photo);
        final GridView grid = (GridView) findViewById(R.id.grid);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            grid.setNestedScrollingEnabled(true);
        }

        final GoTHouse house = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_HOUSE));

        Toolbar toolbar = (Toolbar) findViewById(R.id.t);
        toolbar.setTitle(house.getName());
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ivp.setImageURI(Uri.parse(house.getImageUrl()));

        GoTRestClient client = GoTRestClient.getInstance(this);

        client.characters(new Callback<List<GoTCharacter>>() {
            @Override
            public void onResponse(Response<List<GoTCharacter>> response, Retrofit retrofit) {
                CharacterAdapter adapter = new CharacterAdapter(filter(house.getId(), response.body()));
                grid.setAdapter(adapter);
            }

            @Override
            public void onFailure(Throwable t) {
                // Trace the error
            }
        });
    }

    private List<GoTCharacter> filter(String houseId, List<GoTCharacter> characters) {
        List<GoTCharacter> filtered = new ArrayList<>();

        for (GoTCharacter character : characters) {
            if (character.getHouse() != null && character.getHouse().getId().equals(houseId)) {
                filtered.add(character);
            }
        }

        return filtered;
    }

    private void startDetailActivity(GoTCharacter character, View sharedElement) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_CHARACTER, Parcels.wrap(character));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    sharedElement,
                    getString(R.string.transition_shared_image)
            );

            ActivityCompat.startActivity(this, intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    class CharacterAdapter extends BaseAdapter {
        private List<GoTCharacter> mCharacters;

        CharacterAdapter(List<GoTCharacter> characters) {
            mCharacters = characters;
        }

        public int getCount() {
            return mCharacters.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final GoTCharacter character = mCharacters.get(position);

            final SquareImageView imageView;
            if (convertView == null) {
                imageView = new SquareImageView(HouseDetailActivity.this);
                imageView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, GridView.LayoutParams.MATCH_PARENT));
                imageView.setScaleType(SimpleDraweeView.ScaleType.CENTER_CROP);
            } else {
                imageView = (SquareImageView) convertView;
            }

            imageView.setImageURI(Uri.parse(character.getImageUrl()));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    startDetailActivity(character, imageView);
                }
            });

            return imageView;
        }
    }
}
