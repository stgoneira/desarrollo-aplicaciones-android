# Picasso 

https://square.github.io/picasso/ 

## Permisos :: AndroidManifest.xml

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

## Dependencias :: build.gradle

```
implementation 'com.squareup.picasso:picasso:2.8'
```

## Code 

```kotlin
val imageView  = findViewById<ImageView>(R.id.imageView)
val imgUrl     = "https://play-lh.googleusercontent.com/y_-anVKl3ID25Je02J1dseqlAm41N8pwI-Gad7aDxPIPss3d7hUYF8c08SNCtwSPW5g"

Picasso.get()
    .load(imgUrl )
    .into( imageView )
```
