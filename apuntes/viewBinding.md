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

## Code Example 

```kotlin
val imageView  = findViewById<ImageView>(R.id.imageView)
val imgUrl     = "https://play-lh.googleusercontent.com/y_-anVKl3ID25Je02J1dseqlAm41N8pwI-Gad7aDxPIPss3d7hUYF8c08SNCtwSPW5g"

Picasso.get()
    .load(imgUrl )
    .into( imageView )
```
