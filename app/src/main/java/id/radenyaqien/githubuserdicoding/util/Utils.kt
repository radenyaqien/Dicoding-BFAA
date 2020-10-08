package id.radenyaqien.githubuserdicoding.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

//Create SnackBar
fun View.snackbar(message: String) {
    Snackbar.make(
        this,
        message,
        Snackbar.LENGTH_LONG
    ).also { snackbar ->
        snackbar.setAction("Ok") {
            snackbar.dismiss()
        }
    }.show()
}