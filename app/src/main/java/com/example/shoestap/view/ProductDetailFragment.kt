package com.example.shoestap.view

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.shoestap.R
import com.example.shoestap.ShoesTapApplication
import com.example.shoestap.databinding.FragmentProductDetailBinding
import com.example.shoestap.model.Product
import com.example.shoestap.presenter.ProductDetailsPresenter
import com.example.shoestap.presenter.ProductDetailsPresenterImpl


class ProductDetailFragment : Fragment(), ProductDetailsView {

    private lateinit var binding : FragmentProductDetailBinding

    private val presenter: ProductDetailsPresenter<ProductDetailsView> by lazy {
        ProductDetailsPresenterImpl(this)
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
        // Inflate the layout for this fragment

        binding = FragmentProductDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {

            val navController = view.findNavController()
            binding.Toolbar.setupWithNavController(navController)

            val args = ProductDetailFragmentArgs.fromBundle(requireArguments())

            val product = Product(args.productInfo.name, args.productInfo.image, args.productInfo.price)

            presenter.product = product

            binding.btAddtoCart.setOnClickListener {
                presenter.addToCart(product)
                //Log.i("prueba", product.toString())
                showDialog()
            }
        }
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            ProductDetailFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun showProductDetails(product: Product) {

        Glide.with(binding.root)
            .load(product.image)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.ivImage)
        binding.tvName.text = product.name
        binding.tvPrice.text = "$" + product.price.toString()

    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    private fun showDialog() {
        val dialog = Dialog(this.requireContext())
        dialog.setContentView(R.layout.dialog_added)

        val btnContinuar : Button = dialog.findViewById(R.id.btnContinuar)
        val btnCarrito : Button = dialog.findViewById(R.id.btnCarrito)

        btnContinuar.setOnClickListener {
            dialog.hide()
            binding.root.findNavController().navigate(ProductDetailFragmentDirections.actionProductDetailFragmentToProductListFragment())
        }

        btnCarrito.setOnClickListener {
            dialog.hide()
            binding.root.findNavController().navigate(ProductDetailFragmentDirections.actionProductDetailFragmentToCartFragment())
        }

        dialog.show()
    }
}