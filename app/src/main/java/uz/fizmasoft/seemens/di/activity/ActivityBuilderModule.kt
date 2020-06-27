package uz.fizmasoft.seemens.di.activity



import dagger.Module
import dagger.android.ContributesAndroidInjector
import uz.fizmasoft.seemens.ui.MainActivity

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun contributeMainActivity(): MainActivity

}