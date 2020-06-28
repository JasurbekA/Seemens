package uz.fizmasoft.seemens.ui.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import kotlinx.android.synthetic.main.item_product_ad_view.view.*
import uz.fizmasoft.seemens.R
import uz.fizmasoft.seemens.data.local.fake.ProductAdViewData

class ProductAdViewAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<ProductAdViewAdapter.ProductAdView>() {

    private val diffCallback = object : DiffUtil.ItemCallback<ProductAdViewData>() {
        override fun areItemsTheSame(oldItem: ProductAdViewData, newItem: ProductAdViewData) =
            oldItem.imageUrl == newItem.imageUrl

        override fun areContentsTheSame(
            oldItem: ProductAdViewData,
            newItem: ProductAdViewData
        ): Boolean = oldItem.isFavorite == newItem.isFavorite
    }
    private val differ = AsyncListDiffer(this, diffCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProductAdView(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_product_ad_view,
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: ProductAdView, position: Int) =
        holder.bind(differ.currentList[position])

    override fun getItemCount() = differ.currentList.size


    fun submitList(list: List<ProductAdViewData>) {
        differ.submitList(list)
    }

    inner class ProductAdView
    constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ProductAdViewData): Unit = with(itemView) {
            setOnClickListener {
                item.isFavorite = !item.isFavorite
                interaction?.onItemSelected(adapterPosition, item)
                notifyItemChanged(adapterPosition)
            }

            val icon = if (item.isFavorite)
                R.drawable.ic_favorite_filled
            else
                R.drawable.ic_favorite_outline

            productFavoriteToggle.setImageDrawable(
                ContextCompat.getDrawable(productFavoriteToggle.context, icon)
            )

            productImage.load(item.imageUrl)
            productDesc.text = item.prodDesc
            productName.text = item.prodName
            productPrice.text = item.prodPrice
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: ProductAdViewData)
    }
}
