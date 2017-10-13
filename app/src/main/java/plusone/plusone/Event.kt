package plusone.plusone

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
    var title:String = ""
    var description:String = ""
    var start:java.sql.Date = java.sql.Date(0)
    var duration:Long = 0
    var location:String = ""
    var type:EventType = EventType.OTHER
    var maxPpl:Int = 0
    var minPpl:Int = 0

    // will return
    fun getEndDate():java.sql.Date{
        var durationMilliseconds:Long = duration * 60 * 1000
        return java.sql.Date(start.getTime() + durationMilliseconds)
    }
}