package com.example.noteverse

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NoteDataBaseClass(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "noteverse.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "mynotes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_CONTENT = "description"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_CONTENT TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertData(note: NoteDataClass) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, note.name)
            put(COLUMN_CONTENT, note.description)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getNote(): List<NoteDataClass> {
        val notelist = mutableListOf<NoteDataClass>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val descript = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

            val note = NoteDataClass(id, name, descript)
            notelist.add(note)
        }

        cursor.close()
        db.close()
        return notelist
    }

    fun deleteNote(noteId : Int){
        val db =writableDatabase
        val whereColumn ="$COLUMN_ID = ?"
        val whereArgs = arrayOf(noteId.toString())
        db.delete(TABLE_NAME,whereColumn,whereArgs)
        db.close()
    }
}
