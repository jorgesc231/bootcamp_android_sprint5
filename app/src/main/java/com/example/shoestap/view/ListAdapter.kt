package com.example.shoestap.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoestap.R
import com.example.shoestap.model.Product

class ListAdapter (var productList : List<Product> = emptyList(), private val onItemSelected : (product : Product) -> Unit) : RecyclerView.Adapter<ListViewHolder>() {

    fun updateList(productList : List<Product>) {
        this.productList = productList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        )
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(viewHolder: ListViewHolder, position: Int) {
        viewHolder.bind(productList[position], onItemSelected)
    }

}