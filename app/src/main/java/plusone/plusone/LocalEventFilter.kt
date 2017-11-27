package plusone.plusone

import android.renderscript.RSInvalidStateException
import org.joda.time.*

/**
 * Created by tyler on 28/10/17.
 */

object LocalEventFilter {

    // adapted from:
    // https://rosettacode.org/wiki/Sorting_algorithms/Merge_sort#Kotlin
    fun mergeSort(list: List<Event>, type: String): List<Event> {
        if (list.size <= 1) {
            return list
        }

        val left = mutableListOf<Event>()
        val right = mutableListOf<Event>()

        val middle = list.size / 2
        list.forEachIndexed { index, number ->
            if (index < middle) {
                left.add(number)
            } else {
                right.add(number)
            }
        }

        fun merge(left: List<Event>, right: List<Event>): List<Event> = mutableListOf<Event>().apply {
            var indexLeft = 0
            var indexRight = 0

            while (indexLeft < left.size && indexRight < right.size) {

                if (type == "time") {
                    if (left[indexLeft].getstartDateTime() <= right[indexRight].getstartDateTime()) {
                        add(left[indexLeft])
                        indexLeft++
                    } else {
                        add(right[indexRight])
                        indexRight++
                    }
                } else if (type == "distance") {
                    if (left[indexLeft].distance <= right[indexRight].distance) {
                        add(left[indexLeft])
                        indexLeft++
                    } else {
                        add(right[indexRight])
                        indexRight++
                    }
                } else {
                    throw RSInvalidStateException("No vayas de sobrao")
                }
            }
            while (indexLeft < left.size) {
                add(left[indexLeft])
                indexLeft++
            }

            while (indexRight < right.size) {
                add(right[indexRight])
                indexRight++
            }

        }

        return merge(mergeSort(left, type), mergeSort(right, type))
    }


    fun orderTimeFirstToLast(events: List<Event>): List<Event> =
            mergeSort(events, "time")


    // What the fuck is going can be understood by reading
    // https://kotlinlang.org/docs/reference/lambdas.html
    fun filterByStartTimeAfter(events: List<Event>, startTime: LocalDateTime): List<Event> =
            events.filter { it.getstartDateTime() >= startTime }

    fun filterByStartTimeBefore(events: List<Event>, startTime: LocalDateTime): List<Event> =
            events.filter { it.getstartDateTime() <= startTime }

    fun orderTimeLastToFirst(events: List<Event>): List<Event> =
            mergeSort(events, "time").reversed()

    fun orderEventsByDistance(events: List<Event>): List<Event> {
        /*for (event in events){
        print(event.name)
    }*/
        return mergeSort(events, "distance")
    }

    fun searchEventByName(events: List<Event>?, query:String): List<Event> =
            events!!.filter { it.name.contains(query) }

}

