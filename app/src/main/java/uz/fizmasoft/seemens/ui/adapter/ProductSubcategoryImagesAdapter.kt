package uz.fizmasoft.seemens.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import coil.api.load
import kotlinx.android.synthetic.main.item_product_sub_carousel.view.*
import uz.fizmasoft.seemens.R

class ProductSubcategoryImagesAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<ProductSubcategoryImagesAdapter.Subcategory>() {

    private val diffCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    }
    private val differ = AsyncListDiffer(this, diffCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Subcategory(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_product_sub_carousel,
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: Subcategory, position: Int) =
        holder.bind(differ.currentList[position])

    override fun getItemCount() = differ.currentList.size

    fun submitList(list: List<String>) = differ.submitList(list)

    inner class Subcategory constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: String) : Unit = with(itemView) {
            setOnClickListener {
                interaction?.onItemSelected(adapterPosition) }
            productSubcategoryImage.load(item)
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int)
    }
}
