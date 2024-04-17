package com.example.retrofitadd

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var edname:EditText
    lateinit var edemail:EditText
    lateinit var btnsub:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edname = findViewById(R.id.edtname)
        edemail = findViewById(R.id.edtemail)
        btnsub = findViewById(R.id.btnsubmit)

        btnsub.setOnClickListener {
            addData()
        }

    }

    private fun addData() {

        var dialog = ProgressDialog(this)
        dialog.setMessage("Loadding..")
        dialog.setCancelable(true)
        dialog.show()

        var retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServer::class.java)

        var name = edname.text.toString()
        var email = edemail.text.toString()

        var data = Datatype(0,0,name,email, "0")

        var call = retrofit.AddItem(data)

        call.enqueue(object :Callback<Datatype>{
            override fun onResponse(call: Call<Datatype>, response: Response<Datatype>) {
                if(response.isSuccessful){
                    var r = response.body()
                    Log.d("api detail","result: ${r}")
                    if(dialog.isShowing) dialog.dismiss()
                    Toast.makeText(this@MainActivity, "details entered", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Datatype>, t: Throwable) {
                Log.e("Api error","error: ${t.toString()}")
                if(dialog.isShowing) dialog.dismiss()
            }

        })
    }
}