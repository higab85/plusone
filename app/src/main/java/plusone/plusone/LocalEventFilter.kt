package plusone.plusone

import java.time.LocalDateTime

/**
 * Created by tyler on 28/10/17.
 */

object LocalEventFilter {
    // What the fuck is going can be understood by reading
    // https://kotlinlang.org/docs/reference/lambdas.html
    fun orderTimeFirstToLast(events: List<Event>): List<Event> =
            events.sortedBy{ it.getstartDateTime() }

    fun filterByStartTimeAfter(events: List<Event>, startTime: LocalDateTime): List<Event> =
            events.filter { it.getstartDateTime() >= startTime }

    fun filterByStartTimeBefore(events: List<Event>, startTime: LocalDateTime): List<Event> =
            events.filter { it.getstartDateTime() <= startTime }

    fun orderTimeLastToFirst(events: List<Event>): List<Event> =
            events.sortedBy { it.getstartDateTime() }.reversed()

    fun orderEventsByDistance(events: List<Event>): List<Event> =
            events.sortedBy { it.distance }

    fun searchEventByName(events: List<Event>?, query:String): List<Event> =
            events!!.filter { it.name.contains(query) }

}

