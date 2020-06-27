package uz.fizmasoft.seemens.di.activity

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import uz.fizmasoft.seemens.data.network.SeemensApiService
import uz.fizmasoft.seemens.di.vm.ViewModelKey
import uz.fizmasoft.seemens.ui.MainActivityViewModel
import javax.inject.Singleton

@Module
abstract class MainActivityModule {

    companion object {
        @Provides
        @JvmStatic
        fun provideSeemensApi(retrofit: Retrofit) : SeemensApiService =
            SeemensApiService.invoke(retrofit)
    }


    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainActivityViewModel): ViewModel


}