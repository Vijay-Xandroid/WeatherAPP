package com.vita.weatherforecast.utils.extension

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Build
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.Date

fun <T> runOnMain(block: () -> T) = GlobalScope.launch(Dispatchers.Main) {
    block()
}

fun logTrace(text: String) =  Log.d("WeatherForecast", text)

fun Fragment.showSnack(message: String, duration: Int = Snackbar.LENGTH_SHORT) =
    Snackbar.make(view!!, message, duration).show()

//fun Context.showSnack(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
//    runOnMain {
//        Snackbar.make(this, message, duration).show()
//    }
//}

fun Context.shortToast(msg: String) {
    runOnMain {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}

@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.O)
fun getDateTime(s: Long): DayOfWeek? {
    return try {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val netDate = Date(s * 1000)
        val formattedDate = sdf.format(netDate)

        LocalDate.of(
            formattedDate.substringAfterLast("/").toInt(),
            formattedDate.substringAfter("/").take(2).toInt(),
            formattedDate.substringBefore("/").toInt()
        )
            .dayOfWeek
    } catch (e: Exception) {
        e.printStackTrace()
        DayOfWeek.MONDAY
    }
}

fun View.hideKeyboard() {
    val inputMethodManager: InputMethodManager =
        this.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}