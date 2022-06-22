package cl.stgoneira.ejemploespresso

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.stgoneira.ejemploespresso.ui.MainActivity
import cl.stgoneira.ejemploespresso.ui.recycler.adapter.LibrosAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val mainActivity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun boton_muestra_texto_saludo() {
        // presiona el boton
        onView(withId(R.id.button)).perform(click())

        // verifica que aparezca el texto
        onView(withId(R.id.textview)).check(matches(withText("Texto de Salida")))
    }

    @Test
    fun muestra_detalle_libro() {
        // presiona el boton ver libros
        onView(withId(R.id.btnVerLibros)).perform(click())
        // presiona elemento num 2 del recycler view
        onView(withId(R.id.recyclerview))
            .perform(
                actionOnItemAtPosition<LibrosAdapter.ViewHolder>(
                    2,
                    click()
                ))
        // revisa el detalle del libro mostrado
        onView(withId(R.id.tvDetalleTitulo))
            .check(matches(withText("The Divine Comedy")))
    }


}