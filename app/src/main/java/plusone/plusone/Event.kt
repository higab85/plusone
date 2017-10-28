package plusone.plusone

import java.io.FileDescriptor
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
    var type:String = ""
    var reqPeople:Int = 0

    constructor()
    constructor(name: String, location:String,start:String,end:String, type:String){
        this.name=name
        this.location=location
        this.start=start
        this.end=end
        this.type=type
    }
    constructor(name: String,description:String, location:String,start:String,end:String, type:String,reqPeople:Int){
        this.name=name
        this.description=description
        this.location=location
        this.start=start
        this.end=end
        this.type=type
        this.reqPeople=reqPeople
    }


}