package com.fabiose.childhealth.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.fabiose.childhealth.R
import com.fabiose.childhealth.domains.VaccineHistory
import java.text.SimpleDateFormat

class VaccineHistoryAdapter(private val context: Context, private val vaccineHistories: ArrayList<VaccineHistory>): BaseAdapter(){

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater.inflate(R.layout.adapter_vaccine_history, parent, false)

        var tvVaccineName = view.findViewById<TextView>(R.id.tvVaccineName)
        tvVaccineName.text = vaccineHistories.get(position).vaccine.name

        val format = SimpleDateFormat("yyyy-MM-dd")

        var tvDate = view.findViewById<TextView>(R.id.tvDate)
        tvDate.text = format.format(vaccineHistories.get(position).date)

        return view
    }

    override fun getItem(position: Int): Any {
        return vaccineHistories.get(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return vaccineHistories.size
    }

}