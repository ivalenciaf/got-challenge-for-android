package es.npatarino.android.gotchallenge.view;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.parceler.Parcels;

import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.model.GoTCharacter;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";

    public static final String EXTRA_CHARACTER = "character";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final SimpleDraweeView ivp = (SimpleDraweeView) findViewById(R.id.iv_photo);
        final TextView tvn = (TextView) findViewById(R.id.tv_name);
        final TextView tvd = (TextView) findViewById(R.id.tv_description);

        GoTCharacter character = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_CHARACTER));

        Toolbar toolbar = (Toolbar) findViewById(R.id.t);
        toolbar.setTitle(character.getName());
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tvn.setText(character.getName());
        tvd.setText(character.getDescription());
        ivp.setImageURI(Uri.parse(character.getImageUrl()));
    }
}
