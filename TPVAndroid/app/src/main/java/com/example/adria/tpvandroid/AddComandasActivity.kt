package com.example.adria.tpvandroid

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_add_comandas.*
import kotlinx.android.synthetic.main.activity_add_productos.*
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception


class AddComandasActivity : AppCompatActivity() {

    var dbTable = "Comandas"
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_comandas)


        try {
            val bundle: Bundle = intent.extras
            id = bundle.getInt("id", 0)
            if (id != 0) {
                mesaEt.setText(bundle.getInt("mesaid").toString())
                estadoEt.setText(bundle.getString("est"))
                input_product.setText(bundle.getString("nom"))
            }
        } catch (ex: Exception) {
        }
        val myStrings = arrayOf("Elegir producto...", "Vegetal de atun", "Vegetal de pollo", "Hamburguesa", "Coca Cola")
        Myspinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, myStrings)

    }

    fun addFunc(view: View) {
        if (validate()) {
            if (id == 0) {
                var dbManager = DbManager(this)

                var values = ContentValues()
                values.put("mesaID", mesaEt.text.toString())
                values.put("Estado", estadoEt.text.toString())
                values.put("Nombre", input_product.text.toString())
                val ID = dbManager.insert(values)

                var postComandas = JSONObject()

                postComandas.put("mesaid", mesaEt.text.toString())
                postComandas.put("estado", estadoEt.text.toString())
                postComandas.put("nombre", input_product.text.toString())
                var url = "http://192.168.1.120:3000/comandas"

                val queue = Volley.newRequestQueue(this)

                val req = JsonObjectRequest(
                    Request.Method.POST, url, postComandas,
                    Response.Listener { response ->

                    },
                    Response.ErrorListener { error: VolleyError ->
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                    }
                )
                queue.add(req)
                if (ID > 0) {
                    Toast.makeText(this, "La comanda ha sido añadida", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al añadir la comanda", Toast.LENGTH_SHORT).show()
                }
            } else {
                var selectionArgs = arrayOf(id.toString())
                var dbManager = DbManager(this)
                var values = ContentValues()
                values.put("IDcomanda", id)
                values.put("mesaID", mesaEt.text.toString())
                values.put("Estado", estadoEt.text.toString())
                values.put("Nombre", input_product.text.toString())
                val ID = dbManager.update(values, "IDcomanda=?", selectionArgs)
                var postComandas = JSONObject()

                postComandas.put("id", id)
                postComandas.put("mesaid", mesaEt.text.toString())
                postComandas.put("estado", estadoEt.text.toString())
                postComandas.put("nombre", input_product.text.toString())

                var url = "http://192.168.1.120:3000/comandas/" + id

                val queue = Volley.newRequestQueue(this)

                val req = JsonObjectRequest(
                    Request.Method.PUT, url, postComandas,
                    Response.Listener { response ->

                    },
                    Response.ErrorListener { error: VolleyError ->
                    }
                )
                queue.add(req)
                if (ID > 0) {
                    Toast.makeText(this, "La mesa ha sido actualizada", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al actualizar la mesa", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun validate(): Boolean {
        if (mesaEt.text.isEmpty()){
            Toast.makeText(this, "El campo mesa esta vacio", Toast.LENGTH_SHORT).show()
            return false
        } else {
            if (estadoEt.text.isEmpty()){
            Toast.makeText(this, "El campo estado esta vacio", Toast.LENGTH_SHORT).show()
                return false
        } else {
                if (input_product.text.isEmpty()) {
                    Toast.makeText(this, "El campo producto esta vacio", Toast.LENGTH_SHORT).show()
                    return false
                } else {
                    return true
                }
            }}}

    fun addProduct(view: View) =
        if (Myspinner.selectedItem.equals("Elegir Producto...")) {
            Toast.makeText(this, "Elija un producto", Toast.LENGTH_LONG).show()

        } else {

            var areaProduct = input_product

            if (areaProduct.text.toString().equals("")) {
                areaProduct.append(this.Myspinner.selectedItem.toString())
            } else {
                areaProduct.append(", " + this.Myspinner.selectedItem.toString())
            }
            val myStrings =
                arrayOf("Elegir producto...", "Vegetal de atun", "Vegetal de pollo", "Hamburguesa", "Coca Cola")
            Myspinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, myStrings)
        }

}
