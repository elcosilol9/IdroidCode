package com.ardev.idroid.common

import android.app.Notification
import android.widget.Toast
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ToastTest {

    @Test
    fun showCorrectToast() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        val toast = Toast.makeText(context, "toast message", Toast.LENGTH_SHORT).also {
            it.show()
        }

        assertEquals(Toast.LENGTH_SHORT, toast.duration)
    }
}
