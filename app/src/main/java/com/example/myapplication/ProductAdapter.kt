package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class ProductAdapter(
    private val productList: MutableList<ProductItem>,
    private val onExchangeClickListener: (ProductItem) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productNameTextView: TextView = itemView.findViewById(R.id.productNameTextView)
        val productDescriptionTextView: TextView = itemView.findViewById(R.id.productDescriptionTextView)
        val pointsTextView: TextView = itemView.findViewById(R.id.pointsTextView)
        val productImageView: ImageView = itemView.findViewById(R.id.productImageView)
        val exchangeButton: Button = itemView.findViewById(R.id.exchangeButton)

        private val requestOptions = RequestOptions()
            .transform(CenterCrop(), RoundedCorners(8))
            .placeholder(R.drawable.placeholder_news)
            .error(R.drawable.placeholder_news)
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        fun bind(productItem: ProductItem) {
            productNameTextView.text = productItem.name
            productDescriptionTextView.text = productItem.description
            pointsTextView.text = productItem.points.toString()

            if (productItem.pic.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(productItem.pic)
                    .apply(requestOptions)
                    .into(productImageView)
            } else {
                Glide.with(itemView.context)
                    .load(R.drawable.placeholder_news)
                    .apply(requestOptions)
                    .into(productImageView)
            }

            exchangeButton.setOnClickListener {
                onExchangeClickListener(productItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size

    fun updateData(newList: List<ProductItem>) {
        productList.clear()
        productList.addAll(newList)
        notifyDataSetChanged()
    }
}
