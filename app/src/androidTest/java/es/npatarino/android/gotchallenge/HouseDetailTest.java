package es.npatarino.android.gotchallenge;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.npatarino.android.gotchallenge.view.HouseDetailActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsAnything.anything;

/**
 * Test for house detail
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class HouseDetailTest extends TestStub {
    @Rule
    public ActivityTestRule<HouseDetailActivity> mActivityRule = new ActivityTestRule<>(HouseDetailActivity.class, true, false);

    @Test
    public void detailTitle() {
        startHouseDetailActiviy(mActivityRule);

        matchToolbarTitle(HOUSE_NAME).check(matches(isDisplayed()));
    }

    @Test
    public void gridViewDisplaysAllCharacters() {
        startHouseDetailActiviy(mActivityRule);

        //onView(withId(R.id.grid)).check(matches(isDisplayed()));
        onData(anything())
                .inAdapterView(withId(R.id.grid))
                .atPosition(HOUSE_CHARACTERS_NUM - 1)
                .check(matches(isDisplayed()));
    }
}
