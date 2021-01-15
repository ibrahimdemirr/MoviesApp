package com.ibrahimdemir.moviesapp.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.clear() {
    text.clear()
}

fun EditText.listenChanges(
    afterTextChangedListener: ((s: Editable?) -> Unit)? = null,
    beforeTextChangedListener: ((s: CharSequence?, start: Int, count: Int, after: Int) -> Unit)? = null,
    onTextChangedListener: ((s: CharSequence?, start: Int, before: Int, count: Int) -> Unit)? = null
) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChangedListener?.invoke(s)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            beforeTextChangedListener?.invoke(s, start, count, after)
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChangedListener?.invoke(s, start, before, count)
        }
    })
}