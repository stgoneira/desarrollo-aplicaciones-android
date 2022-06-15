package cl.stgoneira.ejemplomvvm.model.api

import com.google.gson.annotations.SerializedName

data class LibroModel(
    val id:Int,
    @SerializedName("author") val autor:String,
    @SerializedName("country") val pais:String,
    @SerializedName("imageLink") val imagen:String,
    @SerializedName("language") val lenguaje:String,
    @SerializedName("link") val enlace:String,
    @SerializedName("pages") val paginas:Int,
    @SerializedName("title") val titulo:String,
    @SerializedName("year") val anno:Int,
    @SerializedName("price") val precio:Int,
    @SerializedName("lastPrice") val ultimoPrecio:Int,
    @SerializedName("delivery") val despacho:Boolean
)