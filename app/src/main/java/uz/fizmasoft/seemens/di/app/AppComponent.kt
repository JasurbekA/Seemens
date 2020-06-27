package uz.fizmasoft.seemens.di.app

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import uz.fizmasoft.seemens.SeemensApplication
import uz.fizmasoft.seemens.di.activity.ActivityBuilderModule
import uz.fizmasoft.seemens.di.vm.ViewModelFactoryModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityBuilderModule::class,
        ViewModelFactoryModule::class

    ]
)
interface AppComponent : AndroidInjector<SeemensApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}