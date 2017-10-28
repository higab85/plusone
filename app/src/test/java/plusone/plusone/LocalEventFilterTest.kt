package plusone.plusone

import org.junit.Assert.*
import org.junit.Test

/**
 * Created by tyler on 28/10/17.
 */
class LocalEventFilterTest{
    var event1:Event = Event("1", "0,0", "2017-12-03T12:30", "2017-12-03T13:30", "PARTY")
    var event2:Event = Event("2", "1,1", "2017-12-03T13:00", "2017-12-03T13:30", "FOOD")
    var event3:Event = Event("3", "2,1", "2017-12-03T10:00", "2017-12-03T14:30", "FOOD")
    var event4:Event = Event("4", "2,1", "2017-12-03T09:00", "2017-12-03T10:30", "FOOD")
    val events:List<Event> = listOf(event1, event2, event3, event4)

    // Tests whether events are in order from first occurring to last
    @Test
    fun displayEventsCorrectOrderTime(){
        val organisedEvents:List<Event> = LocalEventFilter.orderTimeFirstToLast(events)
        assertTrue(organisedEvents.get(0).name == "4")
        assertTrue(organisedEvents.get(1).name == "3")
        assertTrue(organisedEvents.get(2).name == "1")
        assertTrue(organisedEvents.get(3).name == "2")
    }

    // Tests whether events are in order of distance

}