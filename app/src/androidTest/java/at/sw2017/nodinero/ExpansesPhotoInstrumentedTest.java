package at.sw2017.nodinero;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class ExpansesPhotoInstrumentedTest extends AbstractNoDineroInstrumentedTest {


    @Test
    public void takePictureButtonExists() {
        onView(withId(R.id.button_image)).perform(scrollTo()).check(matches(isDisplayed())).perform(click());
    }

    @Test
    public void validateCameraScenario() {

    }


}
