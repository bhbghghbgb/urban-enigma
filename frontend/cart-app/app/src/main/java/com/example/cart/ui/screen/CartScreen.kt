package com.example.cart.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cart.Models.Cart
import com.example.cart.Models.DetailOfCart
import com.example.cart.Utilities.Zalopay.Api.CreateOrder
import com.example.cart.ViewModels.CartViewModel

@Composable
fun CartScreen(viewModel: CartViewModel = CartViewModel()) {
    val cart by viewModel.cart.collectAsState()

    CartView(cart = cart, viewModel)
}

@Composable
fun CartView(cart: Cart?, viewModel: CartViewModel) = if (cart == null) {
    Text("Loading...")
} else {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Cart ID: ${cart._id}",
            style = TextStyle(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("Customer ID: ${cart.customer}")
        Spacer(modifier = Modifier.height(8.dp))
        val total by viewModel.total.collectAsState()
        Text("Total: $${String.format("%.0f", total)}")
        Spacer(modifier = Modifier.height(16.dp))
        if (cart.products.isNotEmpty()) {
            Text(
                text = "Products:",
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            cart.products.forEach { product -> // truyền vào product
                ProductItem(detailOfCart = product, viewModel)
            }
        } else {
            Text("No products in the cart")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.updateCart()
            val orderAPI: CreateOrder = CreateOrder()
//            val token: String = txtToken.getText().toString()

        }) {
            Text(text = "Thanh Toán")
        }
    }
}

@Composable
fun ProductItem(detailOfCart: DetailOfCart, viewModel: CartViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var amount by remember {
            mutableIntStateOf(detailOfCart.amount)
        }
        val price = detailOfCart.price
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "Price: $${price}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        IconButton(onClick = {
            amount++
            viewModel.increaseTotalOfCart(price)
            viewModel.updateAmountOfProduct(detailOfCart, amount)
        }) {
            Icon(Icons.Filled.Add, contentDescription = "Add")
        }
        Text(text = "${amount}")
        IconButton(onClick = {
            amount--
            if (amount > 0) {
                viewModel.decreaseTotalOfCart(price)
                viewModel.updateAmountOfProduct(detailOfCart, amount)
            } else amount = 0
        }) {
            Icon(Icons.Filled.ArrowDropDown, contentDescription = "Drop")
        }
    }
}