package uz.fizmasoft.seemens

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import uz.fizmasoft.seemens.di.app.DaggerAppComponent

class SeemensApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>  =
        DaggerAppComponent.builder().application(this).build()

}