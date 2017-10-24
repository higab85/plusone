package plusone.plusone

import kotlinx.android.synthetic.main.activity_event.*
import org.junit.Assert.*
import org.junit.Test
import java.sql.SQLException
import java.time.LocalDateTime

/**
 * Created by tyler on 6/10/17.
 */
class DatabaseConnectionTest {


    // tests whether the app can connect to DB
    @Test
    fun canConnectToDB() {
        assertTrue(DatabaseConnection.conn != null)
    }

    // tests whether user with correct credentials can be verified
    @Test
    fun userAuthCorrectCredentials(){
        assertTrue(DatabaseConnection.verifyUser("demo", "demo"))
//        assertFalse(db.verifyUser("Allan", "wrongpass"))
    }
    // tests whether user with wrong password can be verified
    @Test
    fun userAuthWrongPassword(){
        assertFalse(DatabaseConnection.verifyUser("demo", "wrongpass"))
    }

    // tests whether unregistered can be verified
    @Test
    fun userAuthWrongUser(){
        assertFalse(DatabaseConnection.verifyUser("McNuggets", "Kluckpass"))
    }

    // tests whether user object is created
    @Test
    fun userObjectCreation(){
        DatabaseConnection.loginUser("demo", "demo")
        assertTrue(CurrentUser.userLoggedIn)
    }

    // test whether events can be saved to db
    @Test
    fun eventCreation(){
        DatabaseConnection.loginUser("demo", "demo")
        var event = Event()

        event.name = "test-title"
        event.start = "02/04/2017 at 02:00"
        event.end = "02/04/2017 at 02:30"
        event.location = "club"
        event.type = EventType.OTHER
        event.reqPeople = 1
        try {
            DatabaseConnection.createEventDB(event)
        }catch (e:SQLException) {
            fail()
        }
    }

    @Test
    fun getEvents(){
        DatabaseConnection.loginUser("demo", "demo")
        assertNotNull(DatabaseConnection.getEventsDB())
    }

//    @Test
//    fun getPartyEvents(){
//        DatabaseConnection.loginUser("demo", "demo")
//        val eventList:MutableList<Event> =  mutableListOf()
//        for(i:Int in 0..4){
//            var event = Event()
//            event.name = "test-$i"
//            event.start = "02/04/2017 at 0$i:00"
//            event.end = "02/04/2017 at 0$i:30"
//            event.location = "club"
//            event.type = EventType.OTHER
//            event.reqPeople = i
//            eventList.add(event)
//        }
//        val finalEventList = eventList.toList()
//
//        // this function will search in the List ($1:List<Event>) for events where the argument specified
//        // ($2:string) has the value specified ($3:string) and returns a List with all the wanted events.
//        // eg. this following example will return a List<Event> with all events in `finalEventList`
//        // where the `type` is `party`
//        val resultEventList:MutableList<Event> = filterEvents(finalEventList,"type" ,"party")
//
//        assertTrue(resultEventList.isEmpty())
//
//    }

}