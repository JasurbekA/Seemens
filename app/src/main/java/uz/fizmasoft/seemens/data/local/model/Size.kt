package uz.fizmasoft.seemens.data.local.model

data class Size (
	val id : Int,
	val name : String,
	val slug : String,
	val type : String,
	val values : List<Values>
)