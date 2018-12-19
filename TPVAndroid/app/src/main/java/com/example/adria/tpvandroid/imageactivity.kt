package com.example.adria.tpvandroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView

class imageactivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listview)

        var listView = findViewById<ListView>(R.id.listView)
        var arrProductos: ArrayList<ProductosV> = ArrayList()
        arrProductos.add(ProductosV("Longaniza", R.drawable.longaniza))
        arrProductos.add(ProductosV("Bocadillo", R.drawable.bocadillo))
        arrProductos.add(ProductosV("Hamburguesa", R.drawable.hamburguesa))
        arrProductos.add(ProductosV("Pizza", R.drawable.pizza))
        arrProductos.add(ProductosV("Perrito", R.drawable.perrito))
        arrProductos.add(ProductosV("Tortilla", R.drawable.tortilla))
        arrProductos.add(ProductosV("Cerveza", R.drawable.cerveza))
        arrProductos.add(ProductosV("Coca-Cola", R.drawable.cocacola))

        listView.adapter = CustomAdapter(applicationContext, arrProductos)
    }
}