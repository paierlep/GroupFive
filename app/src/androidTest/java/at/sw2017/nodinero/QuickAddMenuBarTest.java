package at.sw2017.nodinero;

import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;


@RunWith(AndroidJUnit4.class)
public class QuickAddMenuBarTest extends AbstractNoDineroInstrumentedTest {

    @Test
    public void checkifExists() {
        //onView(allOf(withId(R.id.fragment_quick_add_menu)).check(matches(isDisplayed())));
    }


}
