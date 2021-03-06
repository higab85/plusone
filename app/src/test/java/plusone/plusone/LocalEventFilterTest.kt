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
    //test para cuando la lista está vacía y te ordena por distancia
    @Test
    fun orderByDistanceEventListEmpty(){
        val events3:List<Event> = listOf()
        assertTrue(LocalEventFilter.orderEventsByDistance(events3).count()==0)
    }
    //test para cuando la lista tiene 4 eventos y te ordena por distancia
    @Test
    fun orderByDistanceEventListHas4Events(){
        assertTrue(LocalEventFilter.orderEventsByDistance(events2).count()==4)
    }
    //test que ordena por distancia y están ya los eventos ordenados
    @Test
    fun orderByDistanceEventAlreadyOrdered(){
        val events3:List<Event> = listOf(event22,event12,event42,event32)
        val organisedEvents:List<Event> = LocalEventFilter.orderEventsByDistance(events3)
        assertTrue(organisedEvents.get(0).name == "Event2")
        assertTrue(organisedEvents.get(1).name == "Event1")
        assertTrue(organisedEvents.get(2).name == "Event4")
        assertTrue(organisedEvents.get(3).name == "Event3")
    }
    //test que ordena por distancia y están desordenadas
    @Test
    fun orderByDistanceEventSimpleCase(){
        val organisedEvents:List<Event> = LocalEventFilter.orderEventsByDistance(events2)
        assertTrue(organisedEvents.get(0).name == "Event2")
        assertTrue(organisedEvents.get(1).name == "Event1")
        assertTrue(organisedEvents.get(2).name == "Event4")
        assertTrue(organisedEvents.get(3).name == "Event3")
    }
    //test que ordena por distancia y están ya los eventos ordenados
    @Test
    fun orderByDistanceEventOneRepeated(){
        val events3:List<Event> = listOf(event12,event12,event42,event32)
        val organisedEvents:List<Event> = LocalEventFilter.orderEventsByDistance(events3)
        assertTrue(organisedEvents.get(0).name == "Event1")
        assertTrue(organisedEvents.get(1).name == "Event1")
        assertTrue(organisedEvents.get(2).name == "Event4")
        assertTrue(organisedEvents.get(3).name == "Event3")
    }
    //test que ordena por distancia y tienen el mismo nombre y distinta distancia
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
    //test que ordena por distancia y misma distancia y diferente nombre
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
    //test en el que se mete un evento incorrecto
    @Test
    fun orderByDistanceEventIncorrectEvent(){
        var eventE:Event = Event()
        val events3:List<Event> = listOf(eventE)
        val organisedEvents:List<Event> = LocalEventFilter.orderEventsByDistance(events3)
        assertFalse(organisedEvents.get(0).name == "EventE")
    }
    //test en el que un evento está vacío
    @Test
    @Throws(RSInvalidStateException::class)
    fun mergeSortNoStringPassed(){
        assertFailsWith(RSInvalidStateException::class){
            mergeSort(events2,"")
        }
    }
    //test qen el que se mete un string inválido
    @Test
    @Throws(RSInvalidStateException::class)
    fun mergeSortStringError(){
        assertFailsWith(RSInvalidStateException::class){
            mergeSort(events2,"hola")
        }
    }


    //test para cuando la lista está vacía y te ordena por tiempo de inicio a final
    @Test
    fun orderByTimeStartEndEventListEmpty(){
        val events3:List<Event> = listOf()
        assertTrue(LocalEventFilter.orderTimeFirstToLast(events3).count()==0)
    }
    //test para cuando la lista tiene 4 eventos y te ordena por tiempo de inicio a final
    @Test
    fun orderByTimeStartEndEventListHas4Events(){
        assertTrue(LocalEventFilter.orderTimeFirstToLast(events2).count()==4)
    }
    //test que ordena por tiempo de inicio a final y están ya los eventos ordenados
    @Test
    fun orderByTimeStartEndEventAlreadyOrdered(){
        val events3:List<Event> = listOf(event4,event3,event1,event2)
        val organisedEvents:List<Event> = LocalEventFilter.orderTimeFirstToLast(events3)
        assertTrue(organisedEvents.get(0).name == "4")
        assertTrue(organisedEvents.get(1).name == "3")
        assertTrue(organisedEvents.get(2).name == "1")
        assertTrue(organisedEvents.get(3).name == "2")
    }
    //test que ordena por tiempo de inicio a final y están desordenadas
    @Test
    fun orderByTimeStartEndEventSimpleCase(){
        val organisedEvents:List<Event> = LocalEventFilter.orderTimeFirstToLast(events)
        assertTrue(organisedEvents.get(0).name == "4")
        assertTrue(organisedEvents.get(1).name == "3")
        assertTrue(organisedEvents.get(2).name == "1")
        assertTrue(organisedEvents.get(3).name == "2")
    }
    //test que ordena por tiempo de inicio a final y están ya los eventos ordenados
    @Test
    fun orderByTimeStartEndEventOneRepeated(){
        val events3:List<Event> = listOf(event1,event1,event4,event3)
        val organisedEvents:List<Event> = LocalEventFilter.orderTimeFirstToLast(events3)
        /* for (event in organisedEvents){
            print(event.name)
        }*/
        assertTrue(organisedEvents.get(0).name == "4")
        assertTrue(organisedEvents.get(1).name == "3")
        assertTrue(organisedEvents.get(2).name == "1")
        assertTrue(organisedEvents.get(3).name == "1")
    }
    //test que ordena por tiempo de inicio a final y tienen el mismo nombre y distinta distancia
    @Test
    fun orderByTimeEventStartEnd2EventsSameNameDifferentTime(){
        var eventSpecial:Event = Event("EventSpecial", "2.1", "2017-12-03T08:30", "2017-12-03T10:30", "FOOD")
        val events3:List<Event> = listOf(event1,event2,event4,eventSpecial)
        val organisedEvents:List<Event> = LocalEventFilter.orderTimeFirstToLast(events3)
        assertTrue(organisedEvents.get(0).name == "EventSpecial")
        assertTrue(organisedEvents.get(1).name == "4")
        assertTrue(organisedEvents.get(2).name == "1")
        assertTrue(organisedEvents.get(3).name == "2")
    }
    //test que ordena por tiempo de inicio a final y misma distancia y diferente nombre
    @Test
    fun orderByTimeEventStartEndSameTimeDifferentName(){
        var eventSpecial:Event = Event("EventSpecial", "2.1", "2017-12-03T10:00", "2017-12-03T10:30", "FOOD")
        val events3:List<Event> = listOf(event3,event2,event1,eventSpecial)
        val organisedEvents:List<Event> = LocalEventFilter.orderTimeFirstToLast(events3)
        /*for (event in organisedEvents){
            print(event.name)
        }*/
        assertTrue(organisedEvents.get(0).name == "3")
        assertTrue(organisedEvents.get(1).name == "EventSpecial")
        assertTrue(organisedEvents.get(2).name == "1")
        assertTrue(organisedEvents.get(3).name == "2")
    }
    //test en el que se mete un evento incorrecto
    @Test
    fun orderByTimeEventStartEndIncorrectEvent(){
        var eventE:Event = Event()
        val events3:List<Event> = listOf(eventE)
        val organisedEvents:List<Event> = LocalEventFilter.orderTimeFirstToLast(events3)
        assertFalse(organisedEvents.get(0).name == "EventE")
    }



    //test para cuando la lista está vacía y te ordena por tiempo de inicio a final
    @Test
    fun orderByTimeEventEndStartListEmpty(){
        val events3:List<Event> = listOf()
        assertTrue(LocalEventFilter.orderTimeLastToFirst(events3).count()==0)
    }
    //test para cuando la lista tiene 4 eventos y te ordena por tiempo de inicio a final
    @Test
    fun orderByTimeEventEndStartListHas4Events(){
        assertTrue(LocalEventFilter.orderTimeLastToFirst(events2).count()==4)
    }
    //test que ordena por tiempo de inicio a final y están ya los eventos ordenados
    @Test
    fun orderByTimeEventEndStartAlreadyOrdered(){
        val events3:List<Event> = listOf(event2,event1,event3,event4)
        val organisedEvents:List<Event> = LocalEventFilter.orderTimeLastToFirst(events3)
        assertTrue(organisedEvents.get(0).name == "2")
        assertTrue(organisedEvents.get(1).name == "1")
        assertTrue(organisedEvents.get(2).name == "3")
        assertTrue(organisedEvents.get(3).name == "4")
    }
    //test que ordena por tiempo de inicio a final y están desordenadas
    @Test
    fun orderByTimeEventEndStartSimpleCase(){
        val organisedEvents:List<Event> = LocalEventFilter.orderTimeLastToFirst(events)
        assertTrue(organisedEvents.get(0).name == "2")
        assertTrue(organisedEvents.get(1).name == "1")
        assertTrue(organisedEvents.get(2).name == "3")
        assertTrue(organisedEvents.get(3).name == "4")
    }
    //test que ordena por tiempo de inicio a final y están ya los eventos ordenados
    @Test
    fun orderByTimeEventEndStartOneRepeated(){
        val events3:List<Event> = listOf(event1,event1,event4,event3)
        val organisedEvents:List<Event> = LocalEventFilter.orderTimeLastToFirst(events3)
        /* for (event in organisedEvents){
            print(event.name)
        }*/
        assertTrue(organisedEvents.get(0).name == "1")
        assertTrue(organisedEvents.get(1).name == "1")
        assertTrue(organisedEvents.get(2).name == "3")
        assertTrue(organisedEvents.get(3).name == "4")
    }
    //test que ordena por tiempo de inicio a final y tienen el mismo nombre y distinta distancia
    @Test
    fun orderByTimeEventEndStart2EventsSameNameDifferentTime(){
        var eventSpecial:Event = Event("EventSpecial", "2.1", "2017-12-03T08:30", "2017-12-03T10:30", "FOOD")
        val events3:List<Event> = listOf(event1,event2,event4,eventSpecial)
        val organisedEvents:List<Event> = LocalEventFilter.orderTimeLastToFirst(events3)
        assertTrue(organisedEvents.get(0).name == "2")
        assertTrue(organisedEvents.get(1).name == "1")
        assertTrue(organisedEvents.get(2).name == "4")
        assertTrue(organisedEvents.get(3).name == "EventSpecial")
    }
    //test que ordena por tiempo de inicio a final y misma distancia y diferente nombre
    @Test
    fun orderByTimeEventEndStartSameTimeDifferentName(){
        var eventSpecial:Event = Event("EventSpecial", "2.1", "2017-12-03T10:00", "2017-12-03T10:30", "FOOD")
        val events3:List<Event> = listOf(event3,event2,event1,eventSpecial)
        val organisedEvents:List<Event> = LocalEventFilter.orderTimeLastToFirst(events3)
        /*for (event in organisedEvents){
            print(event.name)
        }*/
        assertTrue(organisedEvents.get(0).name == "2")
        assertTrue(organisedEvents.get(1).name == "1")
        assertTrue(organisedEvents.get(2).name == "EventSpecial")
        assertTrue(organisedEvents.get(3).name == "3")
    }
    //test en el que se mete un evento incorrecto
    @Test
    fun orderByTimeEventEndStartIncorrectEvent(){
        var eventE:Event = Event()
        val events3:List<Event> = listOf(eventE)
        val organisedEvents:List<Event> = LocalEventFilter.orderTimeLastToFirst(events3)
        assertFalse(organisedEvents.get(0).name == "EventE")
    }
}