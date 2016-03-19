package es.npatarino.android.gotchallenge;

import android.content.Intent;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.Toolbar;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.parceler.Parcels;

import es.npatarino.android.gotchallenge.model.GoTCharacter;
import es.npatarino.android.gotchallenge.model.GoTHouse;
import es.npatarino.android.gotchallenge.view.CharacterDetailActivity;
import es.npatarino.android.gotchallenge.view.HouseDetailActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static org.hamcrest.core.Is.is;

/**
 * Stub of models for the test
 */
public class TestStub {
    public static final String TAB_HOUSES = "Houses";

    static final String CHARACTER_NAME = "Jaime Lannister";
    static final String CHARACTER_DESCRIPTION = "In A Game of Thrones (1996), Jaime is...";
    static final String CHARACTER_IMAGE_URL = "https://s3-eu-west-1.amazonaws.com/npatarino/got/f3c4eaec-741f-45e0-8b64-5985095fa1fa.jpg";
    static final int CHARACTER_POSITION = 3;
    static final int CHARACTER_POSITION_IN_HOUSE = 1;

    static final String HOUSE_ID = "50fab25b";
    static final String HOUSE_NAME = "House Lannister";
    static final String HOUSE_IMAGE_URL = "https://s3-eu-west-1.amazonaws.com/npatarino/got/lannister.jpg";
    static final int HOUSE_POSITION = 2;
    static final int HOUSE_CHARACTERS_NUM = 3;

    static final GoTCharacter character = new GoTCharacter();
    static final GoTHouse house = new GoTHouse();

    static {
        character.setName(CHARACTER_NAME);
        character.setDescription(CHARACTER_DESCRIPTION);
        character.setImageUrl(CHARACTER_IMAGE_URL);

        house.setId(HOUSE_ID);
        house.setName(HOUSE_NAME);
        house.setImageUrl(HOUSE_IMAGE_URL);

    }

    /**
     * Starts CharacterDetailActivity with a stub character model.
     *
     * @param activityTestRule the rule that launch the activity
     */
    protected void startCharacterDetailActiviy(ActivityTestRule activityTestRule) {
        Intent intent = new Intent();
        intent.putExtra(CharacterDetailActivity.EXTRA_CHARACTER, Parcels.wrap(character));

        activityTestRule.launchActivity(intent);
    }

    /**
     * Starts HouseDetailActivity with a stub house model.
     *
     * @param activityTestRule the rule that launch the activity
     */
    protected void startHouseDetailActiviy(ActivityTestRule activityTestRule) {
        Intent intent = new Intent();
        intent.putExtra(HouseDetailActivity.EXTRA_HOUSE, Parcels.wrap(house));

        activityTestRule.launchActivity(intent);
    }

    protected static ViewInteraction matchToolbarTitle(CharSequence title) {
        return onView(isAssignableFrom(Toolbar.class)).check(matches(withToolbarTitle(is(title))));
    }

    private static Matcher<Object> withToolbarTitle(final Matcher<CharSequence> textMatcher) {
        return new BoundedMatcher<Object, Toolbar>(Toolbar.class) {
            @Override
            public boolean matchesSafely(Toolbar toolbar) {
                return textMatcher.matches(toolbar.getTitle());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with toolbar title: ");
                textMatcher.describeTo(description);
            }
        };
    }
}
