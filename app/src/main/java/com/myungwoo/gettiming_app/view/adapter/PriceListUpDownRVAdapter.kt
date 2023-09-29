package com.myungwoo.gettiming_app.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myungwoo.gettiming_app.R
import com.myungwoo.gettiming_app.dataModel.UpDownDataSet

class PriceListUpDownRVAdapter(val context : Context, val dataSet : List<UpDownDataSet>)
    :RecyclerView.Adapter<PriceListUpDownRVAdapter.ViewHolder>(){

        inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.coin_price_change_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

}