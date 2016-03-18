package es.npatarino.android.gotchallenge;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.npatarino.android.gotchallenge.view.HomeActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Tests for SearchView
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SearchTest extends TestStub {
    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void searchCharacter() {
        String name = CHARACTER_NAME.split(" ")[0];
        name = name.substring(1, name.length() - 1).toLowerCase();

        onView(withId(R.id.action_search)).perform(click());

        onView(isAssignableFrom(EditText.class)).perform(typeText(name));

        onView(withId(R.id.characters)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.tv_name)).check(matches(withText(CHARACTER_NAME)));
    }

    @Test
    public void searchViewNotDisplayedInHouses() {
        onView(withText("Houses")).perform(click());
        onView(withId(R.id.action_search)).check(doesNotExist());
    }
}
