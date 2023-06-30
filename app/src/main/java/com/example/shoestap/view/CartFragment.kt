package com.example.shoestap.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoestap.R
import com.example.shoestap.databinding.FragmentCartBinding
import com.example.shoestap.model.Product
import com.example.shoestap.presenter.ListCartPresenter
import com.example.shoestap.presenter.ListCartPresenterImpl

class CartFragment : Fragment(), ListCartView {
    private lateinit var binding : FragmentCartBinding
    private lateinit var cartAdapter : CartAdapter

    private val presenter : ListCartPresenter<ListCartView> by lazy {
        ListCartPresenterImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCartBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartAdapter = CartAdapter {product, event -> changeItem(product, event)}
        binding.rvCart.setHasFixedSize(true)
        binding.rvCart.layoutManager = LinearLayoutManager(this.context)
        binding.rvCart.adapter = cartAdapter

        binding.btCheckout.setOnClickListener {
            if (!presenter.products.isNullOrEmpty()) {
                presenter.deleteCart()
                showDialog()
            }
        }

        val navController = view.findNavController()
        binding.Toolbar.setupWithNavController(navController)

        binding.Toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                // these ids should match the item ids from my_fragment_menu.xml file
                R.id.clear_cart -> {
                    //Log.i("prueba", "clear cart")
                    presenter.deleteCart()
                    // by returning 'true' we're saying that the event
                    // is handled and it shouldn't be propagated further
                    true
                }
                else -> false
            }
        }

        presenter.products

    }

    private fun changeItem(product: Product, event : ITEM_EVENT) {
        when(event) {
            ITEM_EVENT.DELETE -> {
                presenter.deleteItem(product)
            }
            ITEM_EVENT.ADD -> {
                presenter.addItem(product)
            }
            ITEM_EVENT.DECREASE -> {
                presenter.decreaseItem(product)
            }
        }
    }

    enum class ITEM_EVENT {
        DELETE,
        ADD,
        DECREASE
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CartFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun showEmptyCartError() {
        binding.rvCart.visibility = View.GONE
        binding.tvEmptyCart.visibility = View.VISIBLE
    }

    override fun showCartProducts(products: List<Product>) {
        cartAdapter.updateList(products)

        var total = 0.0f
        for (product in products) {
            total += (product.price * product.cuantity)
        }

        binding.tvPrice.text = String.format("$%.2f", total)
    }

    private fun showDialog() {
        val dialog = Dialog(this.requireContext())
        dialog.setContentView(R.layout.dialog_thanks)

        val btnOk : Button = dialog.findViewById(R.id.btnOk)

        btnOk.setOnClickListener {
            binding.root.findNavController().navigate(CartFragmentDirections.actionCartFragmentToProductListFragment())
            dialog.hide()
        }

        dialog.show()
    }
}