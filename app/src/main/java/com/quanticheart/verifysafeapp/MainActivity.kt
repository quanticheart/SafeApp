package com.quanticheart.verifysafeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.quanticheart.safeapp.alertSafeApp

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        alertSafeApp()
    }
}
