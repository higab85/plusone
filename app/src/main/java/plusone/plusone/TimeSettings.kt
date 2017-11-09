package plusone.plusone

import android.app.TimePickerDialog
import android.content.Context
import android.widget.TimePicker
import android.widget.Toast

/**
 * Created by tyler on 9/11/17.
 */
class TimeSetting: TimePickerDialog.OnTimeSetListener{
    var con:Context

    constructor(con: Context){
        this.con = con
    }
    override fun onTimeSet(view: TimePicker?, hourOfDay:Int, minute:Int){
        Toast.makeText(con, "time: " + hourOfDay + ":" + minute, Toast.LENGTH_LONG).show()

    }
}
