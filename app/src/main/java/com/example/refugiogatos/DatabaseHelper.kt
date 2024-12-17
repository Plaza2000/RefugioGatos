package com.example.refugiogatos


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "GatosDB"
        private const val TABLE_GATOS = "Gatos"
        private const val KEY_ID = "id"
        private const val KEY_NOMBRE = "nombre"
        private const val KEY_RAZA = "raza"
        private const val KEY_EDAD = "edad"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_GATOS("
                + "$KEY_ID INTEGER PRIMARY KEY,"
                + "$KEY_NOMBRE TEXT,"
                + "$KEY_RAZA TEXT,"
                + "$KEY_EDAD INTEGER)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_GATOS")
        onCreate(db)
    }

    fun addGato(gato: Gato): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NOMBRE, gato.nombre)
        values.put(KEY_RAZA, gato.raza)
        values.put(KEY_EDAD, gato.edad)
        val success = db.insert(TABLE_GATOS, null, values)
        db.close()
        return success
    }

    @SuppressLint("Range")
    fun getAllGatos(): ArrayList<Gato> {
        val gatosList = ArrayList<Gato>()
        val selectQuery = "SELECT * FROM $TABLE_GATOS"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val gato = Gato(
                    id = cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                    nombre = cursor.getString(cursor.getColumnIndex(KEY_NOMBRE)),
                    raza = cursor.getString(cursor.getColumnIndex(KEY_RAZA)),
                    edad = cursor.getInt(cursor.getColumnIndex(KEY_EDAD))
                )
                gatosList.add(gato)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return gatosList
    }

    fun updateGato(gato: Gato): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NOMBRE, gato.nombre)
        values.put(KEY_RAZA, gato.raza)
        values.put(KEY_EDAD, gato.edad)
        val success = db.update(TABLE_GATOS, values, "$KEY_ID=?", arrayOf(gato.id.toString()))
        db.close()
        return success
    }

    fun deleteGato(id: Int): Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_GATOS, "$KEY_ID=?", arrayOf(id.toString()))
        db.close()
        return success
    }



}
