package es.npatarino.android.gotchallenge;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import es.npatarino.android.gotchallenge.net.GoTRestClient;

public class DetailActivity extends AppCompatActivity {


    private static final String TAG = "DetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final ImageView ivp = (ImageView) findViewById(R.id.iv_photo);
        final TextView tvn = (TextView) findViewById(R.id.tv_name);
        final TextView tvd = (TextView) findViewById(R.id.tv_description);

        final String d = getIntent().getStringExtra("description");
        final String n = getIntent().getStringExtra("name");
        final String i = getIntent().getStringExtra("imageUrl");

        Toolbar toolbar = (Toolbar) findViewById(R.id.t);
        toolbar.setTitle(n);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        GoTRestClient.getInstance(this).getImage(i, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e(TAG, "Exception downloading character image", e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                DetailActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ivp.setImageBitmap(bmp);
                        tvn.setText(n);
                        tvd.setText(d);
                    }
                });
            }
        });
    }
}
