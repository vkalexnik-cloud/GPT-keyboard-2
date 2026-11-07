package com.example.chatgptkeyboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(28, 28, 28, 28)
        }
        layout.addView(TextView(this).apply {
            text = "ChatGPT Keyboard 💙\\n\\nЧтобы включить клавиатуру, открой настройки методов ввода Android."
            textSize = 20f
            gravity = Gravity.CENTER
        })
        layout.addView(Button(this).apply {
            text = "Открыть настройки клавиатур"
            setOnClickListener { startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)) }
        })
        setContentView(layout)
    }
}
