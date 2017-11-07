package plusone.plusone

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_all_info_event.*

class AllInfoEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_info_event)
        val allInfoEventName =this.intent.getStringExtra("allInfoEventName")
        val allInfoEventNameTextView: TextView = findViewById(R.id.allInfoEventName) as TextView
        allInfoEventNameTextView.text = allInfoEventName



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

        val allInfoLatitude =this.intent.getStringExtra("allInfoLatitude")
        val allInfoLongitude =this.intent.getStringExtra("allInfoLongitude")

        SeeMapButton.setOnClickListener{
            val intent = Intent(this, MapsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            intent.putExtra("latitude",allInfoLatitude)
            intent.putExtra("longitude",allInfoLongitude)
            startActivity(intent)
        }
    }
}
