package es.npatarino.android.gotchallenge;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.npatarino.android.gotchallenge.view.HomeActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Test for houses
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class HousesTest {
    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void testOnStarkClick() {
        onView(withText("Houses")).perform(click());
        onView(withId(R.id.houses)).perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));
        onView(allOf(isAssignableFrom(TextView.class), withParent(isAssignableFrom(Toolbar.class))))
                .check(matches(withText("House Stark")));
    }

    @Test
    public void testStarkHouseCharacters() {
        onView(withText("Houses")).perform(click());
        onView(withId(R.id.houses)).perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));

        onData(withId(R.id.grid)).atPosition(3).atPosition(0).perform(click());

        onView(withId(R.id.tv_name)).check(matches(withText("Sansa Stark")));
    }
}
