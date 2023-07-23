package com.ardev.idroid.common

import android.text.Editable
import android.text.TextWatcher

class SingleTextWatcher : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    override fun afterTextChanged(editable: Editable?) {}
}