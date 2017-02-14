package pe.devpicon.android.myfirstkotlinapplication.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import pe.devpicon.android.myfirstkotlinapplication.Comic

/**
 * Created by Armando on 14/2/2017.
 */
class DatabaseManager private constructor(context: Context){

    private val comicDbHelper: ComicDbHelper

    init {
        comicDbHelper = ComicDbHelper(context)
    }

    companion object {
        private var instance: DatabaseManager? = null

        @Synchronized fun getInstance(context: Context): DatabaseManager {
            if (instance == null) {
                instance = DatabaseManager(context.applicationContext)
            }

            return instance as DatabaseManager
        }
    }


    fun queryAllComics(): Cursor?{
        val database = comicDbHelper.readableDatabase
        return database.query(ComicContract.ComicEntry.TABLE_NAME, null, null, null, null, null, null)
    }

    fun insertComics(list: List<Comic>){
        val database = comicDbHelper.writableDatabase
        var comicValues: MutableList<ContentValues> = mutableListOf()

        list.forEach {
            val comicValue = ContentValues()
            comicValue.put(ComicContract.ComicEntry.COLUMN_NAME_TITLE, it.name)
            comicValue.put(ComicContract.ComicEntry.COLUMN_NAME_DESCRIPTION, it.description)
            comicValue.put(ComicContract.ComicEntry.COLUMN_NAME_IMAGEURL, it.imageUrl)
            comicValues.add(comicValue)
        }

        insertDataIntoDatabase(database, comicValues)
    }


    private fun insertDataIntoDatabase(db: SQLiteDatabase?, comicValues: MutableList<ContentValues>) {
        try {
            db?.beginTransaction()
            comicValues.forEach { db?.insert(ComicContract.ComicEntry.TABLE_NAME, null, it) }
            db?.setTransactionSuccessful()
        } catch (e: SQLException) {
            Log.e(javaClass.simpleName, "Ha ocurrido un error durante la inserción.", e )
        } finally {
            db?.endTransaction()
        }
    }
}