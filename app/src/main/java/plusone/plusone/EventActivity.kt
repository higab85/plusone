package plusone.plusone

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.activity_login.*

class EventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        createEventButton.setOnClickListener{
            var event:Event? = null
            event!!.name = eventName.text.toString()
//            event!!.setStart(date, start)
            event!!.location = location.text.toString()

            finish()
        }

    }
}
