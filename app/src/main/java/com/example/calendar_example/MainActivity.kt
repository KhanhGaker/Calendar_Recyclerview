package com.example.calendar_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private lateinit var monthYearText:TextView
    private lateinit var calendarRecyclerView: RecyclerView
    private var selectedDate: LocalDate? = null

    private fun initWidgets() {
        monthYearText = findViewById(R.id.monthYearTV)
        calendarRecyclerView = findViewById(R.id.calendarRecyclerview)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initWidgets()
        selectedDate = LocalDate.now()
        setMonthView()
    }

    private fun setMonthView() {
        monthYearText.text = monthYearFromDate(date = selectedDate!!)

        val daysInMonth = daysInMonthArray(selectedDate!!)
        val adapter = CalendarAdapter()
        adapter.onItemListener = object : CalendarAdapter.OnItemListener{
            override fun onItemClick(position: Int, dayText: String) {
                if (dayText != ""){
                    val ms = "Selected Date $dayText ${monthYearFromDate(selectedDate!!)}"
                    Toast.makeText(this@MainActivity,ms,Toast.LENGTH_SHORT).show()
                }
            }

        }
        calendarRecyclerView.layoutManager = GridLayoutManager(this,7)
        calendarRecyclerView.adapter = adapter
        adapter.updateData(daysInMonth)
    }

    private fun monthYearFromDate(date: LocalDate):String{
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
    }

    private fun daysInMonthArray(date: LocalDate): ArrayList<String>{
        val daysInMonthArray = ArrayList<String>()

        val yearMonth = YearMonth.from(date)
        val daysInMonth = yearMonth.lengthOfMonth()

        val firstOfMonth = selectedDate!!.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value

        for (i in 1..42){
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek){
                daysInMonthArray.add("")
            }else{
                daysInMonthArray.add("${i - dayOfWeek}")
            }
        }
        return daysInMonthArray
    }

    fun nextMonthAction(view: View) {
        selectedDate = selectedDate!!.plusMonths(1)
        setMonthView()
    }
    fun previousMonthAction(view: View) {
        selectedDate = selectedDate!!.minusMonths(1)
        setMonthView()
    }
}