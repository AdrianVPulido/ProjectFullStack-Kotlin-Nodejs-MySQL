/*package com.example.adria.tpvandroid

import android.content.ContentValues
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_server.*
import kotlinx.android.synthetic.main.rowscomandas.view.*
import org.json.JSONException

class ServerActivity : AppCompatActivity() {


    var listComandas = ArrayList<Comandas>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server)

        //Cargar db local
        LoadQuery("%")

        //Cargar db-servidor

        var url = "http://192.168.201.25:3000/comandas"

        val queue = Volley.newRequestQueue(this)

        val req = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    /*if(response.length()!= comandasLv.count){
                        var dbManager = DbManager(this)
                        dbManager.deleteAllEmpresas()
                        LoadQuery("%")
                        //Borrar aqui
                    }*/
                    // Loop de array
                    for (i in 0 until response.length()) {
                        var values = ContentValues()
                        val aux = response.getJSONObject(i)
                        values.put("IDcomanda", aux.getInt("idcomanda"))
                        values.put("mesaID", aux.getInt("mesaid"))
                        values.put("Estado", aux.getString("estado"))
                        values.put("Nombre", aux.getString("nombre"))

                        var dbManager = DbManager(this)
                        dbManager.insert(values)
                        onResume()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error: VolleyError ->
                Toast.makeText(this, "No conectado a Internet", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(req)

    }

    override fun onResume() {
        super.onResume()
        LoadQuery("%")
    }

    private fun LoadQuery(title: String) {
        val dbManager = DbManager(this)
        val projections = arrayOf("IDcomanda", "mesaID", "Estado", "Nombre")
        val selectionArgs = arrayOf(title)
        val cursor = dbManager.Query(projections, "IDcomanda like ?", selectionArgs, "IDcomanda")
        listComandas.clear()

        if (cursor!!.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("IDcomanda"))
                val idmesa = cursor.getInt(cursor.getColumnIndex("mesaID"))
                val estado = cursor.getString(cursor.getColumnIndex("Estado"))
                val nombre = cursor.getString(cursor.getColumnIndex("Nombre"))

                listComandas.add(Comandas(id, idmesa, estado, nombre))
            } while (cursor.moveToNext())
        }
        var myComandasAdapter = myComandasAdapter(this, listComandas)
        //set adapter
        comandasLv.adapter = myComandasAdapter
        //Total number of task
        val total = comandasLv.count
        //Action Bar
        val mActionBar = supportActionBar
        if (mActionBar != null) {
            //set to action bar as subtitle of actionbat
            mActionBar.subtitle = "-Hay " + total + " comandas-"
        }
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val sv: SearchView = menu!!.findItem(R.id.app_bar_search).actionView as SearchView
        val sm = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        sv.setSearchableInfo(sm.getSearchableInfo(componentName))
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                LoadQuery("%" + query + "%")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                LoadQuery("%" + newText + "%")
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when (item.itemId) {
                R.id.sync -> {

                    var url = "http://192.168.202.98:8696/empresas"
                    val queue1 = Volley.newRequestQueue(this)
                    val req = JsonArrayRequest(
                        Request.Method.GET, url, null,
                        Response.Listener { response ->
                            try {
                                if(response.length()!= comandasLv.count){
                                    var dbManager = DbManager(this)
                                    dbManager.deleteAllEmpresas()
                                    LoadQuery("%")
                                    //Borrar aqui
                                }
                                // Loop through the array elements
                                for (i in 0 until response.length()) {
                                    var values = ContentValues()
                                    val aux = response.getJSONObject(i)
                                    values.put("id", aux.getInt("id"))
                                    values.put("nombre", aux.getString("nombre"))
                                    values.put("direccion", aux.getString("direccion"))
                                    values.put("telefono", aux.getString("telefono"))
                                    values.put("correo", aux.getString("correo"))

                                    var dbManager = DbManager(this)
                                    dbManager.insertEmpresas(values)
                                }
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        },
                        Response.ErrorListener { error: VolleyError ->
                            println("Error $error.message")
                        }
                    )
                    queue1.add(req)
                    Toast.makeText(this, "Empresas borradas y sincronizado", Toast.LENGTH_SHORT).show()
                    onResume()
                }
                R.id.action_settings -> {
                    Toast.makeText(this, "Configuraciones por añadir", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }*/

    inner class myComandasAdapter : BaseAdapter {
        var listComandasAdapter = ArrayList<Comandas>()
        var context: Context? = null

        constructor(context: Context, listComandasAdapter: ArrayList<Comandas>) : super() {
            this.listComandasAdapter = listComandasAdapter
            this.context = context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myView = layoutInflater.inflate(R.layout.rowscomandas, null)
            var myComandas = listComandasAdapter[position]
            myView.idcTv.text = myComandas.comandasID.toString()
            myView.idmTv.text = myComandas.comandasMID.toString()
            myView.estTv.text = myComandas.comandasEstado
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

}
*/
