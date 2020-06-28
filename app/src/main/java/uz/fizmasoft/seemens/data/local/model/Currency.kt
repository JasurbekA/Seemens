package uz.fizmasoft.seemens.data.local.model

data class Currency(
    val price: Int,
    val old_price: Int,
    val discount: Int,
    val currency: String
)