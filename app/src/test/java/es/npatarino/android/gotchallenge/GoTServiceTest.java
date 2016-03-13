package es.npatarino.android.gotchallenge;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import es.npatarino.android.gotchallenge.net.GoTRestClient;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Tests of the GoT services.
 */
public class GoTServiceTest {
    private GoTRestClient client;

    @Before
    public void setUp() {
        client = GoTRestClient.getInstance();
    }

    @Test
    public void testCharacters() throws IOException {
        //client.characters(callback);

        List<GoTCharacter> characters = client.characters();

        System.out.println("Characters: " + characters);

        assertNotNull(characters);
        assertNotEquals(0, characters.size());
    }

    @Test
    public void testDownloadImage() throws IOException {
        InputStream image = client.getImage("https://s3-eu-west-1.amazonaws.com/npatarino/got/stark.jpg");

        System.out.println("Image: " + image);

        assertNotNull(image);
    }

    private Callback<List<GoTCharacter>> callback = new Callback<List<GoTCharacter>>() {
        @Override
        public void onResponse(Response<List<GoTCharacter>> response, Retrofit retrofit) {
            List<GoTCharacter> characters = response.body();

            System.out.println("Characters: " + characters);

            assertNotNull(characters);
            assertNotEquals(0, characters.size());
        }

        @Override
        public void onFailure(Throwable t) {
            fail("Exception calling movements service.");
        }
    };
}
