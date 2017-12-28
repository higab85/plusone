package plusone.plusone;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
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
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
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
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.security.spec.RSAOtherPrimeInfo;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;
import static plusone.plusone.R.id.event_list_recycler_view;

/**
 * Created by Javier on 14/11/2017.
 */
@RunWith(AndroidJUnit4.class)
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
        assertNotNull(myActivity.findViewById(R.id.imageButtonAddEvent2));

        onView(withId(R.id.imageButtonAddEvent2)).perform(click());
        Activity secondActivity = getInstrumentation().waitForMonitorWithTimeout(monitorCreateEvent,5000);
        assertNotNull(secondActivity);
        secondActivity.finish();

    }
    @Test
    public void escribirEnSearchBar(){
        myActivityTestRule.launchActivity(new Intent());

        String texto ="Esto va que flipas,no?";

        onView(withId(R.id.editTextSearchHome)).check(matches((isDisplayed())));
        onView(withId(R.id.editTextSearchHome)).perform(clearText(),typeText(texto),closeSoftKeyboard());
        onView(withId(R.id.editTextSearchHome)).toString().compareTo(texto);
        //closeSoftKeyboard();
    }

    @Test
    public void buttonCreateEventWorks2(){
        //myActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.imageButtonAddEvent2)).perform(click());
        intended(hasComponent(EventCreateActivity.class.getName()));
    }
    @Test
    public void spinnerWorksEventCreate(){
       myActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.imageButtonAddEvent2)).perform(click());
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
   /* @Test
   public void createEventConLOCALIZACION(){
        myActivityTestRule.launchActivity(new Intent());

        onView(withId(R.id.imageButtonAddEvent2)).perform(click());

        onView(withId(R.id.eventName)).check(matches((isDisplayed())));
        onView(withId(R.id.eventName)).perform(clearText(),typeText("Evento de testeo2"),closeSoftKeyboard());
        onView(withId(R.id.description)).check(matches((isDisplayed())));
        onView(withId(R.id.description)).perform(clearText(),typeText("Que dise loco"),closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.textViewTimeStart), withText("Starts at"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton3.perform(scrollTo(), click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton4.perform(scrollTo(), click());



        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.eventEndsAtButton), withText("Finishes at"), isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton6.perform(scrollTo(), click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton7.perform(scrollTo(), click());

        onView(withId(R.id.addAddressButon)).perform(click());

        String texto ="Estadio Santiago Bernabéu";

        onView(withId(R.id.SearchAddress)).check(matches((isDisplayed())));
        onView(withId(R.id.SearchAddress)).perform(clearText(),replaceText(texto),closeSoftKeyboard());
        onView(withId(R.id.SearchAddress)).toString().compareTo(texto);
        onView(withId(R.id.SearchMapButton)).perform(click());

        UiDevice uiDevice = UiDevice.getInstance(getInstrumentation());
        UiObject mMarker1 = uiDevice.findObject(new UiSelector().descriptionContains("Your Event Location :" + texto));
        try {
            mMarker1.click();
        } catch (UiObjectNotFoundException e) {

            e.printStackTrace();
        }

        onView(withId(R.id.eventType)).perform(click());
        onData(allOf(is(instanceOf(String.class)),is("Learning"))).perform(click());
        onView(withId(R.id.peopleNeeded)).check(matches((isDisplayed())));
        onView(withId(R.id.peopleNeeded)).perform(clearText(),typeText("4"),closeSoftKeyboard());



        onView(withId(R.id.createEventButton)).check(matches((isCompletelyDisplayed())));
        onView(withId(R.id.createEventButton)).perform(closeSoftKeyboard()).perform(click());

        onView(withId(R.id.buttonLearning)).check(matches((isCompletelyDisplayed())));
        onView(withId(R.id.buttonLearning)).perform(closeSoftKeyboard()).perform(click());

    }*/
    /*@Test
    public void CreateEventSinLOCALIZACION(){
        myActivityTestRule.launchActivity(new Intent());

        onView(withId(R.id.imageButtonAddEvent2)).perform(click());

        onView(withId(R.id.eventName)).check(matches((isDisplayed())));
        onView(withId(R.id.eventName)).perform(clearText(),typeText("Evento de testeo2"),closeSoftKeyboard());
        onView(withId(R.id.description)).check(matches((isDisplayed())));
        onView(withId(R.id.description)).perform(clearText(),typeText("Hola soy Skynet y me he colado dentro de PlusOne, porfavor no digais nada"),closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.textViewTimeStart), withText("Starts at"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton3.perform(scrollTo(), click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton4.perform(scrollTo(), click());



        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.eventEndsAtButton), withText("Finishes at"), isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton6.perform(scrollTo(), click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton7.perform(scrollTo(), click());


        onView(withId(R.id.eventType)).perform(click());
        onData(allOf(is(instanceOf(String.class)),is("Learning"))).perform(click());
        onView(withId(R.id.peopleNeeded)).check(matches((isDisplayed())));
        onView(withId(R.id.peopleNeeded)).perform(clearText(),typeText("4"),closeSoftKeyboard());



        onView(withId(R.id.createEventButton)).check(matches((isCompletelyDisplayed())));
        onView(withId(R.id.createEventButton)).perform(closeSoftKeyboard()).perform(click());


    }*/
    @Test
    public void startEventFunciona() {
        myActivityTestRule.launchActivity(new Intent());

        onView(withId(R.id.imageButtonAddEvent2)).perform(click());
        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.textViewTimeStart), withText("Starts at"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton3.perform(scrollTo(), click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton4.perform(scrollTo(), click());
    }
    @Test
    public void endEventFunciona() {
        myActivityTestRule.launchActivity(new Intent());

        onView(withId(R.id.imageButtonAddEvent2)).perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.eventEndsAtButton), withText("Finishes at"), isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton6.perform(scrollTo(), click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton7.perform(scrollTo(), click());
    }
    @Test
    public void startEventYEndEventFunciona() {
        myActivityTestRule.launchActivity(new Intent());

        onView(withId(R.id.imageButtonAddEvent2)).perform(click());
        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.textViewTimeStart), withText("Starts at"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton3.perform(scrollTo(), click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton4.perform(scrollTo(), click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.eventEndsAtButton), withText("Finishes at"), isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton6.perform(scrollTo(), click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton7.perform(scrollTo(), click());
    }
    @Test
    public void escribirEnSearchTextBar(){
        myActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.imageButtonAddEvent2)).perform(click());

        String texto ="Que pasa Hulio!!";
        onView(withId(R.id.eventName)).check(matches((isDisplayed())));
        onView(withId(R.id.eventName)).perform(clearText(),typeText(texto),closeSoftKeyboard());
        onView(withId(R.id.eventName)).toString().compareTo(texto);
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
        myActivity=null;
    }

}