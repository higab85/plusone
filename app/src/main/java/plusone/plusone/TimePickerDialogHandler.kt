package plusone.plusone

import android.app.Dialog
import android.app.DialogFragment
import android.app.TimePickerDialog
import android.net.Uri

import android.os.Bundle
import android.text.format.DateFormat
import java.util.*

/**
 * Created by tyler on 9/11/17.
 */
class TimePickerDialogHandler : DialogFragment(){

    var timeListener:TimePickerDialog.OnTimeSetListener? = null

    interface OnTimeSetListener {
        fun android.app.TimePickerDialog.OnTimeSetListener()
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var calendar: Calendar = Calendar.getInstance()
        var hour: Int = calendar.get(Calendar.HOUR_OF_DAY)
        var min: Int = calendar.get(Calendar.MINUTE)
        var dialog: TimePickerDialog
        var ts:TimeSetting = TimeSetting(activity)
        dialog = TimePickerDialog(activity,ts,hour,min, DateFormat.is24HourFormat(activity))
        return dialog
    }
}

