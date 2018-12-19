package com.example.adria.tpvandroid

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_productos.*
import java.lang.Exception


class AddProductosActivity : AppCompatActivity() {

    var dbTable = "Productos"
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_productos)

        try {
            val bundle: Bundle = intent.extras
            id = bundle.getInt("IDproducto", 0)
            if (id != 0) {
                cproductoEt.setText(bundle.getString("Categoria"))
                nproductoEt.setText(bundle.getString("Nombre"))
                pproductoEt.setText(bundle.getDouble("Precio"))
            }
        } catch (ex: Exception) {
        }
    }


    fun addFunc(view: View) {
        var dbManager = DbManager(this)
        if (cproductoEt.text.isEmpty()) {
            Toast.makeText(this, "El campo categoria esta vacio", Toast.LENGTH_SHORT).show()
        } else {
            if(nproductoEt.text.isEmpty()){
                Toast.makeText(this, "El campo nombre esta vacio", Toast.LENGTH_SHORT).show()
            } else {
                if (pproductoEt.text.isEmpty()) {
                    Toast.makeText(this, "El campo precio esta vacio", Toast.LENGTH_SHORT).show()
                } else {
                    var values = ContentValues()
                    values.put("Categoria", cproductoEt.text.toString())
                    values.put("Nombre", nproductoEt.text.toString())
                    values.put("Precio", pproductoEt.text.toString())
                    if (id == 0) {
                        val ID = dbManager.insertp(values)
                        if (ID > 0) {
                            Toast.makeText(this, "El Producto ha sido añadido", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this, "Error al añadir el producto", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        var selectionArgs = arrayOf(id.toString())
                        if (cproductoEt.text.isEmpty()) {
                            Toast.makeText(this, "El campo producto esta vacio", Toast.LENGTH_SHORT).show()
                        } else {
                            if (nproductoEt.text.isEmpty()) {
                                Toast.makeText(this, "El campo nombre esta vacio", Toast.LENGTH_SHORT).show()
                            } else {
                                if (pproductoEt.text.isEmpty()) {
                                    Toast.makeText(this, "El campo precio esta vacio", Toast.LENGTH_SHORT).show()
                                } else {
                                    val ID = dbManager.updatep(values, "IDproducto=?", selectionArgs)
                                    if (ID > 0) {
                                        Toast.makeText(this, "El producto ha sido actualizado", Toast.LENGTH_SHORT).show()
                                        finish()
                                    } else {
                                        Toast.makeText(this, "Error al actualizar el producto", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    } }

private fun EditText.setText(double: Double) {

}

