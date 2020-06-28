package uz.fizmasoft.seemens.data.local.fake

import androidx.annotation.DrawableRes
import uz.fizmasoft.seemens.R
import kotlin.random.Random

data class SocialMediaData(
    @DrawableRes val resID: Int,
    val link: String
)


val socialMediaData = arrayListOf(
    SocialMediaData(R.drawable.social_facebook, "Facebook link"),
    SocialMediaData(R.drawable.social_ok, "OK link"),
    SocialMediaData(R.drawable.social_telegram, "Telegram link"),
    SocialMediaData(R.drawable.social_viber, "Viber link"),
    SocialMediaData(R.drawable.social_vk, "VK link"),
    SocialMediaData(R.drawable.social_whatsapp, "WhatsApp link")
)


data class ProductAdViewData(
    val imageUrl: String,
    val prodName: String = "Bershka",
    val prodDesc: String = "Nice one",
    val prodPrice: String = "990,556 UZS",
    var isFavorite: Boolean = Random.nextBoolean()
)

fun getProdAddViewData(imageUrls: List<String>): List<ProductAdViewData> =
    imageUrls.map { ProductAdViewData(it) }.toList()