package com.egan.quicktile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.zxing.activity.CaptureActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        text_view.setOnClickListener { startActivity(Intent(this, CaptureActivity::class.java)) }

    }
}