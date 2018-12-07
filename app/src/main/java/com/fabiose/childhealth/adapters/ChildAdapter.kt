package com.fabiose.childhealth.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import com.fabiose.childhealth.R
import com.fabiose.childhealth.activities.child.ChildDetailsActivity
import com.fabiose.childhealth.domains.Child
import com.fabiose.childhealth.requests.IChildRestRequest
import com.fabiose.childhealth.requests.RetrofitInstance
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.util.ArrayList


class ChildAdapter(private val context: Context, private val children: ArrayList<Child>): BaseAdapter(){

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater.inflate(R.layout.adapter_child, parent, false)

        val tvName = view.findViewById<TextView>(R.id.tvName)
        tvName.text = children.get(position).name

        tvName.setOnClickListener {
            val intent = Intent(context, ChildDetailsActivity::class.java)
            intent.putExtra("child", children.get(position))
            context.startActivity(intent)
        }

        val ibDelete = view.findViewById<ImageButton>(R.id.ibDelete)
        ibDelete.setOnClickListener {
            val iChildRestRequest = RetrofitInstance.getRetrofitInstance().create(IChildRestRequest::class.java)

            val call = iChildRestRequest.delete(children.get(position))

            call.enqueue(object: Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>,
                                        response: Response<ResponseBody>
                ) {
                    if(response.code() == 200) {
                        children.removeAt(position)
                        notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>,
                                       t: Throwable?) {
                }
            })
        }

        return view
    }

    override fun getItem(position: Int): Any {
        return children.get(position);
    }

    override fun getItemId(position: Int): Long {
       return 0
    }

    override fun getCount(): Int {
        return children.size;
    }
}