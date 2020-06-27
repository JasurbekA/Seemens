package uz.fizmasoft.seemens.data.repo

import uz.fizmasoft.seemens.data.network.SeemensApiService
import javax.inject.Inject

public class SeemensRepo @Inject constructor (private val service: SeemensApiService) {
    suspend fun loadResponse() = service.loadSeemensResponse()
}