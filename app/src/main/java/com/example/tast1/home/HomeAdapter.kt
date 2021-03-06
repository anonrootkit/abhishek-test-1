package com.example.tast1.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.tast1.R
import com.example.tast1.model.FacilityItem

class HomeAdapter(
    context: Context,
    values : List<Any>,
    private val onItemSelected : (String) -> Unit
    ) : ArrayAdapter<Any>(context, 0, values) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val data : Any = getItem(position)!!
        var view : View? = null

        when(data) {
            is String -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.facility_header, parent, false)
                view.findViewById<TextView>(R.id.header_label).text = data
            }

            is FacilityItem -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.facility_item, parent, false)

                if (data.isSelected){
                    view.isActivated = true
                    view.setBackgroundResource(R.drawable.ic_launcher_background)
                }else{
                    view.isActivated = false
                    view.setBackgroundResource(0)
                }

                view.setOnClickListener {
                    onItemSelected(data.itemId)
                }

                view.findViewById<TextView>(R.id.facility_name).text = data.name
            }
        }

        return view!!
    }
}