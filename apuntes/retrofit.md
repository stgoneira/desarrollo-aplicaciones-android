# Retrofit

## Permisos :: AndroidManifest.xml

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

## Dependencias :: build.gradle

```
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
```

## Model 

```kotlin
import com.google.gson.annotations.SerializedName

data class Foto (
    val albumId:Int
    , val id:Int
    , @SerializedName("title")          val titulo:String
    , @SerializedName("url")            val imgUrl:String
    , @SerializedName("thumbnailUrl")   val miniatura:String
)
```

## Service 

```kotlin
import retrofit2.Call
import retrofit2.http.GET

interface FotoService {

    @GET("photos")
    fun listFotos(): Call<List<Foto>>

}
```

## Code 

```
val retrofit = Retrofit.Builder()
	.baseUrl("https://jsonplaceholder.typicode.com/")
	.addConverterFactory(GsonConverterFactory.create())
	.build()

val fotoService = retrofit.create(FotoService::class.java)

val callFotos = fotoService.listFotos()

callFotos.enqueue( object: Callback<List<Foto>> {
	override fun onResponse(call: Call<List<Foto>>, response: Response<List<Foto>>) {
		val fotos = response.body() ?: listOf()		
	}

	override fun onFailure(call: Call<List<Foto>>, t: Throwable) {
		Log.e("RETROFIT", "Retrofit falló al traer las fotos")
	}

})
```