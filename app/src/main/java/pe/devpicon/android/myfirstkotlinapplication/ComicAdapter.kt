package pe.devpicon.android.myfirstkotlinapplication

import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.comic_item.view.*
import pe.devpicon.android.myfirstkotlinapplication.data.ComicContract

/**
 * Created by Armando on 11/2/2017.
 */
class ComicAdapter(var cursor: Cursor?) : RecyclerView.Adapter<ComicAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent?.context).inflate(R.layout.comic_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if (cursor == null || !(cursor!!.moveToPosition(position))) {
            return
        }

        val comic = getComicFromCursor(cursor!!)
        holder?.bindComic(comic)
    }

    override fun getItemCount(): Int = cursor?.count ?: 0

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bindComic(comic: Comic) {
            itemView.txt_comic_name.text = comic.name

            Glide.with(itemView.context)
                    .load(comic.imageUrl)
                    .fitCenter()
                    .override(48, 48)
                    .into(itemView.img_comic_thumbnail)

        }
    }

    private fun getComicFromCursor(cursor: Cursor): Comic {

        var comic = Comic(
                name = cursor.getString(cursor.getColumnIndex(ComicContract.ComicEntry.COLUMN_NAME_TITLE)),
                description = cursor.getString(cursor.getColumnIndex(ComicContract.ComicEntry.COLUMN_NAME_DESCRIPTION)),
                imageUrl = cursor.getString(cursor.getColumnIndex(ComicContract.ComicEntry.COLUMN_NAME_IMAGEURL))
        )

        return comic
    }


}

