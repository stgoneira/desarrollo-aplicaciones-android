package cl.aiep.android.anchorbooks

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import cl.aiep.android.anchorbooks.adapter.LibroAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val mainActivity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    @LargeTest
    fun prueba_click_recycler_muestra_detalle() {
        // hace click sobre el 2do elemento del RecyclerView
        onView(withId(R.id.recyclerView))
            .perform(
                scrollToPosition<LibroAdapter.ViewHolder>(4)
            )
            .perform(
                actionOnItemAtPosition<LibroAdapter.ViewHolder>(
                    2,
                    click()
                )
            )

        // revisar que se cargue el titulo
        onView(withId(R.id.tvDetalleTitulo))
            .check(
                matches(
                    withText("The Divine Comedy")
                )
            )
    }
}