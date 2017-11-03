package plusone.plusone

import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
    fun displayEventsCorrectOrderTimeFirstToLast(){
        val organisedEvents:List<Event> = LocalEventFilter.orderTimeFirstToLast(events)
        assertTrue(organisedEvents.get(0).name == "4")
        assertTrue(organisedEvents.get(1).name == "3")
        assertTrue(organisedEvents.get(2).name == "1")
        assertTrue(organisedEvents.get(3).name == "2")
    }

    // Tests whether events are in order from last occurring to first
    @Test
    fun displayEventsCorrectOrderTimeLastToFirst(){
        val organisedEvents:List<Event> = LocalEventFilter.orderTimeLastToFirst(events)
        assertTrue(organisedEvents.get(0).name == "2")
        assertTrue(organisedEvents.get(1).name == "1")
        assertTrue(organisedEvents.get(2).name == "3")
        assertTrue(organisedEvents.get(3).name == "4")
    }

    // Tests whether only events after a certain time are filtered
    @Test
    fun displayEventsAfterSpecificTime(){
        val dateTime1 = LocalDateTime.parse( "2017-12-03T09:30", DateTimeFormatter.ISO_DATE_TIME)
        val dateTime2 = LocalDateTime.parse( "2017-12-03T09:00", DateTimeFormatter.ISO_DATE_TIME)
        val dateTime3 = LocalDateTime.parse( "2017-12-03T12:15", DateTimeFormatter.ISO_DATE_TIME)

        val organisedEvents1:List<Event> = LocalEventFilter.filterByStartTimeAfter(events, dateTime1)
        val organisedEvents2:List<Event> = LocalEventFilter.filterByStartTimeAfter(events, dateTime2)
        val organisedEvents3:List<Event> = LocalEventFilter.filterByStartTimeAfter(events, dateTime3)

        assertTrue(organisedEvents1.count() == 3)
        assertTrue(organisedEvents2.count() == 4)
        assertTrue(organisedEvents3.count() == 2)
    }

    // Tests whether only events before a certain time are filtered
    @Test
    fun displayEventsBeforeSpecificTime(){
        val dateTime1 = LocalDateTime.parse( "2017-12-03T09:30", DateTimeFormatter.ISO_DATE_TIME)
        val dateTime2 = LocalDateTime.parse( "2017-12-03T08:00", DateTimeFormatter.ISO_DATE_TIME)
        val dateTime3 = LocalDateTime.parse( "2017-12-03T12:30", DateTimeFormatter.ISO_DATE_TIME)

        val organisedEvents1:List<Event> = LocalEventFilter.filterByStartTimeBefore(events, dateTime1)
        val organisedEvents2:List<Event> = LocalEventFilter.filterByStartTimeBefore(events, dateTime2)
        val organisedEvents3:List<Event> = LocalEventFilter.filterByStartTimeBefore(events, dateTime3)

        assertTrue(organisedEvents1.count() == 1)
        assertTrue(organisedEvents2.count() == 0)
        assertTrue(organisedEvents3.count() == 3)
    }

}