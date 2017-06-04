package at.sw2017.nodinero;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by julianheritsch on 04.06.17.
 */

@RunWith(AndroidJUnit4.class)
public class IntroScreenInstrumentedTest {

    //@Rule
    //public ActivityTestRule activityTestRule = new ActivityTestRule(WelcomeActivity.class);

    @Test
    public void checkifExists() {

        onView(withText(R.string.title_activity_welcome)).check(matches(isDisplayed()));
        onView(withText(R.string.TextIntro1)).check(matches(isDisplayed()));
        onView(withText(R.string.title_activity_welcome)).perform(swipeLeft());

        onView(withText(R.string.title_activity_welcome2)).check(matches(isDisplayed()));
        onView(withText(R.string.TextIntro2)).check(matches(isDisplayed()));
        onView(withText(R.string.title_activity_welcome2)).perform(swipeLeft());


    }


}
