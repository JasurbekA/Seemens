package uz.fizmasoft.seemens.ui

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import uz.fizmasoft.seemens.R
import uz.fizmasoft.seemens.data.local.fake.SocialMediaData
import uz.fizmasoft.seemens.data.local.fake.getProdAddViewData
import uz.fizmasoft.seemens.data.local.fake.socialMediaData
import uz.fizmasoft.seemens.data.local.model.Delivery
import uz.fizmasoft.seemens.data.local.model.Details
import uz.fizmasoft.seemens.data.local.model.SeemensResponse
import uz.fizmasoft.seemens.data.local.model.Size
import uz.fizmasoft.seemens.ui.adapter.*
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
                is MainActivityViewModel.LoadingResponseState.OnLoading -> onLoading()
                is MainActivityViewModel.LoadingResponseState.OnSuccess -> onDataLoadedSuccess(it.response)
                is MainActivityViewModel.LoadingResponseState.OnError -> onDataLoadingError(it.errorMessage)
            }
        })

    private fun onLoading() {
        println("observeData Loading")
    }

    private fun onDataLoadedSuccess(response: SeemensResponse) {
        println("observeData Success")
        setClickListeners()
        setupImageCarousel(response.details.images)
        setupProductDetails(response.details)
        setupDeliveryData(response.delivery)
        setupSocialMedia()
        setupSimilarProducts(response.details.images)
        setupSimilarRecentlySeen(response.details.images)
    }

    private var isInfoExpanded = false
    private val infoClicked = View.OnClickListener {
        println("click event")
        isInfoExpanded = !isInfoExpanded
        val icon = ContextCompat.getDrawable(
            this,
            if (isInfoExpanded) {
                infoMainContent.visibility = View.VISIBLE
                R.drawable.ic_minus
            } else {
                infoMainContent.visibility = View.GONE
                R.drawable.ic_add
            }
        )
        infoAboutProduct.icon = icon
    }

    private fun setClickListeners() {
        infoAboutProductContainer.setOnClickListener{
            println("click event")
            isInfoExpanded = !isInfoExpanded
            val icon = ContextCompat.getDrawable(
                this,
                if (isInfoExpanded) {
                    infoMainContent.visibility = View.VISIBLE
                    R.drawable.ic_minus
                } else {
                    infoMainContent.visibility = View.GONE
                    R.drawable.ic_add
                }
            )
            infoAboutProduct.icon = icon
        }
    }

    private fun setupSimilarRecentlySeen(images: List<String>) {
        val adViewAdapter = ProductAdViewAdapter()
        adViewAdapter.submitList(getProdAddViewData(images))
        recentlySeenProductsCarousel.adapter = adViewAdapter
    }

    private fun setupSimilarProducts(images: List<String>) {
        val adViewAdapter = ProductAdViewAdapter()
        adViewAdapter.submitList(getProdAddViewData(images))
        relatedProductsCarousel.adapter = adViewAdapter
    }

    private fun onDataLoadingError(errorMessage: String) {
        println("observeData Error $errorMessage")
    }


    private fun setupImageCarousel(imageUrls: List<String>) {
        val indicatorAdapter = CarouselIndicatorAdapter(imageUrls)
        mainImageCarousel.adapter = indicatorAdapter
        carouselImageIndicator.setupWithViewPager(mainImageCarousel)
    }

    private fun setupProductDetails(details: Details) {
        productName.text = details.name
        infoMainContent.text = details.description
        setupSubCarousel(details.images)
        brandName.text = details.brand
        brandType.text = details.category
        "${details.currency[0].price} ${details.currency[0].currency}".apply {
            productCost.text = this
        }
        setupSizeCarousel(details.attribute_combination.size)
    }

    private fun setupSizeCarousel(size: Size) {
        val sizeAdapter = ProductSizesAdapter()
        sizeAdapter.submitList(size.values)
        productSizesRV.adapter = sizeAdapter
    }


    private fun setupSocialMedia() {
        val socialMediaAdapter = SocialMediaAdapter(socialMediaItemClick)
        socialMediaAdapter.submitList(socialMediaData)
        socialMediaRV.adapter = socialMediaAdapter
    }

    private fun setupSubCarousel(imageUrls: List<String>) {
        val subcategoryAdapter = ProductSubcategoryImagesAdapter()
        subcategoryAdapter.submitList(imageUrls)
        subImageCarousel.adapter = subcategoryAdapter
    }

    private fun setupDeliveryData(delivery: Delivery) {
        closestDelivery.text = delivery.date
        returnDate.text = delivery.returnType
    }

    private val socialMediaItemClick = object : SocialMediaAdapter.Interaction {
        override fun onItemSelected(position: Int, item: SocialMediaData) {
            Toast.makeText(this@MainActivity, item.link, Toast.LENGTH_SHORT).show()
        }
    }
}