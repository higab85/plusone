package plusone.plusone;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertNotNull;


import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by Usuario on 14/11/2017.
 */
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> myActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity myActivity= null;

    Instrumentation.ActivityMonitor monitorCreateEvent = getInstrumentation().addMonitor(EventCreateActivity.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitorSportsEvents = getInstrumentation().addMonitor(EventListInfoAdapter.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        myActivity=myActivityTestRule.getActivity();
    }
    @Test
    public void buttonCreateEventWorks(){
        assertNotNull(myActivity.findViewById(R.id.imageButtonAddEvent));

        onView(withId(R.id.imageButtonAddEvent)).perform(click());
        Activity secondActivity = getInstrumentation().waitForMonitorWithTimeout(monitorCreateEvent,5000);
        assertNotNull(secondActivity);
        secondActivity.finish();
    }
    @Test
    public void escribirEnSearchBar(){
        String texto ="Parece que funciona";

        onView(withId(R.id.editTextSearchHome)).check(matches((isDisplayed())));
        onView(withId(R.id.editTextSearchHome)).perform(clearText(),typeText(texto));
        onView(withId(R.id.editTextSearchHome)).toString().compareTo(texto);
    }
    @Test
    public void buttonSportsEventsWorks(){
        assertNotNull(myActivity.findViewById(R.id.buttonSportsEvents));

        /*onView(withId(R.id.buttonSportsEvents)).perform(closeSoftKeyboard());
        Activity secondActivity = getInstrumentation().waitForMonitorWithTimeout(monitorSportsEvents,5000);
        assertNotNull(secondActivity);
        secondActivity.finish();*/
    }

    @After
    public void tearDown() throws Exception {
        myActivity=null;
    }

}