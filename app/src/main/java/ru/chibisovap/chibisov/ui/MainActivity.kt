package ru.chibisovap.chibisov.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.chibisovap.chibisov.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, GifFragment())
            .commit()
    }
}