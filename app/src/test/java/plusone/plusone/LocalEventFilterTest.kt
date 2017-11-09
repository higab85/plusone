package plusone.plusone

import android.renderscript.RSInvalidStateException
import org.junit.Assert.*
import org.junit.Test
import plusone.plusone.LocalEventFilter.mergeSort
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.test.*

/**
 * Created by tyler & Javicraft on 28/10/17.
 */
class LocalEventFilterTest{
    var event1:Event = Event("1", "0.0", "2017-12-03T12:30", "2017-12-03T13:30", "PARTY")
    var event2:Event = Event("2", "1.1", "2017-12-03T13:00", "2017-12-03T13:30", "FOOD")
    var event3:Event = Event("3", "2.1", "2017-12-03T10:00", "2017-12-03T14:30", "FOOD")
    var event4:Event = Event("4", "2.1", "2017-12-03T09:00", "2017-12-03T10:30", "FOOD")
    val events:List<Event> = listOf(event1, event2, event3, event4)


    var event12:Event = Event("Event1", "0.0", "2017-12-03T12:30", "2017-12-03T13:30", "PARTY",2.2)
    var event22:Event = Event("Event2", "1.1", "2017-12-03T13:00", "2017-12-03T13:30", "FOOD",0.5)
    var event32:Event = Event("Event3", "2.1", "2017-12-03T10:00", "2017-12-03T14:30", "FOOD",7.5)
    var event42:Event = Event("Event4", "2.1", "2017-12-03T09:00", "2017-12-03T10:30", "FOOD",5.5)
    val events2:List<Event> = listOf(event12,event22,event32,event42)
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
    @Test
    fun orderByDistanceEventListEmpty(){
        val events3:List<Event> = listOf()
        assertTrue(LocalEventFilter.orderEventsByDistance(events3).count()==0)
    }
    @Test
    fun orderByDistanceEventListHas4Events(){
        assertTrue(LocalEventFilter.orderEventsByDistance(events2).count()==4)
    }
    @Test
    fun orderByDistanceEventAlreadyOrdered(){
        val events3:List<Event> = listOf(event22,event12,event42,event32)
        val organisedEvents:List<Event> = LocalEventFilter.orderEventsByDistance(events3)
        assertTrue(organisedEvents.get(0).name == "Event2")
        assertTrue(organisedEvents.get(1).name == "Event1")
        assertTrue(organisedEvents.get(2).name == "Event4")
        assertTrue(organisedEvents.get(3).name == "Event3")
    }
    @Test
    fun orderByDistanceEventSimpleCase(){
        val organisedEvents:List<Event> = LocalEventFilter.orderEventsByDistance(events2)
        assertTrue(organisedEvents.get(0).name == "Event2")
        assertTrue(organisedEvents.get(1).name == "Event1")
        assertTrue(organisedEvents.get(2).name == "Event4")
        assertTrue(organisedEvents.get(3).name == "Event3")
    }
    @Test
    fun orderByDistanceEventOneRepeated(){
        val events3:List<Event> = listOf(event12,event12,event42,event32)
        val organisedEvents:List<Event> = LocalEventFilter.orderEventsByDistance(events3)
        assertTrue(organisedEvents.get(0).name == "Event1")
        assertTrue(organisedEvents.get(1).name == "Event1")
        assertTrue(organisedEvents.get(2).name == "Event4")
        assertTrue(organisedEvents.get(3).name == "Event3")
    }
    @Test
    fun orderByDistanceEvent2EventsSameNameDifferentDistance(){
        var eventSpecial:Event = Event("Event4", "2.1", "2017-12-03T09:00", "2017-12-03T10:30", "FOOD",4.5)
        val events3:List<Event> = listOf(event12,event22,event42,eventSpecial)
        val organisedEvents:List<Event> = LocalEventFilter.orderEventsByDistance(events3)
        assertTrue(organisedEvents.get(0).name == "Event2")
        assertTrue(organisedEvents.get(1).name == "Event1")
        assertTrue(organisedEvents.get(2).name == "Event4")
        assertTrue(organisedEvents.get(3).name == "Event4")
    }
    @Test
    fun orderByDistanceEventSameDistanceDifferentName(){
        var eventSpecial:Event = Event("EventSpecial", "2.1", "2017-12-03T09:00", "2017-12-03T10:30", "FOOD",7.5)
        val events3:List<Event> = listOf(event32,event22,event12,eventSpecial)
        val organisedEvents:List<Event> = LocalEventFilter.orderEventsByDistance(events3)
        /*for (event in organisedEvents){
            print(event.name)
        }*/
        assertTrue(organisedEvents.get(0).name == "Event2")
        assertTrue(organisedEvents.get(1).name == "Event1")
        assertTrue(organisedEvents.get(2).name == "Event3")
        assertTrue(organisedEvents.get(3).name == "EventSpecial")
    }
    @Test
    fun orderByDistanceEventIncorrectEvent(){
        var eventE:Event = Event()
        val events3:List<Event> = listOf(eventE)
        val organisedEvents:List<Event> = LocalEventFilter.orderEventsByDistance(events3)
        assertFalse(organisedEvents.get(0).name == "EventE")
    }
//    @Test
//    @Throws(RSInvalidStateException::class)
//    fun mergeSortNoStringPassed(){
//        assertFailsWith(RSInvalidStateException::class){
//            mergeSort(events2,"")
//        }
//    }
//    @Test//(expected = Exception::class)
//    @Throws(RSInvalidStateException::class)
//    fun mergeSortStringError(){
//
//        assertFailsWith(RSInvalidStateException::class){
//            mergeSort(events2,"hola")
//        }
//
//    }
}