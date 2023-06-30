package com.example.shoestap.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoestap.R
import com.example.shoestap.databinding.ItemCartBinding
import com.example.shoestap.databinding.ItemListBinding
import com.example.shoestap.model.Product

class CartViewHolder (view : View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemCartBinding.bind(view)

    fun bind (product : Product, onItemDeleted : (Product, CartFragment.ITEM_EVENT ) -> Unit) {
        binding.tvName.text = product.name
        binding.tvPrice.text = "$" + product.price.toString()
        binding.tvCuantity.text = product.cuantity.toString()

        Glide.with(binding.root)
            .load(product.image)
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.ivProductImage)

        binding.btDeleteItem.setOnClickListener {
            onItemDeleted(product, CartFragment.ITEM_EVENT.DELETE)
        }

        binding.btAdd.setOnClickListener {
            onItemDeleted(product, CartFragment.ITEM_EVENT.ADD)
        }

        binding.btDecrease.setOnClickListener {
            onItemDeleted(product, CartFragment.ITEM_EVENT.DECREASE)
        }
    }
}