package com.arctouch.codechallenge

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import com.arctouch.codechallenge.flow.movies.MoviesListActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class MoviesListActivityInstrumentedTest {

    @get:Rule
    val rule = ActivityTestRule(MoviesListActivity::class.java)

    @Test
    fun searchBarIsVisibleWhenSearchIconClicked() {
        onView(ViewMatchers.withId(R.id.movieListMainStateSearchButton)).perform(ViewActions.click())

        onView(ViewMatchers.withId(R.id.movieListSearchTextInputLayout)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )

    }

}