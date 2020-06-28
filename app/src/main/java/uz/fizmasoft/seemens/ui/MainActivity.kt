package uz.fizmasoft.seemens.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import uz.fizmasoft.seemens.R
import uz.fizmasoft.seemens.data.local.SeemensResponse
import uz.fizmasoft.seemens.ui.adapter.CarouselIndicatorAdapter
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var mainViewModel: MainActivityViewModel

    @Inject
    lateinit var providerFactory: ViewModelProvider.Factory



    lateinit var indicatorAdapter: CarouselIndicatorAdapter


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
                is MainActivityViewModel.LoadingResponseState.OnLoading -> onLoading()
                is MainActivityViewModel.LoadingResponseState.OnSuccess -> onDataLoadedSuccess(it.response)
                is MainActivityViewModel.LoadingResponseState.OnError -> onDataLoadingError(it.errorMessage)
            }
        })

    private fun onLoading() {
        println("observeData Loading")
    }

    private fun onDataLoadedSuccess(response : SeemensResponse) {
        println("observeData Success")
        setupImageCarousel(response.details.images)

    }

    private fun onDataLoadingError(errorMessage: String) {
        println("observeData Error $errorMessage")
    }


    private fun setupImageCarousel(imageUrls: List<String>) {
        indicatorAdapter = CarouselIndicatorAdapter(imageUrls)
        mainImageCarousel.adapter = indicatorAdapter
        carouselImageIndicator.setupWithViewPager(mainImageCarousel)
    }
}