package com.example.lab_4_todo_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    companion object {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var fragmentTransaction = supportFragmentManager.beginTransaction()
        val listOfTasksFragment = ListOfTasksFragment.newInstance()
        fragmentTransaction.replace(R.id.listFragmentContainer, listOfTasksFragment)
            .addToBackStack("listOfTasksFragment").commit()
    }
}