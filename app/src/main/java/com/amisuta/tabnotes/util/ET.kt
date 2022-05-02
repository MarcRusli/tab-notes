package com.amisuta.tabnotes.util

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.ACTION_UP
import android.view.KeyEvent.KEYCODE_BACK

class ET(context: Context, attrs: AttributeSet?) : androidx.appcompat.widget.AppCompatEditText(context, attrs) {
    //constructor(context: Context) : super(context)

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KEYCODE_BACK && event.action == ACTION_UP){
            Log.d("Marc", "keyboard down event")
            clearFocus()
        }
        return false
    }
}