package com.ardev.idroid.ui.settings.preference

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceManager
import androidx.preference.ListPreferenceDialogFragmentCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import androidx.preference.PreferenceFragmentCompat

class MaterialListPreferenceDialogFragment : ListPreferenceDialogFragmentCompat() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mWhichButtonClicked = DialogInterface.BUTTON_NEGATIVE
        val builder = MaterialAlertDialogBuilder(requireContext())
            .setTitle(preference.dialogTitle)
            .setIcon(preference.dialogIcon)
            .setPositiveButton(preference.positiveButtonText, this)
            .setNegativeButton(preference.negativeButtonText, this)
        val contentView = onCreateDialogView(requireContext())
        if (contentView != null) {
            onBindDialogView(contentView)
            builder.setView(contentView)
        } else {
            builder.setMessage(preference.dialogMessage)
        }
        onPrepareDialogBuilder(builder)

        val dialog = builder.create()
        return dialog
    }

   
    private var mWhichButtonClicked = 0

    override fun onClick(dialog: DialogInterface, which: Int) {
        mWhichButtonClicked = which
    }

    override fun onDismiss(dialog: DialogInterface) {
        onDialogClosedWasCalledFromOnDismiss = true
        super.onDismiss(dialog)
    }

    private var onDialogClosedWasCalledFromOnDismiss = false

    override fun onDialogClosed(positiveResult: Boolean) {
        if (onDialogClosedWasCalledFromOnDismiss) {
            onDialogClosedWasCalledFromOnDismiss = false
           
            super.onDialogClosed(mWhichButtonClicked == DialogInterface.BUTTON_POSITIVE)
        } else {
            super.onDialogClosed(positiveResult)
        }
    }
}

fun PreferenceFragmentCompat.showListPreferenceDialog(preference: ListPreference) {
    val dialogFragment = MaterialListPreferenceDialogFragment().apply {
        arguments = Bundle(1).apply {
            putString("key", preference.key)
        }
    }
    dialogFragment.setTargetFragment(this, 0)
    dialogFragment.show(parentFragmentManager, "androidx.preference.PreferenceFragment.DIALOG")
}
