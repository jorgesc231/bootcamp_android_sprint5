package com.example.shoestap.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoestap.R
import com.example.shoestap.databinding.ItemListBinding
import com.example.shoestap.model.Product

class ListViewHolder (view : View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemListBinding.bind(view)

    fun bind(product : Product, onItemSelected : (Product) -> Unit) {
        binding.tvName.text = product.name
        binding.tvPrice.text = "$" + product.price.toString()

        Glide.with(binding.root)
            .load(product.image)
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.ivImage)


        binding.root.setOnClickListener {
            onItemSelected(product)
        }
    }
}