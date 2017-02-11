package pe.devpicon.android.myfirstkotlinapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    val fruits = arrayListOf("banana", "coconut", "orange", "strawberry")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myRecyclerView = findViewById(R.id.my_recycler_view) as RecyclerView
        myRecyclerView.layoutManager = LinearLayoutManager(this)
        myRecyclerView.adapter = FruitAdapter(fruits)

    }
}
