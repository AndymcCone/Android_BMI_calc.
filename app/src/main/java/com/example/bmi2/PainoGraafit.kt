package com.example.bmi2

import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson


class PainoGraafit : AppCompatActivity() {

    var barChart: BarChart? = null
    var barData: BarData? = null
    var barDataSet: BarDataSet? = null
    var barEntriesArrayList = ArrayList<BarEntry>()
    val paino_tallennus = "paino"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paino_graafit)

        barChart = findViewById(R.id.idBarChart);
        getBarEntries();

        barDataSet = BarDataSet(barEntriesArrayList, "BMI historia");
        barData = BarData(barDataSet);
        barChart?.setData(barData);
        barDataSet?.setColors(Color.rgb(134,99,99));
        barDataSet?.setValueTextColor(Color.BLACK);
        barDataSet?.setValueTextSize(16f);
        barChart?.getDescription()?.setEnabled(false);
    }

        fun getBarEntries() {
            val sharedpreferences = getSharedPreferences(paino_tallennus, MODE_PRIVATE)
            barEntriesArrayList = ArrayList<BarEntry>();
            var gson = Gson();
            var values: String? = sharedpreferences?.getString(paino_tallennus, "")
            if (values == null || values?.isEmpty()) {
                return
            }
            var editor: SharedPreferences.Editor? = sharedpreferences?.edit()
            val itemType = object : TypeToken<ArrayList<String>>() {}.type
            var bmi = arrayListOf<String>()
            bmi = gson.fromJson<ArrayList<String>>(values, itemType)
            var index = 1f
            for (item in bmi) {
                barEntriesArrayList.add(BarEntry(index, item.toFloat()));
                index++
                if (barEntriesArrayList.size > 10){
                    barEntriesArrayList.removeAt(0)
                }
            }
        }




    }
