package com.example.alaasa_oblig2
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitString
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class MainActivity : AppCompatActivity() {

    private val tag= "MainActivity" // for logging purposes
    private val baseUrl= "https://www.uio.no/studier/emner/matnat/ifi/IN2000/v20/obligatoriske-oppgaver/alpakka2000.json "
    var gson = Gson()
    var list :MutableList<Alpaca> = mutableListOf()
    val adapter = ListAdapter(this,list)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(IO).launch{
            hentAlpaca()
            CoroutineScope(Main).launch{
                prog()
            }
        }
        my_recyclerView.layoutManager = LinearLayoutManager(this)
        //my_recyclerView.adapter = adapter

    }

    suspend fun hentAlpaca(){
        try {
            val response:String = Fuel.get(baseUrl).awaitString()
            Log.w(tag, response)
            val objekter:MutableList<Alpaca> =  gson.fromJson<MutableList<Alpaca>>(response,object :TypeToken<MutableList<Alpaca>>(){}.type)

            list.addAll(objekter)

        }catch (e  : Exception) {
            e.printStackTrace()
            this@MainActivity.runOnUiThread{Toast.makeText(this@MainActivity, "ingen JSON-objekter", Toast.LENGTH_SHORT).show()}
        }

    }

    suspend fun prog() {
        progressBar3.visibility = View.VISIBLE
        delay(2000)
        progressBar3.visibility = View.GONE
        if (list.size != 0) {
            adapter.notifyDataSetChanged()
            my_recyclerView.adapter = adapter
        }else {
            Toast.makeText(this@MainActivity, "ingen JSON-objekter", Toast.LENGTH_SHORT).show()
        }
    }
}

