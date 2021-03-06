package es.npatarino.android.gotchallenge;

import android.support.test.espresso.contrib.RecyclerViewActions;
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
 * Test flows of application.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class FlowsTest extends TestStub {
    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void clickAtHomeOnCharacter() {
        onView(withId(R.id.characters)).perform(RecyclerViewActions.actionOnItemAtPosition(CHARACTER_POSITION, click()));
        onView(withId(R.id.tv_name)).check(matches(withText(CHARACTER_NAME)));
    }

    @Test
    public void clickAtHomeOnHouse() {
        onView(withText(TAB_HOUSES)).perform(click());
        onView(withId(R.id.houses)).perform(RecyclerViewActions.actionOnItemAtPosition(HOUSE_POSITION, click()));

        matchToolbarTitle(HOUSE_NAME).check(matches(isDisplayed()));
    }

    @Test
    public void clickAtHomeOnHouseAndThenClickOnCharacter() {
        onView(withText(TAB_HOUSES)).perform(click());
        onView(withId(R.id.houses)).perform(RecyclerViewActions.actionOnItemAtPosition(HOUSE_POSITION, click()));

        onView(withId(R.id.characters))
                .perform(RecyclerViewActions.actionOnItemAtPosition(CHARACTER_POSITION_IN_HOUSE, click()));

        matchToolbarTitle(CHARACTER_NAME).check(matches(isDisplayed()));
    }
}
