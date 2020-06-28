package uz.fizmasoft.seemens.data.local.model

data class Details (

    val id : Int,
    val name : String,
    val description : String,
    val long_description : String,
    val reference : String,
    val currency : List<Currency>,
    val brand : String,
    val category : String,
    val is_prepaid : Boolean,
    val in_stock : Boolean,
    val is_favorites_added : Boolean,
    val closest_delivery : String,
    val attribute_combination : AttributeCombination,
    val features : List<Features>,
    val images : List<String>
)