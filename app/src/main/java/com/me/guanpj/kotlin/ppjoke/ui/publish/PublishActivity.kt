package com.me.guanpj.kotlin.ppjoke.ui.publish

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.me.guanpj.kotlin.ppjoke.R
import com.me.guanpj.libnavannotation.ActivityDestination

@ActivityDestination(pageUrl = "main/tabs/publish")
class PublishActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publish)
    }
}
