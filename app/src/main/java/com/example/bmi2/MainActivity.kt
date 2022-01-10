package com.example.bmi2

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.example.bmi2.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.math.pow


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    val bmi_tallennus = "paino"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)


        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val pituus = preferences.getString("pituus", "TYHJÄ").toString()
        val paino = findViewById<TextView>(R.id.editTextNumberWeight)
        val display = findViewById<TextView>(R.id.textViewOutput)


        if (pituus != "TYHJÄ")
        {
            findViewById<TextView>(R.id.textViewMain).text = "Pituus: " + pituus + "cm"
        }
        else
        {
            findViewById<TextView>(R.id.textViewMain).text = "Aseta pituus settings valikossa"
        }

        findViewById<Button>(R.id.buttonMain).setOnClickListener{
            if (pituus != "TYHJÄ"){
            var height = preferences.getString("pituus", "").toString()
            if (paino.text.toString() == "") {
                display.text = "Syötä paino."
            }
            else {
                val bmi = String.format(
                    "%.1f", paino.text.toString().toFloat() / height?.toFloat().pow(2) * 10000
                )
                display.text = "Painoindeksi: " + bmi
                putToSharedPreferences(bmi.toFloat())
            }
            }
        }

        findViewById<Button>(R.id.button_painohistoria).setOnClickListener {
            val intent = Intent(this@MainActivity, PainoGraafit::class.java)
            startActivity(intent)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings ->
            {
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivityForResult(intent, 1)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun putToSharedPreferences(value : Float){
        val sharedpreferences = getSharedPreferences(bmi_tallennus,MODE_PRIVATE)

        var values : String? = sharedpreferences?.getString(bmi_tallennus,"")
        var editor : SharedPreferences.Editor? = sharedpreferences?.edit()

        var l = arrayListOf<String>()
        var gson =  Gson();

        if(values == null || values?.isEmpty()){
            values = value.toString()
            l.add(values)
        }

        else{
            val itemType = object : TypeToken<ArrayList<String>>() {}.type
            l = gson.fromJson<ArrayList<String>>(values, itemType)
            l.add(value.toString())
        }
        editor?.putString(bmi_tallennus,gson.toJson(l))
        //editor?.putString(paino_tallennus,"")
        editor?.commit()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        findViewById<TextView>(R.id.textViewMain).text = "Pituus: "+resultCode.toString()+"cm"
        //findViewById<TextView>(R.id.textViewMain).text = "Pituus: " + preferences.getString("pituus", "")
        //val pituus = resultCode.toString()
        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val pituus = preferences.getString("pituus", "TYHJÄ").toString()
        val paino = findViewById<TextView>(R.id.editTextNumberWeight)
        val display = findViewById<TextView>(R.id.textViewOutput)
        findViewById<Button>(R.id.buttonMain).setOnClickListener{
            if (paino.text.toString() == "") {
                display.text = "Syötä paino."
            }
            else {
                val bmi = String.format(
                    "%.1f",paino.text.toString().toFloat() / pituus?.toFloat().pow(2) * 10000)
                display.text = "Painoindeksi: " + bmi
                putToSharedPreferences(bmi.toFloat())
            }
        }

    }







}