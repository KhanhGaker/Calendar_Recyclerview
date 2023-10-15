package com.example.calendar_example

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CalendarAdapter : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    private var dayOfMonths = ArrayList<String>()

    lateinit var onItemListener: OnItemListener
    private var selectedStr = "1"

    fun updateData(list: ArrayList<String>){
        dayOfMonths.clear()
        dayOfMonths.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.calendar_cell,parent,false)
        return CalendarViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dayOfMonths.size
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var dayOfMonth: TextView = itemView.findViewById(R.id.cellDayText)
        fun bind(position: Int){
            dayOfMonth.text = dayOfMonths[position]
            if (dayOfMonths[position] == selectedStr){
                dayOfMonth.setTextColor(Color.RED)
            }else{
                dayOfMonth.setTextColor(Color.BLACK)
            }
            dayOfMonth.setOnClickListener {
                onItemListener.onItemClick(position,dayOfMonths[position])
                selectedStr = dayOfMonths[position]
                notifyDataSetChanged()
            }
        }
    }

    interface OnItemListener{
        fun onItemClick(position:Int, dayText:String)
    }
}