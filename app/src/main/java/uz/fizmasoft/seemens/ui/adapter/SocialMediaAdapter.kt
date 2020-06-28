package uz.fizmasoft.seemens.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import coil.api.load
import kotlinx.android.synthetic.main.item_social_media.view.*
import uz.fizmasoft.seemens.R
import uz.fizmasoft.seemens.data.local.fake.SocialMediaData

class SocialMediaAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<SocialMediaAdapter.SocialMediaItem>() {

    private val diffCallback = object : DiffUtil.ItemCallback<SocialMediaData>() {
        override fun areItemsTheSame(oldItem: SocialMediaData, newItem: SocialMediaData) = false
        override fun areContentsTheSame(
            oldItem: SocialMediaData,
            newItem: SocialMediaData
        ): Boolean = true
    }
    private val differ = AsyncListDiffer(this, diffCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SocialMediaItem(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_social_media,
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: SocialMediaItem, position: Int) =
        holder.bind(differ.currentList[position])

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<SocialMediaData>) {
        differ.submitList(list)
    }

    inner class SocialMediaItem
    constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: SocialMediaData): Unit = with(itemView) {
            setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            socialMediaImage.load(item.resID)
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: SocialMediaData)
    }
}
