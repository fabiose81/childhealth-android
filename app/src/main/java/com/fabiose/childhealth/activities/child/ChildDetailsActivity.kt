package com.fabiose.childhealth.activities.child

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.fabiose.childhealth.R
import com.fabiose.childhealth.activities.vaccine.VaccineHistoryDetailsActivity
import com.fabiose.childhealth.activities.vaccine.VaccineHistoryActivity
import com.fabiose.childhealth.adapters.VaccineHistoryAdapter
import com.fabiose.childhealth.domains.VaccineHistory
import com.fabiose.childhealth.domains.Child
import com.fabiose.childhealth.domains.Vaccine
import com.fabiose.childhealth.requests.IVaccineHistoryRestRequest
import com.fabiose.childhealth.requests.RetrofitInstance
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.JsonArray
import kotlin.collections.ArrayList
import android.graphics.BitmapFactory
import android.util.Base64


class ChildDetailsActivity : AppCompatActivity() {

    var child : Child? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child_details)

        child = getIntent().extras.getSerializable("child") as? Child

        val tvName = findViewById<TextView>(R.id.tvName)
        tvName.text = child?.name

        val format = SimpleDateFormat("yyyy-MM-dd")

        val tvAge = findViewById<TextView>(R.id.tvAge)
        tvAge.text = format.format(child?.dateBirth)

        val tvMotherName = findViewById<TextView>(R.id.tvMotherName)
        tvMotherName.text = child?.motherName

        val tvFatherName = findViewById<TextView>(R.id.tvFatherName)
        tvFatherName.text = child?.fatherName

        val ivChild = findViewById<ImageView>(R.id.ivChild)

       if(!child?.photo.isNullOrEmpty()){
           val imageBytes = Base64.decode(child?.photo, Base64.DEFAULT)
           val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
           ivChild.setImageBitmap(decodedImage)
       }



        val btAdd = findViewById<FloatingActionButton>(R.id.btAdd);
        btAdd.setOnClickListener {
            val intent = Intent(applicationContext, VaccineHistoryActivity::class.java)
            val vaccineHistory = VaccineHistory()
            vaccineHistory.child = child
            intent.putExtra("vaccineHistory", vaccineHistory)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        val lvHistoryVaccines = findViewById<ListView>(R.id.lvHistoryVaccines)

        val iVaccineHistoryRestRequest = RetrofitInstance.getRetrofitInstance().create(IVaccineHistoryRestRequest::class.java)
        val call = iVaccineHistoryRestRequest.findByChild(child?.id)

        call.enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>,
                                    response: Response<ResponseBody>
            ) {

                val vaccineHistories = ArrayList<VaccineHistory>()
                val jsonArrayVaccinesHistory = Gson().fromJson<JsonArray>(response.body().string(), object : TypeToken<JsonArray>() {
                }.type)

                val format = SimpleDateFormat("yyyy-MM-dd")

                jsonArrayVaccinesHistory.forEach {
                    val jsonObject = it.asJsonObject
                    val vaccine = Vaccine()
                    vaccine.id = jsonObject.get("vaccine_id").asInt
                    vaccine.name = jsonObject.get("vaccine_name").asString

                    val comment = if (jsonObject.get("comment").isJsonNull) "" else  jsonObject.get("comment").asString

                    val vaccineHistory = VaccineHistory(jsonObject.get("id").asInt,
                        jsonObject.get("weight").asFloat,
                        jsonObject.get("height").asInt,
                        comment,
                        child,
                        vaccine,
                        format.parse(jsonObject.get("date").asString))

                    vaccineHistories.add(vaccineHistory)
                }

                /*   val vaccineHistories = Gson().fromJson<ArrayList<VaccineHistory>>(response.body().string(), object : TypeToken<ArrayList<VaccineHistory>>() {
                   }.type)*/

                val vaccineHistoryAdapter = VaccineHistoryAdapter(applicationContext, vaccineHistories)
                lvHistoryVaccines.adapter = vaccineHistoryAdapter;

                lvHistoryVaccines.setOnItemClickListener { parent, view, position, id ->
                    val intent = Intent(applicationContext, VaccineHistoryDetailsActivity::class.java)
                    intent.putExtra("vaccine", vaccineHistories.get(position))
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<ResponseBody>,
                                   t: Throwable?) {

            }
        })
    }
}
