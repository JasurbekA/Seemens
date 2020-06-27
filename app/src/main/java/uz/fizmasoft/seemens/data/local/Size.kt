package uz.fizmasoft.seemens.data.local
data class Size (
	val id : Int,
	val name : String,
	val slug : String,
	val type : String,
	val values : List<Values>
)