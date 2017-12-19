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
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertNotNull;


import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

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
 * Created by Javier on 07/12/2017.
 */
public class LoginActivityUITest {
    @Rule
    public ActivityTestRule<LoginActivity> myActivityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    //private LoginActivity myActivity= null;
    @Before
    public void setUp() throws Exception {
        Intents.init();
       // myActivity=myActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
       // myActivity=null;
    }
    @Test
    public void userYPaswCorrect(){
        myActivityTestRule.launchActivity(new Intent());

        onView(withId(R.id.user)).check(matches(isDisplayed()));
        onView(withId(R.id.user)).perform(clearText(),typeText("javier@asenjo.com"),closeSoftKeyboard());
        onView(withId(R.id.password)).check(matches(isDisplayed()));
        onView(withId(R.id.password)).perform(clearText(),typeText("javicraft"),closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }
    @Test
    public void UserIncorrect(){

        myActivityTestRule.launchActivity(new Intent());

        onView(withId(R.id.user)).check(matches(isDisplayed()));
        onView(withId(R.id.user)).perform(clearText(),typeText("itsATrap@test.com"),closeSoftKeyboard());
        onView(withId(R.id.password)).check(matches(isDisplayed()));
        onView(withId(R.id.password)).perform(clearText(),typeText("test"),closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        onView(withId(R.id.password)).check(matches(hasErrorText("Wrong password")));

    }
    @Test
    public void PasswordIncorrect(){

        myActivityTestRule.launchActivity(new Intent());

        onView(withId(R.id.user)).check(matches(isDisplayed()));
        onView(withId(R.id.user)).perform(clearText(),replaceText("Este test lo he hecho, perdón, hemos"),closeSoftKeyboard());
        onView(withId(R.id.password)).check(matches(isDisplayed()));
        onView(withId(R.id.password)).perform(clearText(),typeText("itsATrap"),closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        onView(withId(R.id.password)).check(matches(hasErrorText("Wrong password")));

    }
    @Test
    public void UserEmpty(){

        myActivityTestRule.launchActivity(new Intent());


        onView(withId(R.id.password)).check(matches(isDisplayed()));
        onView(withId(R.id.password)).perform(clearText(),typeText("test"),closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        onView(withId(R.id.password)).check(matches(hasErrorText("Wrong password")));

    }
    @Test
    public void PasswordEmpty(){

        myActivityTestRule.launchActivity(new Intent());

        onView(withId(R.id.user)).check(matches(isDisplayed()));
        onView(withId(R.id.user)).perform(clearText(),typeText("testMalo@test.com"),closeSoftKeyboard());

        onView(withId(R.id.loginButton)).perform(click());

        onView(withId(R.id.password)).check(matches(hasErrorText("Wrong password")));

    }
    @Test
    public void EverythingIsEmpty(){

        myActivityTestRule.launchActivity(new Intent());

        onView(withId(R.id.loginButton)).perform(click());

        onView(withId(R.id.password)).check(matches(hasErrorText("Wrong password")));

    }
    @Test
    public void crearEventoSinLocalizacion() {
        ViewInteraction appCompatAutoCompleteTextView = onView(
                withId(R.id.user));
        appCompatAutoCompleteTextView.perform(scrollTo(), click());

        ViewInteraction appCompatAutoCompleteTextView2 = onView(
                withId(R.id.user));
        appCompatAutoCompleteTextView2.perform(scrollTo(), replaceText("javier@asenjo.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText = onView(
                withId(R.id.password));
        appCompatEditText.perform(scrollTo(), replaceText("javicraft"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                Matchers.allOf(withId(R.id.loginButton), withText("Sign in"),
                        withParent(Matchers.allOf(withId(R.id.user_login_form),
                                withParent(withId(R.id.login_form))))));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction appCompatImageButton = onView(
                Matchers.allOf(withId(R.id.imageButtonAddEvent2), isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                Matchers.allOf(withId(R.id.eventName), isDisplayed()));
        appCompatEditText2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                Matchers.allOf(withId(R.id.eventName), isDisplayed()));
        appCompatEditText3.perform(replaceText("EventoCreadoPorTest(EvSinLoca)"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                Matchers.allOf(withId(R.id.description), isDisplayed()));
        appCompatEditText4.perform(replaceText("I like turtles"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                Matchers.allOf(withId(R.id.textViewTimeStart), withText("Starts at"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                Matchers.allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton3.perform(scrollTo(), click());

        ViewInteraction appCompatButton4 = onView(
                Matchers.allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton4.perform(scrollTo(), click());

        ViewInteraction appCompatButton5 = onView(
                Matchers.allOf(withId(R.id.eventEndsAtButton), withText("Finishes at"), isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                Matchers.allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton6.perform(scrollTo(), click());

        ViewInteraction appCompatButton7 = onView(
                Matchers.allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton7.perform(scrollTo(), click());

        ViewInteraction appCompatSpinner = onView(
                Matchers.allOf(withId(R.id.eventType), isDisplayed()));
        appCompatSpinner.perform(click());

        ViewInteraction appCompatTextView = onView(
                Matchers.allOf(withId(android.R.id.text1), withText("Learning"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                Matchers.allOf(withId(R.id.peopleNeeded), isDisplayed()));
        appCompatEditText6.perform(replaceText("2"), closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                Matchers.allOf(withId(R.id.peopleNeeded), withText("2"), isDisplayed()));
        appCompatEditText7.perform(pressImeActionButton());

        ViewInteraction appCompatButton8 = onView(
                Matchers.allOf(withId(R.id.createEventButton), withText("Create"), isDisplayed()));
        appCompatButton8.perform(click());

    }
    @Test
    public void muestraUnEventoEnCardView() {
        ViewInteraction appCompatAutoCompleteTextView = onView(
                withId(R.id.user));
        appCompatAutoCompleteTextView.perform(scrollTo(), click());

        ViewInteraction appCompatAutoCompleteTextView2 = onView(
                withId(R.id.user));
        appCompatAutoCompleteTextView2.perform(scrollTo(), replaceText("javier@asenjo.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText = onView(
                withId(R.id.password));
        appCompatEditText.perform(scrollTo(), replaceText("javicraft"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                Matchers.allOf(withId(R.id.loginButton), withText("Sign in"),
                        withParent(Matchers.allOf(withId(R.id.user_login_form),
                                withParent(withId(R.id.login_form))))));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction appCompatButton2 = onView(
                Matchers.allOf(withId(R.id.buttonSportsEvents), withText("Sports Events"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction recyclerView = onView(
                Matchers.allOf(withId(R.id.event_list_recycler_view), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction textView = onView(
                Matchers.allOf(withId(R.id.allInfoEventName), withText("PruebaDeportes"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("PruebaDeportes")));

        ViewInteraction textView2 = onView(
                Matchers.allOf(withId(R.id.allInfoDescription), withText("blabla"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class),
                                        1),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("blabla")));

    }
   /* @Test
    public void crearEventoYMostrarlo() {
        ViewInteraction appCompatAutoCompleteTextView = onView(
                withId(R.id.user));
        appCompatAutoCompleteTextView.perform(scrollTo(), click());

        ViewInteraction appCompatAutoCompleteTextView2 = onView(
                withId(R.id.user));
        appCompatAutoCompleteTextView2.perform(scrollTo(), replaceText("test@test.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText = onView(
                withId(R.id.password));
        appCompatEditText.perform(scrollTo(), replaceText("test"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                Matchers.allOf(withId(R.id.loginButton), withText("Sign in"),
                        withParent(Matchers.allOf(withId(R.id.user_login_form),
                                withParent(withId(R.id.login_form))))));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction appCompatImageButton = onView(
                Matchers.allOf(withId(R.id.imageButtonAddEvent2), isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                Matchers.allOf(withId(R.id.eventName), isDisplayed()));
        appCompatEditText2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                Matchers.allOf(withId(R.id.eventName), isDisplayed()));
        appCompatEditText3.perform(replaceText("EventoCreadoPorTest(CrearEveYMostrar)"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                Matchers.allOf(withId(R.id.description), isDisplayed()));
        appCompatEditText4.perform(replaceText("Descripcion de prueba"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                Matchers.allOf(withId(R.id.textViewTimeStart), withText("Starts at"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                Matchers.allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton3.perform(scrollTo(), click());

        ViewInteraction appCompatButton4 = onView(
                Matchers.allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton4.perform(scrollTo(), click());

        ViewInteraction appCompatButton5 = onView(
                Matchers.allOf(withId(R.id.eventEndsAtButton), withText("Finishes at"), isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                Matchers.allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton6.perform(scrollTo(), click());

        ViewInteraction appCompatButton7 = onView(
                Matchers.allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton7.perform(scrollTo(), click());

        ViewInteraction appCompatSpinner = onView(
                Matchers.allOf(withId(R.id.eventType), isDisplayed()));
        appCompatSpinner.perform(click());

        ViewInteraction appCompatTextView = onView(
                Matchers.allOf(withId(android.R.id.text1), withText("Learning"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                Matchers.allOf(withId(R.id.peopleNeeded), isDisplayed()));
        appCompatEditText6.perform(replaceText("2"), closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                Matchers.allOf(withId(R.id.peopleNeeded), withText("2"), isDisplayed()));
        appCompatEditText7.perform(pressImeActionButton());

        ViewInteraction appCompatButton8 = onView(
                Matchers.allOf(withId(R.id.createEventButton), withText("Create"), isDisplayed()));
        appCompatButton8.perform(click());

        ViewInteraction appCompatButton9 = onView(
                Matchers.allOf(withId(R.id.buttonLearning), withText("Learning"), isDisplayed()));
        appCompatButton9.perform(click());

        ViewInteraction recyclerView = onView(
                Matchers.allOf(withId(R.id.event_list_recycler_view), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(3, click()));

        ViewInteraction textView = onView(
                Matchers.allOf(withId(R.id.allInfoEventName), withText("EventoCreadoPorTest(CrearEveYMostrar)"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("EventoCreadoPorTest(CrearEveYMostrar)")));

        ViewInteraction textView2 = onView(
                Matchers.allOf(withId(R.id.allInfoDescription), withText("Descripcion de prueba"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class),
                                        1),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("Descripcion de prueba")));

    }*/
    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
   /* @Test
    public void escribirEnSearchBar(){
        myActivityTestRule.launchActivity(new Intent());

        String texto ="Parece que funciona";

        onView(withId(R.id.editTextSearchHome)).check(matches((isDisplayed())));
        onView(withId(R.id.editTextSearchHome)).perform(clearText(),typeText(texto),closeSoftKeyboard());
        onView(withId(R.id.editTextSearchHome)).toString().compareTo(texto);
        //closeSoftKeyboard();
    }*/
}
