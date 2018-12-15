package com.example.adria.tpvandroid

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

class DbManager {

    //database name
    var dbName = "TPVAndroid"
    //table name
    var dbTable = "Comandas"
    var dbTablep = "Productos"
    //columns Comandas
    var colID = "IDcomanda"
    var colIDM = "mesaID"
    var colestado = "Estado"
    var colNombre = "Nombre"
    //colums Productos
    var colPID = "IDproducto"
    var colPNombre = "Nombre"
    var colCategoria = "Categoria"
    var colPrecio = "Precio"
    //Database Version
    var dbVersion = 1

    //Create Table
    val sqlCreateTable = "CREATE TABLE IF NOT EXISTS $dbTable($colID INTEGER PRIMARY KEY AUTOINCREMENT,$colIDM VARCHAR(50) NOT NULL,$colestado VARCHAR(50) NOT NULL, $colNombre VARCHAR(200) NOT NULL)"
    val sqlCreateTable2 = "CREATE TABLE IF NOT EXISTS $dbTablep($colPID INTEGER PRIMARY KEY AUTOINCREMENT,$colPNombre VARCHAR(50) NOT NULL, $colCategoria VARCHAR(50) NOT NULL, $colPrecio DOUBLE NOT NULL)"

    var sqlDB:SQLiteDatabase?=null

    constructor(context: Context) {
        var db = DatabaseHelperMesas(context)
        sqlDB = db.writableDatabase
    }

     inner class DatabaseHelperMesas:SQLiteOpenHelper{
        var context: Context?=null
        constructor(context: Context):super(context,dbName,null,dbVersion){
            this.context = context
        }

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(sqlCreateTable)
            db!!.execSQL(sqlCreateTable2)
            Toast.makeText(this.context, "database created...", Toast.LENGTH_SHORT).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("Drop table if Exists$dbTable")
            db!!.execSQL("Drop table if Exists$dbTablep")

        }
    }

    fun insert(values: ContentValues):Long{
        val ID = sqlDB!!.insert(dbTable,"",values)
        return ID
    }

    fun Query(projection:Array<String>, selection:String, selectionArgs:Array<String>, sorOrder: String): Cursor{
        val qb = SQLiteQueryBuilder();
        qb.tables = dbTable
        val cursor = qb.query(sqlDB, projection, selection, selectionArgs, null, null, sorOrder)
        return cursor
    }

    fun delete(selection:String, selectionArgs:Array<String>):Int{
        val count = sqlDB!!.delete(dbTable, selection, selectionArgs)
        return count
    }

    fun update(values: ContentValues, selection: String, selectionArgs: Array<String>):Int {
        val count = sqlDB!!.update(dbTable, values, selection, selectionArgs)
        return count
    }

    //Funciones de la tabla Producto

    fun insertp(values: ContentValues):Long{
        val IDp = sqlDB!!.insert(dbTablep,"",values)
        return IDp
    }

    fun Queryp(projection:Array<String>, selection:String, selectionArgs:Array<String>, sorOrder: String): Cursor{
        val qb = SQLiteQueryBuilder();
        qb.tables = dbTablep
        val cursor = qb.query(sqlDB, projection, selection, selectionArgs, null, null, sorOrder)
        return cursor
    }

    fun deletep(selection:String, selectionArgs:Array<String>):Int{
        val count = sqlDB!!.delete(dbTablep, selection, selectionArgs)
        return count
    }

    fun updatep(values: ContentValues, selection: String, selectionArgs: Array<String>):Int {
        val count = sqlDB!!.update(dbTablep, values, selection, selectionArgs)
        return count
    }


}