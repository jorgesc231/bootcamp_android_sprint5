package com.example.shoestap.view

import android.opengl.Visibility
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.shoestap.R
import com.example.shoestap.databinding.FragmentProductListBinding
import com.example.shoestap.model.Product
import com.example.shoestap.model.ProductStore
import com.example.shoestap.presenter.ListProductPresenter
import com.example.shoestap.presenter.ListProductPresenterImpl

/**
 * A simple [Fragment] subclass.
 * Use the [ProductListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductListFragment : Fragment(), ListProductsView {

    private lateinit var binding : FragmentProductListBinding
    private lateinit var listAdapter : ListAdapter

    private val presenter: ListProductPresenter<ListProductsView> by lazy {
        ListProductPresenterImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentProductListBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            // Cuando se presiona buscar
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (!query.isNullOrEmpty()) {
                    val filteredList = ProductStore.getProducts().filter { it.name.contains(query, true) }

                    if(filteredList.isNotEmpty()) {
                        binding.rvProducts.visibility = View.VISIBLE
                        listAdapter.productList = filteredList
                        listAdapter.notifyDataSetChanged()
                    } else {
                        binding.rvProducts.visibility = View.GONE
                        binding.tvSearchError.visibility = View.VISIBLE
                        binding.tvSearchError.text = String.format("No se encontró nada para el termino: \"%s\"", query)
                    }

                }

                return false
            }

            // Cuando el texto de busqueda cambia
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    if (newText.isEmpty()) {
                        // Search
                        binding.rvProducts.visibility = View.VISIBLE
                        listAdapter.productList = ProductStore.getProducts()
                        listAdapter.notifyDataSetChanged()
                    }
                }

                return false
            }
        })

        val navController = view.findNavController()
        binding.Toolbar.setupWithNavController(navController)

        listAdapter = ListAdapter { navigateToDetail(it) }
        binding.rvProducts.setHasFixedSize(true)
        // NOTE: Seteado en el xml
        //binding.rvProducts.layoutManager = GridLayoutManager(this.context, 2)
        binding.rvProducts.adapter = listAdapter

        binding.btCart.setOnClickListener { binding.root.findNavController().navigate(ProductListFragmentDirections.actionProductListFragmentToCartFragment()) }

        // TODO: Actualiza los productos, no creo que este bien...
        presenter.products
    }

    private fun navigateToDetail(product: Product) {
        //binding.root.findNavController().navigate(R.id.action_productListFragment_to_productDetailFragment)
        val directions =
            ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(product)
        binding.root.findNavController().navigate(directions)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProductListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun showEmptyListError() {
        binding.rvProducts.visibility = View.GONE
        binding.tvEmptyList.visibility = View.VISIBLE
        Toast.makeText(this.context, "No hay productos", Toast.LENGTH_LONG).show()
    }

    override fun showProducts(products: List<Product>) {
        listAdapter.updateList(products)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}