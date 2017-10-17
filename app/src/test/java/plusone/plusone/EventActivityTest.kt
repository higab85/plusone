package plusone.plusone

import org.junit.Assert.*
import org.junit.Test
import java.text.DateFormat
import java.time.LocalDateTime
import java.util.*

/**
 * Created by tyler on 16/10/17.
 */
class EventActivityTest{

    // Event Class tests
//    @Test
//    // tests start
//    fun startParse(){
//        var event = Event()
//
//        event.setStart("2015-12-11", "19:10")
//        val time = LocalDateTime.parse("2015-12-11T19:10")
//        assertTrue(event.start.equals(time))
//    }

    @Test
    fun correctType(){
        var event = Event()
        event.setType("party")
        assertTrue(event.type == EventType.PARTY)
    }

    @Test
    fun checkType(){
        var event = Event()
        event.setType("meal")
        assertFalse(event.type == EventType.PARTY)
    }

}
