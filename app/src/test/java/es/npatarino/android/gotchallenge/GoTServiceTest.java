package es.npatarino.android.gotchallenge;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import es.npatarino.android.gotchallenge.model.GoTCharacter;
import es.npatarino.android.gotchallenge.model.GoTHouse;
import es.npatarino.android.gotchallenge.net.GoTRestClient;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

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
        List<GoTCharacter> characters = client.characters();

        assertNotNull(characters);
        assertNotEquals(0, characters.size());

        for (GoTCharacter character : characters) {
            System.out.println(character);
        }
    }

    @Test
    public void testHouses() throws IOException {
        Collection<GoTCharacter> characters = client.characters();

        Collection<GoTHouse> houses = GoTHouse.housesFromCharacters(characters);

        assertNotNull(houses);
        assertThat(houses.size(), is(7));
    }

    @Test
    public void testCharactersOfHouse() throws IOException {
        Collection<GoTCharacter> characters = client.characters();

        Collection<GoTCharacter> filtered = GoTHouse.charactersOfHouse("50fab25b", characters);

        assertNotNull(filtered);
        assertThat(filtered.size(), is(3));
    }
}
