package plusone.plusone

import java.sql.Time
import java.time.LocalDateTime
import java.util.*

/**
 * Created by tyler on 13/10/17.
 */

// types of events there can be
enum class EventType{
    SPORTSEVENT,FOOD,PARTY,ENTERTAINMENT,LEARNING,OTHER
}


class Event {
    var eventID:Int = 0
    var name:String = ""
    var description:String = ""
    // TODO: change this to calculatable Date format
    var start:String = ""
    var end:String = ""
    var location:String = ""
    var type:EventType = EventType.OTHER
    var reqPeople:Int = 0
constructor()
constructor(name: String, location:String,start:String,end:String){
    this.name=name
    this.location=location
    this.start=start
    this.end=end


}


    fun setType(eventType: String){
        eventType.toLowerCase()
        when(eventType){
            "sportsevent" -> type = EventType.SPORTSEVENT
            "food" -> type = EventType.FOOD
            "party" -> type = EventType.PARTY
            "entertainment" -> type = EventType.ENTERTAINMENT
            "learning" -> type = EventType.LEARNING
            "other" -> type = EventType.OTHER
        }

    }

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
}