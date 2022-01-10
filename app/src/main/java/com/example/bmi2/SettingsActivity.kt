package com.example.bmi2

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)


        //val pituustext = findViewById<EditText>(R.id.editTextNumberSettings).text.toString()

        findViewById<Button>(R.id.buttonSettings).setOnClickListener {
          //  findViewById<TextView>(R.id.textViewMain).text = pituustext
            finish()

        }

        val pituus = preferences.getString("pituus", "aseta pituus")
        findViewById<EditText>(R.id.editTextNumberSettings).setText(pituus)

        findViewById<EditText>(R.id.editTextNumberSettings).doAfterTextChanged {
            var text = it
            with (preferences.edit()){
                putString("pituus", text.toString())
                apply()
            }
        }

    }

    override fun finish(){
        setResult(findViewById<EditText>(R.id.editTextNumberSettings).text.toString().toInt())
        super.finish()
    }


}