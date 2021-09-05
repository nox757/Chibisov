package ru.chibisovap.chibisov.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.chibisovap.chibisov.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, GifFragment())
            .commit()
    }
}