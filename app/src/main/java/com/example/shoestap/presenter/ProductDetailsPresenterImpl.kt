package com.example.shoestap.presenter

import android.widget.Toast
import com.example.shoestap.ShoesTapApplication.Companion.prefs
import com.example.shoestap.model.Product
import com.example.shoestap.view.ProductDetailsView

class ProductDetailsPresenterImpl(override var view: ProductDetailsView?
) : ProductDetailsPresenter<ProductDetailsView> {
    override var product: Product
        get() = TODO("Not yet implemented")
        set(value) {
             view?.showProductDetails(value)
        }


    override fun addToCart(product: Product) {
        prefs.addProduct(product)
    }
}