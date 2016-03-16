package es.npatarino.android.gotchallenge;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import es.npatarino.android.gotchallenge.model.GoTCharacter;
import es.npatarino.android.gotchallenge.net.GoTRestClient;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests of the GoT services.
 */
public class GoTServiceTest {
    private GoTRestClient client;

    @Before
    public void setUp() {
        client = GoTRestClient.getInstance(null);
    }

    @Test
    public void testCharacters() throws IOException {
        //client.characters(callback);

        List<GoTCharacter> characters = client.characters();

        assertNotNull(characters);
        assertNotEquals(0, characters.size());

        for (GoTCharacter character : characters) {
            System.out.println(character);
        }
    }
}
