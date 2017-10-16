package plusone.plusone

import java.sql.Time
import java.util.*

/**
 * Created by tyler on 13/10/17.
 */

// types of events there can be
enum class EventType{
    MEAL, DAYACTIVITY, NIGHTLIFE, DRINK, CONCERT, OTHER
}


class Event{
    var eventID:String = ""
    var name:String = ""
    var description:String = ""
    var start:java.sql.Date = java.sql.Date(0)
    var end:java.sql.Date = java.sql.Date(0)
    var location:String = ""
    var type:EventType = EventType.OTHER
    var maxPpl:Int = 0
    var minPpl:Int = 0

    fun setStart(date:Date, time:Time){

    }
}