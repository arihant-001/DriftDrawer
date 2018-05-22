package com.sdsmdg.aridj.popoutnavigationdrawer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sdsmdg.aridj.lib.PopOutNavBuilder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // for demo
        PopOutNavBuilder(this).build()
    }
}
