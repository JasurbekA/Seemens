package uz.fizmasoft.seemens.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_content.*
import kotlinx.android.synthetic.main.activity_main_loading_error.*
import uz.fizmasoft.seemens.R
import uz.fizmasoft.seemens.data.local.fake.SocialMediaData
import uz.fizmasoft.seemens.data.local.fake.getProdAddViewData
import uz.fizmasoft.seemens.data.local.fake.socialMediaData
import uz.fizmasoft.seemens.data.local.model.Delivery
import uz.fizmasoft.seemens.data.local.model.Details
import uz.fizmasoft.seemens.data.local.model.SeemensResponse
import uz.fizmasoft.seemens.data.local.model.Size
import uz.fizmasoft.seemens.extention.toast
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
        loadData()
        setClickListeners()
    }

    private fun loadData() {
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
        progressBar.visibility = View.VISIBLE
        retryContainer.visibility = View.GONE
        mainContent.visibility = View.GONE
    }

    private fun onDataLoadedSuccess(response: SeemensResponse) {
        setupViewsVisibilityForSuccess()
        setupImageCarousel(response.details.images)
        setupProductDetails(response.details)
        setupDeliveryData(response.delivery)
        setupSocialMedia()
        setupSimilarProducts(response.details.images)
        setupSimilarRecentlySeen(response.details.images)
    }

    private fun setupViewsVisibilityForSuccess() {
        progressBar.visibility = View.GONE
        retryContainer.visibility = View.GONE
        mainContent.visibility = View.VISIBLE
    }

    private var isInfoExpanded = false
    private var isFavorite = false
    private val infoClicked = View.OnClickListener {

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


    private val favoriteImageClicked = View.OnClickListener {
        isFavorite = !isFavorite
        val icon = ContextCompat.getDrawable(
            this,
            if (isFavorite)
                R.drawable.ic_favorite_filled
            else
                R.drawable.ic_favorite_outline
        )
        favoriteImage.icon = icon
    }


    private val templateClick = View.OnClickListener { toast("Clicked") }

    private fun setClickListeners() {
        infoAboutProduct.setOnClickListener(infoClicked)
        favoriteImage.setOnClickListener(favoriteImageClicked)
        btnRetry.setOnClickListener { loadData() }

        backArrow.setOnClickListener { toast("Back button clicked") }
        shareProduct.setOnClickListener { toast("Product share is clicked") }
        btnAddToCart.setOnClickListener { toast("Product add to cart is clicked") }

        otherProductInType.setOnClickListener(templateClick)
        allProductInBrand.setOnClickListener(templateClick)
        allProductInType.setOnClickListener(templateClick)
        shareImage.setOnClickListener(templateClick)
        delivery.setOnClickListener(templateClick)
        ingredientProduct.setOnClickListener(templateClick)
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
        progressBar.visibility = View.GONE
        retryContainer.visibility = View.VISIBLE
        mainContent.visibility = View.GONE
        errorMessageHolder.text = errorMessage
    }


    private fun setupImageCarousel(imageUrls: List<String>) {
        val indicatorAdapter = CarouselIndicatorAdapter(imageUrls)
        mainImageCarousel.adapter = indicatorAdapter
        carouselImageIndicator.setupWithViewPager(mainImageCarousel)
    }

    private fun setupProductDetails(details: Details) {
        productName.text = details.attribute_combination.color.values[0].name
        infoMainContent.text = details.description
        setupSubCarousel(details.images)
        brandName.text = details.brand
        brandDesc.text = details.name
        brandType.text = details.category
        "Все ${details.brand}".apply { allProductInBrand.text = this }
        "Все ${details.category} ${details.brand}".apply { allProductInType.text = this }
        "Другие ${details.category}".apply { otherProductInType.text = this }
        "${details.currency[0].price} ${details.attribute_combination.color.values[0].currency[0].currency}"
            .apply { productCost.text = this }
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
        override fun onItemSelected(position: Int, item: SocialMediaData) = toast(item.link)
    }

}