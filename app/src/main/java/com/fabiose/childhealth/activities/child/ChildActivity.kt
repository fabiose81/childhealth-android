package com.fabiose.childhealth.activities.child

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.fabiose.childhealth.R
import com.fabiose.childhealth.domains.Child
import com.fabiose.childhealth.requests.IChildRestRequest
import com.fabiose.childhealth.requests.RetrofitInstance
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*
import android.provider.MediaStore
import android.graphics.Bitmap
import java.io.ByteArrayOutputStream


class ChildActivity : AppCompatActivity() {

    var photo : String? = null
    var ivChild : ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child)

        var dateBirth : Date? = null
        val format = SimpleDateFormat("yyyy-MM-dd")

        val tvDateBirth = findViewById<TextView>(R.id.tvDateBirth)
        tvDateBirth.setOnClickListener {

            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            val dialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view,year,month,day ->
                tvDateBirth.text = year.toString() +"-"+(month + 1)+"-"+day
                dateBirth = format.parse(tvDateBirth.text.toString())
            },year,month,day)

            dialog.datePicker.maxDate = cal.timeInMillis
            dialog.show()
        }

        ivChild = findViewById<ImageView>(R.id.ivChild)
        ivChild?.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            if (intent.resolveActivity(packageManager) != null) {
                startActivityForResult(intent, 1)
            }
        }

        val btSave = findViewById<Button>(R.id.btSave);
        btSave.setOnClickListener {

            val etNameChild = findViewById<EditText>(R.id.etNameChild)
            val etNameMother = findViewById<EditText>(R.id.etNameMother)
            val etNameFather = findViewById<EditText>(R.id.etNameFather)

            val child = Child(etNameChild.text.toString(), dateBirth, etNameMother.text.toString(), etNameFather.text.toString(), photo)

            val iChildRestRequest = RetrofitInstance.getRetrofitInstance().create(IChildRestRequest::class.java)

            val call = iChildRestRequest.save(child)

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && data != null){
            val imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data?.data);
            ivChild?.setImageBitmap(imageBitmap)

            val byteArrayOutputStream = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            photo = Base64.getEncoder().encodeToString(byteArray
            )
        }

    }
}
