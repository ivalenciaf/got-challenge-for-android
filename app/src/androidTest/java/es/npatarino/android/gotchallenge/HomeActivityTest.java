package es.npatarino.android.gotchallenge;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.npatarino.android.gotchallenge.view.HomeActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Test for HomeActivity
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomeActivityTest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void charactersAreDisplayed() {
        onView(withId(R.id.characters)).check(matches(isDisplayed()));
    }

    @Test
    public void housesAreDisplayed() {
        onView(withText(TestStub.TAB_HOUSES)).perform(click());
        onView(withId(R.id.houses)).check(matches(isDisplayed()));
    }
}
