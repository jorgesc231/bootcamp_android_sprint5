package com.example.shoestap.presenter

import com.example.shoestap.model.Product
import com.example.shoestap.model.ProductStore
import com.example.shoestap.view.ListProductsView

class ListProductPresenterImpl(override var view: ListProductsView?) : ListProductPresenter<ListProductsView> {

    override var products: List<Product>?
        get() = getProductsFromStorage()
        set(value) {}

    override fun getProductsFromStorage(): List<Product> {

        val products = ProductStore.getProducts()

        if (products.isEmpty()) {
            view?.showEmptyListError()

            return products
        }

        view?.showProducts(products)

        return products
    }
}