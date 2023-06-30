package com.example.shoestap.model

import android.content.Context
import android.content.SharedPreferences

class SPrefsHelper (val context: Context) {

    private val PREFS_NAME: String = "com.shoestap.cart"
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)


    fun addProduct(product: Product) {

        var count : Int = prefs.getInt(product.name, 0)

        if (count != 0) {
            prefs.edit().putInt(product.name, ++count).apply()
        } else {
            prefs.edit().putInt(product.name, 1).apply()
        }
    }

    fun getProducts() : List<Pair<String, Any?>> {
        return prefs.all.toList()
    }

    fun deleteProduct(name : String) {
        if (prefs.contains(name)) prefs.edit().remove(name).apply()
    }

    fun decreaseProduct(product: Product) {
        var count : Int = prefs.getInt(product.name, 0)

        if (count > 1) {
            prefs.edit().putInt(product.name, --count).apply()
        } else {
            prefs.edit().remove(product.name).apply()
        }
    }

    fun deleteAllProducts() {
        prefs.edit().clear().apply()
    }
}