package cl.stgoneira.ejemplopicasso

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView   = findViewById<ImageView>(R.id.imageView)
        val imgUrl      = "https://play-lh.googleusercontent.com/y_-anVKl3ID25Je02J1dseqlAm41N8pwI-Gad7aDxPIPss3d7hUYF8c08SNCtwSPW5g"

        Picasso.get()
            .load( imgUrl )
            .into( imageView )
    }
}