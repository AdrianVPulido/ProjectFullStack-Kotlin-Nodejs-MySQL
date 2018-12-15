package com.example.adria.tpvandroid

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.widget.SearchView
import android.text.ClipboardManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import com.example.adria.tpvandroid.R.id.input_product
import kotlinx.android.synthetic.main.activity_comandas.*
import kotlinx.android.synthetic.main.comandasrow.view.*


class ComandasActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                var intent2 = Intent(this, ComandasActivity::class.java)
                startActivity(intent2)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                var intent3 = Intent(this, ProductosActivity::class.java)
                startActivity(intent3)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private var listComandas = ArrayList<Comandas>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comandas)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


        //Load from DB
        LoadQuery("%")
    }

    override fun onResume() {
        super.onResume()
        LoadQuery("%")
    }

     fun LoadQuery(nombre:String) {
        var dbManager = DbManager(this)
        val projections = arrayOf("IDcomanda", "mesaID", "Estado, Nombre")
        val selectionArgs = arrayOf(nombre)
        val cursor = dbManager.Query(projections, "mesaID like ?", selectionArgs, "mesaID")
        listComandas.clear()
        if (cursor.moveToFirst()){

            do {
                val ID = cursor.getInt(cursor.getColumnIndex("IDcomanda"))
                val IDmesa = cursor.getString(cursor.getColumnIndex("mesaID"))
                val Estado = cursor.getString(cursor.getColumnIndex("Estado"))
                val Nombre = cursor.getString(cursor.getColumnIndex("Nombre"))

                listComandas.add(Comandas(ID, IDmesa, Estado, Nombre))

            }while (cursor.moveToNext())
        }

        //Adapter
        var myMesasAdapter = MyMesasAdapter(this, listComandas)
        //set adapter
        mesasLv.adapter = myMesasAdapter

        //get total number of tasks from ListView
        val total = mesasLv.count
        //actionbar
        val mActionBar = supportActionBar
        //set to actionbar as subtitle of actionbar
        if (mActionBar != null) {
            mActionBar.subtitle = "Tienes $total comanda(s) en lista"
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
                    startActivity(Intent(this, AddComandasActivity::class.java))
                }
                R.id.action_settings->{
                    Toast.makeText(this, "Proximamente!!!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class MyMesasAdapter : BaseAdapter {
        var listComandasAdapter = ArrayList<Comandas>()
        var context: Context?=null

        constructor(context: Context, listComandasAdapter: ArrayList<Comandas>) : super() {
            this.listComandasAdapter  = listComandasAdapter
            this.context = context
        }


        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
           //inflate layout comandasrow.xmlxml
            var myView = layoutInflater.inflate(R.layout.comandasrow, null)
            val myComanda = listComandasAdapter[position]
            myView.idTv.text = myComanda.comandasMID!!
            myView.nomTv.text = myComanda.comandasEstado

            //delete button click
            myView.deleteBtn.setOnClickListener{
                var dbManager = DbManager(this.context!!)
                val selectionArgs = arrayOf(myComanda.comandasID.toString())
                dbManager.delete("IDcomanda=?", selectionArgs)
                LoadQuery("%")
            }

            //Edit/Update button click
            myView.editBtn.setOnClickListener{
                    GoToUpdateFun(myComanda)
            }

            // Copy button click
            myView.copyBtn.setOnClickListener{
                //get Title
                val title = myView.nomTv.text.toString()
                //concatinate
                val s = title
                val cb = getSystemService(Context.CLIPBOARD_SERVICE)as ClipboardManager
                cb.text = s //add to clipboard
                Toast.makeText(this@ComandasActivity, "Copied...", Toast.LENGTH_SHORT).show()
            }
            return myView
        }

        override fun getItem(position: Int): Any {
            return listComandasAdapter[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listComandasAdapter.size
        }

    }

        fun GoToUpdateFun(myComandas: Comandas) {
        var intent = Intent(this, AddComandasActivity::class.java)
        intent.putExtra("IDcomanda", myComandas.comandasID)//put ID
        intent.putExtra("mesaID", myComandas.comandasMID)//put IDmesa
        intent.putExtra("Estado", myComandas.comandasEstado) // put estado
            intent.putExtra("Nombre", myComandas.comandasNombre) // put nombre
        startActivity(intent) // start activity
    }


}



