package uz.fizmasoft.seemens.ui.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.item_product_size.view.*
import uz.fizmasoft.seemens.R
import uz.fizmasoft.seemens.data.local.model.Values

class ProductSizesAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<ProductSizesAdapter.ProductSize>() {

    private var activeSizePos = 0

    private val diffCallback = object : DiffUtil.ItemCallback<Values>() {
        override fun areItemsTheSame(oldItem: Values, newItem: Values) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Values, newItem: Values) = oldItem == newItem
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProductSize(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_product_size,
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: ProductSize, position: Int) =
        holder.bind(differ.currentList[position])

    override fun getItemCount() = differ.currentList.size

    fun submitList(list: List<Values>) = differ.submitList(list)


    inner class ProductSize constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Values): Unit = with(itemView) {
            setOnClickListener {
                activeSizePos = adapterPosition
                interaction?.onItemSelected(adapterPosition, item)
                notifyDataSetChanged()
            }
            sizeSelectedStatus(adapterPosition != activeSizePos, productSize)
            productSize.text = item.name
        }
    }

    private fun sizeSelectedStatus(isSelected: Boolean, productSize: MaterialTextView) {
        val color: ColorDrawable
        val textColor: Int

        if (isSelected) {
            color = ColorDrawable(Color.LTGRAY)
            textColor = R.color.sizeSelectedColor
        } else {
            color = ColorDrawable(Color.BLACK)
            textColor = R.color.sizeUnSelectedColor
        }

        productSize.background = color
        productSize.setTextColor(ContextCompat.getColor(productSize.context, textColor))
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Values)
    }
}
