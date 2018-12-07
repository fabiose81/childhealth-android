package com.fabiose.childhealth.activities.vaccine

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.fabiose.childhealth.R
import com.fabiose.childhealth.adapters.VaccineAdapter
import com.fabiose.childhealth.domains.Vaccine
import com.fabiose.childhealth.domains.VaccineHistory
import com.fabiose.childhealth.requests.IVaccineHistoryRestRequest
import com.fabiose.childhealth.requests.IVaccineRestRequest
import com.fabiose.childhealth.requests.RetrofitInstance
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class VaccineHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vaccine_history)

        val vaccineHistory = getIntent().extras.getSerializable("vaccineHistory") as? VaccineHistory

        var vaccine : Vaccine? = null

        var tvNameChild = findViewById<TextView>(R.id.tvNameChild)
        tvNameChild.text = vaccineHistory?.child?.name

        val spVaccine = findViewById<Spinner>(R.id.spVaccine)

        val iVaccineRestRequest = RetrofitInstance.getRetrofitInstance().create(IVaccineRestRequest::class.java)
        val call = iVaccineRestRequest.findAll()
        call.enqueue(object: Callback<ArrayList<Vaccine>> {
            override fun onResponse(call: Call<ArrayList<Vaccine>>,
                                    response: Response<ArrayList<Vaccine>>
            ) {

                var vaccines = response.body()
                val vaccineAdapter = VaccineAdapter(applicationContext, vaccines)
                spVaccine.adapter = vaccineAdapter

                spVaccine.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        vaccine = vaccines.get(position)
                    }

                }
            }

            override fun onFailure(call: Call<ArrayList<Vaccine>>,
                                   t: Throwable?) {
            }
        })

        var date : Date? = null
        val format = SimpleDateFormat("yyyy-MM-dd")

        val tvDate = findViewById<TextView>(R.id.tvDate)
        tvDate.setOnClickListener {

            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            val dialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, year, month, day ->
                tvDate.text = year.toString() +"-"+(month + 1)+"-"+day
                date = format.parse(tvDate.text.toString())
            },year,month,day)

            dialog.datePicker.maxDate = cal.timeInMillis

            dialog.show()
        }

        val btSave = findViewById<Button>(R.id.btSave);
        btSave.setOnClickListener {

            val etWeight = findViewById<EditText>(R.id.etWeight)
            val etHeight = findViewById<EditText>(R.id.etHeight)
            val etComment = findViewById<EditText>(R.id.etComment)

            vaccineHistory?.date = date
            vaccineHistory?.weight = etWeight.text.toString().toFloatOrNull()!!
            vaccineHistory?.height = etHeight.text.toString().toIntOrNull()!!
            vaccineHistory?.comment = etComment.text.toString()
            vaccineHistory?.child = vaccineHistory?.child
            vaccineHistory?.vaccine = vaccine

            val iVaccineHistoryRestRequest = RetrofitInstance.getRetrofitInstance().create(IVaccineHistoryRestRequest::class.java)

            val call = iVaccineHistoryRestRequest.save(vaccineHistory)

            call.enqueue(object: Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>,
                                        response: Response<ResponseBody>
                ) {

                   if(response.code() == 200){
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
