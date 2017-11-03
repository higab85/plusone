package plusone.plusone

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class AllInfoEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_info_event)
        val allInfoEventName =this.intent.getStringExtra("allInfoEventName")
        val allInfoEventNameTextView: TextView = findViewById(R.id.allInfoEventName) as TextView
        allInfoEventNameTextView.text = allInfoEventName

        val allInfoLocation =this.intent.getStringExtra("allInfoLocation")
        val allInfoLocationTextView: TextView = findViewById(R.id.allInfoLocation) as TextView
        allInfoLocationTextView.text = allInfoLocation

        val allInfoStart =this.intent.getStringExtra("allInfoStart")
        val allInfoStartTextView: TextView = findViewById(R.id.allInfoStart) as TextView
        allInfoStartTextView.text = allInfoStart

        val allInfoEnd =this.intent.getStringExtra("allInfoEnd")
        val allInfoEndTextView: TextView = findViewById(R.id.allInfoEnd) as TextView
        allInfoEndTextView.text = allInfoEnd

        val allInfoDescription =this.intent.getStringExtra("allInfoDescription")
        val allInfoDescriptionTextView: TextView = findViewById(R.id.allInfoDescription) as TextView
        allInfoDescriptionTextView.text = allInfoDescription

        val allInfoEventType =this.intent.getStringExtra("allInfoEventType")
        val allInfoEventTypeTextView: TextView = findViewById(R.id.allInfoEventType) as TextView
        allInfoEventTypeTextView.text = allInfoEventType

        val allInfoPeopleNeeded =this.intent.getStringExtra("allInfoPeopleNeeded")
        val allInfoPeopleNeededTextView: TextView = findViewById(R.id.allInfoPeopleNeeded) as TextView
        allInfoPeopleNeededTextView.text = allInfoPeopleNeeded
    }
}
