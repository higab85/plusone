package plusone.plusone;

import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Javier on 12/12/2017.
 */

public class MapsActivityTest {
    @Rule
    public ActivityTestRule<MapsActivity> myActivityTestRule = new ActivityTestRule<MapsActivity>(MapsActivity.class);

    private MapsActivity myActivity= null;
    @Before
    public void setUp() throws Exception {
        Intents.init();
        myActivity=myActivityTestRule.getActivity();
    }
    @Test
    public void seEscribeEnLaBarra2(){
        myActivityTestRule.launchActivity(new Intent());

        String texto ="Calle wallaby 42 sydney";

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
    }
    @Test
    public void seEscribeEnLaBarra(){
        myActivityTestRule.launchActivity(new Intent());

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
    }




    @After
    public void tearDown() throws Exception {
        Intents.release();
        myActivity=null;
    }
}
