package uz.fizmasoft.seemens.data.local

data class Values (

	val id : Int,
	val name : String,
	val color : String,
	val currency : List<Currency>,
	val description : String,
	val reference : String,
	val in_stock : Boolean,
	val is_default : Boolean,
	val sort_order : Int,
	val images : List<String>
)