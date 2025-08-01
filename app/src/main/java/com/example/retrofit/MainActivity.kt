package com.example.retrofit

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.adapter.UserAdapter
import com.example.retrofit.model.DataItem
import com.example.retrofit.model.Responseuser
import com.example.retrofit.network.ApiConfig

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
Nama : M Taufik Iqbal
Kelas : P.Andro 4
Nim : 10122336
Tanggal Pengerjaan Terakhir : 07-29-2025 13:20
 */

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = UserAdapter(mutableListOf())

        val recyclerView = findViewById<RecyclerView>(R.id.rv_users)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        getUsers()


    }

    private fun getUsers(){
        val client = ApiConfig.getApiService().getListUsers("1")

        client.enqueue(object : Callback<Responseuser> {
            override fun onResponse(call: Call<Responseuser>, response: Response<Responseuser>) {
                if (response.isSuccessful){
                    val dataArray = response.body()?.data as List<DataItem>
                    for (data in dataArray){
                        adapter.addUser(data)
                    }
            }
        }
            override fun onFailure(call: Call<Responseuser>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })

    }
}