package pe.devpicon.android.myfirstkotlinapplication.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID
import pe.devpicon.android.myfirstkotlinapplication.data.ComicContract

/**
 * Created by Armando on 14/2/2017.
 */
class ComicDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    override fun onCreate(db: SQLiteDatabase?) {

        val SQL_CREATE_COMIC_TABLE = "CREATE TABLE ${ComicContract.ComicEntry.TABLE_NAME} (" +
                "${_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${ComicContract.ComicEntry.COLUMN_NAME_TITLE} TEXT NOT NULL," +
                "${ComicContract.ComicEntry.COLUMN_NAME_DESCRIPTION} TEXT NOT NULL," +
                "${ComicContract.ComicEntry.COLUMN_NAME_IMAGEURL} TEXT NOT NULL" +
                ");"

        if(db != null){
            db.execSQL(SQL_CREATE_COMIC_TABLE)
        }

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if(db != null){
            db.execSQL("DROP TABLE IF EXISTS ${ComicContract.ComicEntry.TABLE_NAME}")
            onCreate(db)
        }
    }

    companion object {
        val DATABASE_NAME = "comics.db"
        val DATABASE_VERSION = 1
    }

}