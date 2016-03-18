package es.npatarino.android.gotchallenge;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.npatarino.android.gotchallenge.view.DetailActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Test for character detail.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class DetailActivityTest extends TestStub {

    @Rule
    public ActivityTestRule<DetailActivity> mActivityRule = new ActivityTestRule<>(DetailActivity.class, true, false);

    @Test
    public void detailTitle() {
        startCharacterDetailActiviy(mActivityRule);

        matchToolbarTitle(CHARACTER_NAME).check(matches(isDisplayed()));
    }

    @Test
    public void detailName() {
        startCharacterDetailActiviy(mActivityRule);

        onView(withId(R.id.tv_name)).check(matches(withText(CHARACTER_NAME)));
    }

    @Test
    public void detailDescription() {
        startCharacterDetailActiviy(mActivityRule);

        onView(withId(R.id.tv_description)).check(matches(withText(CHARACTER_DESCRIPTION)));
    }
}
