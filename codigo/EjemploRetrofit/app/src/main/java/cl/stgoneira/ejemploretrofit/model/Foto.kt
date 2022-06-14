package cl.stgoneira.ejemploretrofit.model

import com.google.gson.annotations.SerializedName

data class Foto (
    val albumId:Int
    , val id:Int
    , @SerializedName("title")          val titulo:String
    , @SerializedName("url")            val imgUrl:String
    , @SerializedName("thumbnailUrl")   val miniatura:String
)