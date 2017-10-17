package plusone.plusone

import java.sql.Time
import java.time.LocalDateTime
import java.util.*

/**
 * Created by tyler on 13/10/17.
 */

// types of events there can be
enum class EventType{
    MEAL, DAYACTIVITY, PARTY, DRINK, CONCERT, OTHER
}


class Event {
    var eventID:String = ""
    var name:String = ""
    var description:String = ""
    // TODO: change this to calculatable Date format
    var start:String = ""
    var end:String = ""
    var location:String = ""
    var type:EventType = EventType.OTHER
    var reqPeople:Int = 0



//    // Sets start from a date and a time
//    fun setStart(date:String, time:String){
//        val parsableString:String = date+"T"+time
//        start = LocalDateTime.parse(parsableString)
//    }
//
//    // Sets start from a date and a time
//    fun setEnd(date:String, time:String){
//        val parsableString:String = date+"T"+time
//        end = LocalDateTime.parse(parsableString)
//    }

    fun setType(eventType: String){
        eventType.toLowerCase()
        when(eventType){
            "meal" -> type = EventType.MEAL
            "day activity" -> type = EventType.DAYACTIVITY
            "party" -> type = EventType.PARTY
            "drink" -> type = EventType.DRINK
            "concert" -> type = EventType.CONCERT
            "other" -> type = EventType.OTHER
        }

    }
}