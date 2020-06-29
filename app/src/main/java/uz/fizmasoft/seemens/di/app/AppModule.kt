package uz.fizmasoft.seemens.di.app

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.fizmasoft.seemens.data.local.Constants
import javax.inject.Singleton

@Module
class AppModule {
    companion object {
        @Singleton
        @Provides
        @JvmStatic
        fun provideRetrofitInstance(): Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}