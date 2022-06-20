package data.ws

import com.google.gson.annotations.SerializedName

data class Tarea(
    @SerializedName("userId") val usuarioId:Int,
    val id:Int,
    @SerializedName("title") val titulo:String,
    @SerializedName("completed") val completada:Boolean
)
