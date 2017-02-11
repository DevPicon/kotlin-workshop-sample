package pe.devpicon.android.myfirstkotlinapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtHelloWold = findViewById(R.id.txt_hello_world) as TextView
        txtHelloWold.text = "Hola Armando"
    }
}
