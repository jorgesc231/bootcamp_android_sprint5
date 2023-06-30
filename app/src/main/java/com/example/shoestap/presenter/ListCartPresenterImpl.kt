package com.example.shoestap.presenter

import com.example.shoestap.ShoesTapApplication.Companion.prefs
import com.example.shoestap.model.Product
import com.example.shoestap.model.ProductStore
import com.example.shoestap.view.ListCartView

class ListCartPresenterImpl(override var view: ListCartView?) : ListCartPresenter<ListCartView> {
    override var products: List<Product>?
        get() = getProductsFromStorage()
        set(value) {}

    override fun deleteCart() {
        prefs.deleteAllProducts()
        view?.showEmptyCartError()
    }

    override fun deleteItem(product: Product) {
        prefs.deleteProduct(product.name)
        view?.showCartProducts(getProductsFromStorage())
    }

    override fun addItem(product: Product) {
        prefs.addProduct(product)
        view?.showCartProducts(getProductsFromStorage())
    }

    override fun decreaseItem(product: Product) {
        prefs.decreaseProduct(product)
        view?.showCartProducts(getProductsFromStorage())
    }

    private fun getProductsFromStorage(): List<Product> {

        var addedProducts : MutableList<Product> = mutableListOf()
        val allProducts: List<Product> = ProductStore.getProducts()
        val storedProducts: List<Pair<String, Any?>> = prefs.getProducts()

        for (product in allProducts) {
            for (item in storedProducts) {
                if (product.name == item.first) {
                    addedProducts.add(Product(product.name, product.image, product.price, item.second as Int))
                }
            }
        }

         if (addedProducts.isEmpty()) {
            view?.showEmptyCartError()

            return addedProducts
        }

        view?.showCartProducts(addedProducts)

        return addedProducts
    }
}