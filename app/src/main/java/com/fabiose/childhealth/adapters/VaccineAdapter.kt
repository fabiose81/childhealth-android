package com.fabiose.childhealth.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.fabiose.childhealth.R
import com.fabiose.childhealth.domains.Vaccine

class VaccineAdapter(private val context: Context, private val vaccines: ArrayList<Vaccine>): BaseAdapter(){

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater.inflate(R.layout.adapter_vaccine, parent, false)

        val tvNameVaccine = view.findViewById<TextView>(R.id.tvNameVaccine)
        tvNameVaccine.text = vaccines.get(position).name

        return view
    }

    override fun getItem(position: Int): Any {
        return vaccines.get(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return vaccines.size
    }

}