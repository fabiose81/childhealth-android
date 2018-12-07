package com.fabiose.childhealth.activities.child

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.View
import android.widget.ListView
import com.fabiose.childhealth.R
import com.fabiose.childhealth.adapters.ChildAdapter
import com.fabiose.childhealth.domains.Child
import com.fabiose.childhealth.requests.IChildRestRequest
import com.fabiose.childhealth.requests.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class ListChildActivity : AppCompatActivity() {

    var lvChidren : ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_child)

        lvChidren = findViewById<ListView>(R.id.lvChidren)

        val btAdd = findViewById<FloatingActionButton>(R.id.btAdd);
        btAdd.setOnClickListener {
            val intent = Intent(applicationContext, ChildActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()

        val iChildRestRequest = RetrofitInstance.getRetrofitInstance().create(IChildRestRequest::class.java)
        val call = iChildRestRequest.findAll()
        call.enqueue(object: Callback<ArrayList<Child>> {
            override fun onResponse(call: Call<ArrayList<Child>>,
                                    response: Response<ArrayList<Child>>) {

                var children = response.body()
                val childAdapter = ChildAdapter(applicationContext, children)
                lvChidren?.adapter = childAdapter;

            }

            override fun onFailure(call: Call<ArrayList<Child>>,
                                   t: Throwable?) {
            }

        })
    }
}
