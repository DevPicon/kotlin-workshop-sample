package pe.devpicon.android.myfirstkotlinapplication

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by Armando on 11/2/2017.
 */
class FruitAdapter(var fruits: List<String>) : RecyclerView.Adapter<FruitAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent?.context).inflate(R.layout.card_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.txtFruitName?.text = fruits.get(position)
    }

    override fun getItemCount(): Int = fruits.size ?: 0

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var txtFruitName: TextView

        init {
            txtFruitName = itemView?.findViewById(R.id.txt_fruit_name) as TextView
        }
    }
}

