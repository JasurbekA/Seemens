package uz.fizmasoft.seemens.data.local

import com.google.gson.annotations.SerializedName

data class Delivery(

    val date: String,
    @SerializedName("return") val returnType: String
)