package com.fabiose.childhealth.activities.vaccine

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.fabiose.childhealth.R
import com.fabiose.childhealth.domains.VaccineHistory
import com.fabiose.childhealth.requests.IVaccineHistoryRestRequest
import com.fabiose.childhealth.requests.RetrofitInstance
import java.text.SimpleDateFormat
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VaccineHistoryDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vaccine_history_details)

        val vaccineHistory = getIntent().extras.getSerializable("vaccine") as? VaccineHistory

        val tvNameChild = findViewById<TextView>(R.id.tvNameChild)
        tvNameChild.text ="Name: ".plus(vaccineHistory?.child?.name)

        val tvHeight = findViewById<TextView>(R.id.tvHeight)
        tvHeight.text ="Height: ".plus(vaccineHistory?.height)

        val tvWeight = findViewById<TextView>(R.id.tvWeight)
        tvWeight.text ="Weight: ".plus(vaccineHistory?.weight)

        val tvVaccineName = findViewById<TextView>(R.id.tvVaccineName)
        tvVaccineName.text ="Vaccine: ".plus(vaccineHistory?.vaccine?.name)

        val format = SimpleDateFormat("yyyy-MM-dd")

        val tvDate = findViewById<TextView>(R.id.tvDate)
        tvDate.text ="Date: ".plus(format.format(vaccineHistory?.date))

        val tvComment = findViewById<TextView>(R.id.tvComment)
        tvComment.text = vaccineHistory?.comment

        val btDelete = findViewById<Button>(R.id.btDelete)
        btDelete.setOnClickListener {
            val iVaccineHistoryRestRequest = RetrofitInstance.getRetrofitInstance().create(IVaccineHistoryRestRequest::class.java)

            val call = iVaccineHistoryRestRequest.delete(vaccineHistory)

            call.enqueue(object: Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>,
                                        response: Response<ResponseBody>
                ) {
                    if(response.code() == 200) {
                        onBackPressed()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>,
                                       t: Throwable?) {
                }
            })
        }
    }
}
