package com.example.adria.tpvandroid

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.text.ClipboardManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_productos.*
import kotlinx.android.synthetic.main.activity_productos.*
import kotlinx.android.synthetic.main.productosrow.view.*

class ProductosActivity : AppCompatActivity() {

    private var listProductos = ArrayList<Productos>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)

        //Load from DB
        LoadQuery("%")
    }

    override fun onResume() {
        super.onResume()
        LoadQuery("%")
    }

    private fun LoadQuery(producto:String) {
        var dbManager = DbManager(this)
        val projections = arrayOf("IDproducto", "Categoria", "Nombre", "Precio")
        val selectionArgs = arrayOf(producto)
        val cursor = dbManager.Queryp(projections, "Categoria like ?", selectionArgs, "Categoria")
        listProductos.clear()
        if (cursor.moveToFirst()){

            do {
                val ID = cursor.getInt(cursor.getColumnIndex("IDproducto"))
                val Categoria= cursor.getString(cursor.getColumnIndex("Categoria"))
                val Nombre = cursor.getString(cursor.getColumnIndex("Nombre"))
                val Precio = cursor.getDouble(cursor.getColumnIndex("Precio"))

                listProductos.add(Productos(ID, Categoria, Nombre, Precio))

            }while (cursor.moveToNext())
        }

        //Adapter
        var myProductosAdapter = MyProductosAdapter(this, listProductos)
        //set adapter
        productosLv.adapter = myProductosAdapter

        //get total number of tasks from ListView
        val total = productosLv.count
        //actionbar
        val mActionBar = supportActionBar
        //set to actionbar as subtitle of actionbar
        if (mActionBar != null) {
            mActionBar.subtitle = "Tienes $total producto(s) en lista"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean{
        menuInflater.inflate(R.menu.main_menu, menu)
        //searchView
        val sv: SearchView = menu!!.findItem(R.id.app_bar_search).actionView as SearchView

        val sm = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        sv.setSearchableInfo(sm.getSearchableInfo(componentName))
        sv.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                LoadQuery("%$query%")
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                LoadQuery("%$p0%")
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null){
            when(item.itemId) {
                R.id.add->{
                    startActivity(Intent(this, AddProductosActivity::class.java))
                }
                R.id.action_settings->{
                    Toast.makeText(this, "Proximamente!!!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class MyProductosAdapter : BaseAdapter {
        public var listProductosAdapter = ArrayList<Productos>()
        var context: Context?=null

        constructor(context: Context, listProductosAdapter: ArrayList<Productos>) : super() {
            this.listProductosAdapter  = listProductosAdapter
            this.context = context
        }


        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            //inflate layout comandasrow.xmlxml
            var myView = layoutInflater.inflate(R.layout.productosrow, null)
            val myProducto = listProductosAdapter[position]
            myView.pcTv.text = myProducto.productoCategoria!!
            myView.pnTv.text = myProducto.productoNombre

            //delete button click
            myView.pdeleteBtn.setOnClickListener{
                var dbManager = DbManager(this.context!!)
                val selectionArgs = arrayOf(myProducto.productoID.toString())
                dbManager.deletep("IDproducto=?", selectionArgs)
                LoadQuery("%")
            }

            //Edit/Update button click
            myView.peditBtn.setOnClickListener{
                GoToUpdateFun(myProducto)
            }

            // Copy button click
            myView.pcopyBtn.setOnClickListener{
                //get Title
                val title = myView.pnTv.text.toString()
                //concatinate
                val s = title
                val cb = getSystemService(Context.CLIPBOARD_SERVICE)as ClipboardManager
                cb.text = s //add to clipboard
                Toast.makeText(this@ProductosActivity, "Copied...", Toast.LENGTH_SHORT).show()
            }
            return myView
        }

        override fun getItem(position: Int): Any {
            return listProductosAdapter[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listProductosAdapter.size
        }

    }

    private fun GoToUpdateFun(myProductos: Productos) {
        var intent = Intent(this, AddProductosActivity::class.java)
        intent.putExtra("IDproducto", myProductos.productoID)//put ID
        intent.putExtra("Categoria", myProductos.productoCategoria)//put categoria
        intent.putExtra("Nombre", myProductos.productoNombre) // put nombre
        intent.putExtra("Precio", myProductos.productoPrecio) // put precio
        startActivity(intent) // start activity
    }


}


