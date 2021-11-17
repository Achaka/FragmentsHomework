package com.achaka.fragmentshomework

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.add

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val supportFragmentManager = supportFragmentManager
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .add<ContactsListFragment>(R.id.contacts_fragment_container_view)
            .commit()
    }
}