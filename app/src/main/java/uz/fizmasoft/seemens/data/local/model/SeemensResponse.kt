package uz.fizmasoft.seemens.data.local.model

import uz.fizmasoft.seemens.data.local.model.Delivery
import uz.fizmasoft.seemens.data.local.model.Details

data class SeemensResponse (
	val details : Details,
	val delivery : Delivery
)