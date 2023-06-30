package com.example.shoestap.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoestap.R
import com.example.shoestap.model.Product

class CartAdapter(var productList: List<Product> = emptyList(), private val onItemChanged : (product : Product, event : CartFragment.ITEM_EVENT) -> Unit) : RecyclerView.Adapter<CartViewHolder>() {

    fun updateList(productList : List<Product>) {
        this.productList = productList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        )
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(productList[position], onItemChanged)
    }

}