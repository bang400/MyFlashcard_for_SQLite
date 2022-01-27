package com.yamato.myflashcard_for_sqlite.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult

class WordConfirmDeleteDialogFragment:DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireActivity()).apply {
            setMessage("単語のデータがすべて削除されます。\nよろしいですか？")
            setPositiveButton(android.R.string.ok,listener)
            setNegativeButton(android.R.string.cancel,listener)
        }.create()
    }

    private val listener = DialogInterface.OnClickListener { _, which ->
        setFragmentResult("confirm", bundleOf("result" to which))
    }
}