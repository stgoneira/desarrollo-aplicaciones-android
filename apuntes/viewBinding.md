# View Binding 

https://developer.android.com/topic/libraries/view-binding


## Configuration :: build.gradle

```
android {
    ...
    buildFeatures {
        viewBinding true
    }
}
```

## Code Example :: activity_main.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/textView1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        tools:text="textview1"
    />
    <TextView
        android:id="@+id/textView2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        tools:text="textview2"
    />
</LinearLayout>
```

## Code Example :: MainActivity.kt 

```kotlin 
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView1.text = "Hola Mundillo Uno!!!"
        binding.textView2.text = "Hola Mundo #2!!!!!"
    }
}
```




