package uz.fizmasoft.seemens.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import uz.fizmasoft.seemens.R
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var mainViewModel: MainActivityViewModel

    @Inject
    lateinit var providerFactory: ViewModelProvider.Factory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        initVM()
        requestData()
        observeData()
    }


    private fun initVM() {
        mainViewModel = ViewModelProvider(
            this,
            providerFactory
        )[MainActivityViewModel::class.java]
    }

    private fun requestData() = mainViewModel.loadSeemsResponse()


    private fun observeData() = mainViewModel.seemensResponse.observe(this,
        Observer {
            when (it) {
                is MainActivityViewModel.LoadingResponseState.OnLoading -> println("observeData Loading")
                is MainActivityViewModel.LoadingResponseState.OnSuccess -> println("observeData Success")
                is MainActivityViewModel.LoadingResponseState.OnError -> println("observeData Error")
            }
        })
    
}