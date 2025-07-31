package com.example.note.presentation.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.note.R
import com.example.note.presentation.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object{
        lateinit var preferences: PreferenceUtil
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        preferences = PreferenceUtil(applicationContext)
        showFragment()

    }

    private fun showFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout, HomeFragment())
            .commit()
    }

    class PreferenceUtil(context: Context) {
        private val preferences: SharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE)

        fun getId(key: String, defValue: Int): Int {
            val current = preferences.getInt(key, defValue)
            setId(key, current + 1)
            return current
        }

        private fun setId(key: String, defValue: Int){
            preferences.edit().putInt(key, defValue).apply()
        }
    }
}