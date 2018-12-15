package com.example.adria.tpvandroid

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_comandas.*
import java.lang.Exception


class AddComandasActivity : AppCompatActivity() {

    var dbTable = "Comandas"
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_comandas)
        val myStrings = arrayOf("Elegir producto...", "Vegetal de atun", "Vegetal de pollo", "Hamburguesa", "Coca Cola")
        Myspinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, myStrings)

        try {
            val bundle: Bundle = intent.extras
            id = bundle.getInt("IDcomanda", 0)
            if (id != 0) {
                mesaEt.setText(bundle.getString("mesaID"))
                estadoEt.setText(bundle.getString("Estado"))
                input_product.setText(bundle.getString("Nombre"))
            }
        } catch (ex: Exception) {
        }
    }

    fun addProduct (view: View) =
        if(Myspinner.selectedItem.equals("Elegir Producto...")) {
            Toast.makeText(this, "Elija un producto", Toast.LENGTH_LONG).show()

        }else{

            var areaProduct = input_product

            if(areaProduct.text.toString().equals("")){
                areaProduct.append(this.Myspinner.selectedItem.toString())
            }else{
                areaProduct.append(", " + this.Myspinner.selectedItem.toString())
            }
            val myStrings = arrayOf("Elegir producto...", "Vegetal de atun", "Vegetal de pollo", "Hamburguesa", "Coca Cola")
            Myspinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, myStrings)
        }

    fun addFunc(view: View) {
        var dbManager = DbManager(this)
        if (mesaEt.text.isEmpty()) {
            Toast.makeText(this, "El campo mesa esta vacio", Toast.LENGTH_SHORT).show()
        } else {
            if(estadoEt.text.isEmpty()){
                Toast.makeText(this, "El campo estado esta vacio", Toast.LENGTH_SHORT).show()
            } else {
                if (input_product.text.isEmpty()) {
                    Toast.makeText(this, "El campo producto esta vacio", Toast.LENGTH_SHORT).show()
                } else {
                    var values = ContentValues()
                    values.put("mesaID", mesaEt.text.toString())
                    values.put("Estado", estadoEt.text.toString())
                    values.put("Nombre", input_product.text.toString())
                    if (id == 0) {
                        val ID = dbManager.insert(values)
                        if (ID > 0) {
                            Toast.makeText(this, "La comanda ha sido añadida", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this, "Error al añadir la comanda", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        var selectionArgs = arrayOf(id.toString())
                        if (mesaEt.text.isEmpty()) {
                            Toast.makeText(this, "El campo mesa esta vacio", Toast.LENGTH_SHORT).show()
                        } else {
                            if (estadoEt.text.isEmpty()) {
                                Toast.makeText(this, "El campo estado esta vacio", Toast.LENGTH_SHORT).show()
                            } else {
                                if (input_product.text.isEmpty()) {
                                    Toast.makeText(this, "El campo producto esta vacio", Toast.LENGTH_SHORT).show()
                                } else {
                                val ID = dbManager.update(values, "IDcomanda=?", selectionArgs)
                                if (ID > 0) {
                                    Toast.makeText(this, "La mesa ha sido actualizada", Toast.LENGTH_SHORT).show()
                                    finish()
                                } else {
                                    Toast.makeText(this, "Error al actualizar la mesa", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }}
} }}
