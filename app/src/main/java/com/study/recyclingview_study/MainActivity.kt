package com.study.recyclingview_study

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.study.recyclingview_study.contacts.ContactsActivity
import com.study.recyclingview_study.counries.CountriesActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val countriesBtn : Button = findViewById(R.id.counriesBtn)
        val contactsBtn : Button = findViewById(R.id.contactsBtn)


        countriesBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, CountriesActivity::class.java)
            startActivity(intent)
        }

        contactsBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, ContactsActivity::class.java)
            startActivity(intent)
        }

    }
}