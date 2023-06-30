package com.example.shoestap.presenter

import com.example.shoestap.model.Product
import com.example.shoestap.view.View

interface Presenter<T : View> {
    var view: T?

    fun onDestroy(){
        view = null
    }
}

interface ListProductPresenter<T : View>: Presenter<T> {
    var products : List<Product>?
    fun getProductsFromStorage() : List<Product>
}

interface ProductDetailsPresenter<T : View>: Presenter<T> {
    var product: Product
    fun addToCart(product : Product)
}

interface ListCartPresenter<T : View>: Presenter<T> {
    var products : List<Product>?
    fun deleteCart()
    fun deleteItem(product: Product)
    fun addItem(product: Product)
    fun decreaseItem(product: Product)
}