package uz.fizmasoft.seemens.data.network

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Headers
import uz.fizmasoft.seemens.data.local.model.SeemensResponse

interface SeemensApiService {

    @Headers("Mobile-UID: someUidString")
    @GET("v1/products/1/details/")
    suspend fun loadSeemensResponse(): Response<SeemensResponse>


    companion object {
        operator fun invoke(
            retrofit: Retrofit
        ): SeemensApiService = retrofit
            .create(SeemensApiService::class.java)
    }

}