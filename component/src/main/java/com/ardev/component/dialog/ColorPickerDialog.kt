package com.ardev.component.dialog

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.ardev.component.R
import com.ardev.component.view.colorpicker.ColorPicker
import com.ardev.component.view.colorpicker.listeners.ColorSelectionListener
import com.ardev.idroid.ext
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ColorPickerDialog : DialogFragment() {

    private lateinit var dialog: AlertDialog
    private lateinit var mColor: String
    private var mListener: DialogListener? = null
    fun setOnColorSelected(listener: DialogListener) {
        mListener = listener
    }

    private lateinit var preview: MaterialCardView
    private lateinit var colorPicker: ColorPicker
    private var colorSelected: Int = 0

    fun setCurrentColor(color: String) {
        mColor = getColor(color)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(activity).inflate(R.layout.colorpicker_dialog, (view as ViewGroup), false)
        initView(view)

        val builder = MaterialAlertDialogBuilder(activity!!)
        builder.setTitle(R.string.dialog_title_select_color)
        builder.setView(view)
        builder.setNegativeButton(R.string.dialog_cancel, null)
        builder.setPositiveButton(
            R.string.dialog_choose
        ) { dlg, i ->
            mListener?.onColorSelected(colorSelected)
            dismiss()
        }

        dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        view.post {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
        }

        return dialog
    }

    private fun initView(view: View) {
        preview = view.findViewById(R.id.preview)
        colorPicker = view.findViewById(R.id.colorPicker)
        colorPicker.color = Color.parseColor(mColor)
        preview.setCardBackgroundColor(Color.parseColor(mColor))
        colorPicker.setColorSelectionListener(object : ColorSelectionListener() {
            override fun onColorSelected(color: Int) {
                preview.setCardBackgroundColor(color)
                colorSelected = color
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true
            }
        })
        colorSelected = Color.parseColor(mColor)
    }

    interface DialogListener {
        fun onColorSelected(color: Int)
    }
}