package pe.devpicon.android.myfirstkotlinapplication

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_main3.*
import org.jetbrains.anko.UI
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONArray
import org.json.JSONObject
import pe.devpicon.android.myfirstkotlinapplication.data.DatabaseManager
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class Main3Activity : AppCompatActivity() {

    val TAG = javaClass.simpleName

    private var comicAdapter: ComicAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        comic_recyclerview.layoutManager = LinearLayoutManager(this)
        comicAdapter = ComicAdapter(null)
        comic_recyclerview.adapter = comicAdapter

        doAsync {

            val comics: List<Comic> = getComicsFromService(this@Main3Activity)
            val databaseManager = DatabaseManager.getInstance(this@Main3Activity)
            databaseManager.insertComics(comics)

            val cursor = databaseManager.queryAllComics()

            uiThread {

                if(comicAdapter != null){
                    comicAdapter!!.cursor = cursor
                    comicAdapter!!.notifyDataSetChanged()
                }
            }
        }

    }

    private fun getComicsFromService(context: Context): List<Comic> {

        val url = URL(COMPLETE_URL)
        val conn : HttpURLConnection = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.connect()

        val comicJson : String = getComicJson(conn)
        val comics : MutableList<Comic> = convertJsonToComicObjects(comicJson)

        return comics
    }

    private fun convertJsonToComicObjects(comicJson: String): MutableList<Comic> {
        Log.d(TAG, "-> convertJsonToComicObject")

        val comicJson = JSONObject(comicJson)

        Log.d(TAG, "Return code: ${comicJson.getInt("code")}")

        val data = comicJson.getJSONObject("data")
        val results = data.getJSONArray("results")
        val comicList: MutableList<Comic> = mutableListOf()

        for (i in 0..(results.length() - 1)) {
            val item = results.getJSONObject(i)

            val comic = Comic(
                    name = item.getString("title"),
                    imageUrl = getImageURL(item.getJSONArray("images")),
                    description = item.optString("description", "")

            )

            comicList.add(comic)

        }

        return comicList

    }

    private fun getImageURL(jsonArray: JSONArray?): String {
        if(jsonArray != null && jsonArray.length() > 0 ) {
            val imageJsonObject = jsonArray.getJSONObject(0)
            return generateImageURL(imageJsonObject.getString("path"),
                    imageJsonObject.getString("extension"))
        }
        return ""
    }


    private fun getComicJson(conn: HttpURLConnection): String {

        var comicJson: String = ""
        val inputStream: InputStream? = conn.inputStream
        val buffer = StringBuffer()

        if (inputStream != null) {
            val reader: BufferedReader = BufferedReader(InputStreamReader(inputStream))

            var line: String? = null;

            while ({ line = reader.readLine(); line }() != null) {
                Log.d(javaClass.simpleName, line)
                buffer.append(line + "\n")
            }

            reader.close()
            comicJson = buffer.toString()
        }

        return comicJson
    }

    companion object {
        private val PUBLIC_API_KEY = "a953888a4098cff50b054c9375839786"
        private val RANDOM_WORD = "armando"
        private val HASH = "7f3db60eab45a2c204d69b8aafdcfbc9"
        private val COMIC_BASE_URL = "https://gateway.marvel.com/v1/public/comics"
        private val COMPLETE_URL = "${COMIC_BASE_URL}?ts=${RANDOM_WORD}&apikey=${PUBLIC_API_KEY}&hash=${HASH}"
    }

    fun generateImageURL(path: String, extension: String) = "$path.$extension"
}
