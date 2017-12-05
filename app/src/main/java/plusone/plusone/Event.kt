package plusone.plusone

import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

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
    var latitude = ""
    var longitude = ""
    var type:String = ""
    var reqPeople:Int = 0
    var distance:Double = 0.0
    var subscription:Boolean? = null

    constructor()
    constructor(name: String, location:String,start:String,end:String, type:String){

        this.name=name
        this.location=location
        this.start=start
        this.end=end
        this.type=type
    }
    constructor(eventID:Int,name: String,description:String, location:String,start:String,end:String, type:String,reqPeople:Int){
        this.eventID=eventID
        this.name=name
        this.description=description
        this.location=location
        this.start=start
        this.end=end
        this.type=type
        this.reqPeople=reqPeople
    }
    constructor(name: String, location:String,start:String,end:String, type:String,distance:Double){
        this.name=name
        this.location=location
        this.start=start
        this.end=end
        this.type=type
        this.distance=distance
    }
    fun getstartDateTime():LocalDateTime{
        return LocalDateTime.parse(start, DateTimeFormatter.ISO_DATE_TIME)
    }

    fun getstartTime():LocalTime{
        return getstartDateTime().toLocalTime()
    }




}