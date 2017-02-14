package pe.devpicon.android.myfirstkotlinapplication.data

import android.provider.BaseColumns

/**
 * Created by Armando on 14/2/2017.
 */
object ComicContract {
    class ComicEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "comics"
            val COLUMN_NAME_TITLE = "title"
            val COLUMN_NAME_DESCRIPTION = "description"
            val COLUMN_NAME_IMAGEURL = "imageUrl"
        }
    }
}