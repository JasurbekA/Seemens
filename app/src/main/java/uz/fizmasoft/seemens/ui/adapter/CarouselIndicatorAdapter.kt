package uz.fizmasoft.seemens.ui.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.viewpager.widget.PagerAdapter
import coil.api.load
import uz.fizmasoft.seemens.R


class CarouselIndicatorAdapter (
    private val productImagesURL: List<String>
) : PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutScreen: View =
            LayoutInflater.from(container.context).inflate(R.layout.main_image_carousel, container, false)
        val productImageContainer = layoutScreen.findViewById<AppCompatImageView>(R.id.productImage)
        productImageContainer.load(productImagesURL[position])
        container.addView(layoutScreen)
        return layoutScreen
    }

    override fun getCount() = productImagesURL.size

    override fun isViewFromObject(view: View, o: Any) = view === o

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        item: Any
    ) = container.removeView(item as View)

}