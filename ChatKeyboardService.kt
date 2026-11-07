package com.example.chatgptkeyboard

import android.inputmethodservice.InputMethodService
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputConnection
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class ChatKeyboardService : InputMethodService() {
    private val layouts = listOf(
        listOf("й ц у к е н г ш щ з х", "ф ы в а п р о л д ж э", "я ч с м и т ь б ю"),
        listOf("й ц у к е н г ш щ з х", "ф і в а п р о л д ж є", "я ч с м и т ь б ю ґ"),
        listOf("q w e r t y u i o p", "a s d f g h j k l", "z x c v b n m")
    )
    private val languages = listOf("РУС", "УКР", "ENG")
    private var languageIndex = 0
    private var symbols = false

    override fun onCreateInputView(): View = buildKeyboard()

    private fun buildKeyboard(): View {
        val outer = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(6, 8, 6, 8)
            setBackgroundColor(Color.rgb(15, 22, 43))
        }

        val toolbar = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        toolbar.addView(label("🤖 ChatGPT", 0xFF8BD8FF.toInt(), 16f), weight())
        toolbar.addView(actionButton(languages[languageIndex]) {
            languageIndex = (languageIndex + 1) % layouts.size
            refresh()
        })
        toolbar.addView(actionButton("🌐") { languageIndex = (languageIndex + 1) % layouts.size; refresh() })
        toolbar.addView(actionButton("⚙") { requestShowSelf(android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT); })
        outer.addView(toolbar)

        val emojis = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        listOf("😀","😂","🥹","😍","❤️","👍","🙏","🐱").forEach { emoji ->
            emojis.addView(actionButton(emoji) { currentInputConnection?.commitText(emoji, 1) }, weight())
        }
        outer.addView(emojis)

        layouts[languageIndex].forEach { rowText ->
            val row = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER
            }
            rowText.split(" ").forEach { key ->
                row.addView(keyButton(key) { commit(key) }, weight())
            }
            outer.addView(row)
        }

        val bottom = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        bottom.addView(actionButton("?123") { symbols = !symbols; refresh() }, weight(1.3f))
        bottom.addView(actionButton("🌍") { languageIndex = (languageIndex + 1) % layouts.size; refresh() }, weight())
        bottom.addView(actionButton("English / Русский") { currentInputConnection?.commitText(" ", 1) }, weight(2.4f))
        bottom.addView(actionButton("⌫") { currentInputConnection?.deleteSurroundingText(1, 0) }, weight(1.2f))
        bottom.addView(actionButton("↵") { currentInputConnection?.sendKeyEvent(android.view.KeyEvent(android.view.KeyEvent.ACTION_DOWN, android.view.KeyEvent.KEYCODE_ENTER)); currentInputConnection?.sendKeyEvent(android.view.KeyEvent(android.view.KeyEvent.ACTION_UP, android.view.KeyEvent.KEYCODE_ENTER)) }, weight(1.2f))
        outer.addView(bottom)
        val note = TextView(this).apply {
            text = "Переводчик и ИИ-помощник будут подключены следующим этапом"
            textSize = 10f
            gravity = Gravity.CENTER
            setTextColor(0xFF9AAAC8.toInt())
            setPadding(2, 6, 2, 2)
        }
        outer.addView(note)
        return outer
    }

    private fun refresh() {
        setInputView(buildKeyboard())
    }

    private fun commit(value: String) {
        currentInputConnection?.commitText(value, 1)
    }

    private fun weight(w: Float = 1f) = LinearLayout.LayoutParams(0, 48, w).apply {
        setMargins(2, 3, 2, 3)
    }

    private fun label(text: String, color: Int, size: Float) = TextView(this).apply {
        this.text = text
        textSize = size
        setTextColor(color)
        typeface = Typeface.DEFAULT_BOLD
        gravity = Gravity.CENTER_VERTICAL
    }

    private fun actionButton(text: String, action: () -> Unit) = Button(this).apply {
        this.text = text
        textSize = 12f
        isAllCaps = false
        setTextColor(Color.WHITE)
        setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFF26395F.toInt()))
        setOnClickListener { action() }
    }

    private fun keyButton(text: String, action: () -> Unit) = Button(this).apply {
        this.text = text
        textSize = 18f
        isAllCaps = false
        setTextColor(Color.WHITE)
        setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFF34496F.toInt()))
        setOnClickListener { action() }
    }
}
