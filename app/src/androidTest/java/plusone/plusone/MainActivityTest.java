package plusone.plusone;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertNotNull;


import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.security.spec.RSAOtherPrimeInfo;

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

    @Before
    public void setUp() throws Exception {
        Intents.init();
        myActivity=myActivityTestRule.getActivity();
    }

   @Test
    public void buttonCreateEventWorks(){
        myActivityTestRule.launchActivity(new Intent());
        assertNotNull(myActivity.findViewById(R.id.imageButtonAddEvent));

        onView(withId(R.id.imageButtonAddEvent)).perform(click());
        Activity secondActivity = getInstrumentation().waitForMonitorWithTimeout(monitorCreateEvent,5000);
        assertNotNull(secondActivity);
        secondActivity.finish();

    }
    @Test
    public void escribirEnSearchBar(){
        myActivityTestRule.launchActivity(new Intent());

        String texto ="Parece que funciona";

        onView(withId(R.id.editTextSearchHome)).check(matches((isDisplayed())));
        onView(withId(R.id.editTextSearchHome)).perform(clearText(),typeText(texto),closeSoftKeyboard());
        onView(withId(R.id.editTextSearchHome)).toString().compareTo(texto);
        //closeSoftKeyboard();
    }
    /*@Test
    public void createEventAndItIsShown(){
        myActivityTestRule.launchActivity(new Intent());


        onView(withId(R.id.imageButtonAddEvent)).perform(click());
        onView(withId(R.id.eventName)).check(matches((isDisplayed())));
        onView(withId(R.id.eventName)).perform(clearText(),typeText("Evento de testeo"),closeSoftKeyboard());
        onView(withId(R.id.description)).check(matches((isDisplayed())));
        onView(withId(R.id.description)).perform(clearText(),typeText("Que dise loco"),closeSoftKeyboard());
        onView(withId(R.id.end)).check(matches((isDisplayed())));
        onView(withId(R.id.end)).perform(clearText(),typeText("21-12-2017-14:30"),closeSoftKeyboard());
        onView(withId(R.id.peopleNeeded)).check(matches((isDisplayed())));
        onView(withId(R.id.peopleNeeded)).perform(clearText(),typeText("4"),closeSoftKeyboard());
        onView(withId(R.id.createEventButton)).check(matches((isDisplayed())));
        onView(withId(R.id.createEventButton)).perform(scrollTo(),click());


        //assertNotNull(myActivity.findViewById(R.id.buttonSportsEvents));
        //onView(withId(R.id.buttonSportsEvents)).perform(click());
        //intended(hasComponent(EventListCardView.class.getName()));
        *//*//**//*onView(withId(R.id.buttonSportsEvents)).perform(closeSoftKeyboard());
        Activity secondActivity = getInstrumentation().waitForMonitorWithTimeout(monitorSportsEvents,5000);
        assertNotNull(secondActivity);
        secondActivity.finish();*//*

    }*/
    @Test
    public void buttonCreateEventWorks2(){
        //myActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.imageButtonAddEvent)).perform(click());
        intended(hasComponent(EventCreateActivity.class.getName()));
    }
    @Test
    public void spinnerWorksEventCreate(){
       myActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.imageButtonAddEvent)).perform(click());
        onView(withId(R.id.eventType)).perform(click());
        onData(allOf(is(instanceOf(String.class)),is("Others"))).perform(click());
        onView(withId(R.id.eventType)).check(matches(withSpinnerText("Others")));

    }
    @Test
    public void checkThatWeAreOnMainActivity(){
        myActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.buttonSportsEvents)).check(matches(withText("Sports Events")));
        onView(withId(R.id.buttonFood)).check(matches(withText("Food")));
        onView(withId(R.id.buttonEntertainment)).check(matches(withText("Entertainement")));
        onView(withId(R.id.buttonParty)).check(matches(withText("Party")));
        onView(withId(R.id.buttonLearning)).check(matches(withText("Learning")));
        onView(withId(R.id.buttonOthers)).check(matches(withText("Others")));
    }
    @After
    public void tearDown() throws Exception {
        Intents.release();
        myActivity=null;
    }

}