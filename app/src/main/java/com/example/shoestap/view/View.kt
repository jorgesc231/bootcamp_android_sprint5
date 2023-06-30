package com.example.shoestap.view

import com.example.shoestap.model.Product

interface View

interface ListProductsView: View {
    fun showEmptyListError() /* show error when list is empty */
    fun showProducts(products: List<Product>) /* show product details */
}

interface ProductDetailsView: View {
    fun showProductDetails(product: Product)
}


interface ListCartView: View {
    fun showEmptyCartError() /* show error when list is empty */
    fun showCartProducts(products: List<Product>) /* show product details */
}