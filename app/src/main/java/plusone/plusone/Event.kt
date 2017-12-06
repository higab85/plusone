package plusone.plusone

import java.io.Serializable
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * Created by tyler on 13/10/17.
 */


class Event: Serializable {
    var id:String = ""
    var name:String = ""
    var description:String = ""
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
    constructor(id:String,name: String,description:String, location:String,start:String,end:String, type:String,reqPeople:Int){
        this.id=id
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

    fun getStartTime():LocalTime{
        return getstartDateTime().toLocalTime()
    }


}