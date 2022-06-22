# Instrumented Test (Espresso)

## app/build.gradle

```
dependencies {

    implementation 'androidx.core:core-ktx:1.7.0'
    implementation 'androidx.appcompat:appcompat:1.4.2'
    implementation 'com.google.android.material:material:1.6.1'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'

    // TEST IMPLEMENTATION
    testImplementation 'junit:junit:4.13.2'

    // ANDROID TEST IMPLEMENTATION

    // Core library
    androidTestImplementation "androidx.test:core-ktx:1.4.0"

    // AndroidJUnitRunner and JUnit Rules
    androidTestImplementation "androidx.test:runner:1.4.0"
    androidTestImplementation "androidx.test:rules:1.4.0"

    // Assertions
    androidTestImplementation "androidx.test.ext:junit-ktx:1.1.3"
    androidTestImplementation "androidx.test.ext:truth:1.4.0"

    // Espresso dependencies
    androidTestImplementation "androidx.test.espresso:espresso-core:3.4.0"
    androidTestImplementation "androidx.test.espresso:espresso-contrib:3.4.0"
    androidTestImplementation "androidx.test.espresso:espresso-intents:3.4.0"
    androidTestImplementation "androidx.test.espresso:espresso-accessibility:3.4.0"
    androidTestImplementation "androidx.test.espresso:espresso-web:3.4.0"
    androidTestImplementation "androidx.test.espresso.idling:idling-concurrent:3.4.0"

    // The following Espresso dependency can be either "implementation",
    // or "androidTestImplementation", depending on whether you want the
    // dependency to appear on your APK’"s compile classpath or the test APK
    // classpath.
    androidTestImplementation "androidx.test.espresso:espresso-idling-resource:3.4.0"

}
```

## androidTest package 

```kotlin
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
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
}
```

## Activity

```kotlin
class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            binding.textview.text = "Texto de Salida"
        }
    }
}
```

## Layout 

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:text="Mi Botón"
    />

    <TextView
        android:id="@+id/textview"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textAppearance="@style/TextAppearance.AppCompat.Large"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/button"
        app:layout_constraintVertical_bias="0.1"
        tools:text="Salida" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
