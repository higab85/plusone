package plusone.plusone


import com.google.gson.*
import java.io.IOException
import kotlin.jvm.java
import com.google.gson.reflect.TypeToken
import okhttp3.*


/**
 * Created by tyler on 30/11/17.
 */

class UsableResponse {
    var message:String? = null
    var body: String? = null

    constructor(response: Response?){
        this.message = response?.message()
        this.body = response?.body()?.string()
    }
}

object ServerConnection{

    val JSON = MediaType.parse("application/json; charset=utf-8")

    val client = OkHttpClient()
    val urlHost = "https://fierce-plateau-73728.herokuapp.com"

    fun getUserData(){
        val gson = Gson()
        val url = urlHost + "/user/" + CurrentUser.email
        val response:UsableResponse? = get(url, CurrentUser.token)
        if (response?.message == "OK") {
            var user = gson.fromJson(response?.body, User::class.java)
            CurrentUser.username = user.username
            CurrentUser.email = user.email
            CurrentUser.name = user.name
        }

    }

    fun loginUser():Boolean{
        val url = urlHost + "/login"
        val credential = Credentials.basic(CurrentUser.email, CurrentUser.password)
        val response:UsableResponse? = get(url,credential)

        if (response?.message == "NOT FOUND" || response?.message == "UNAUTHORIZED")
            return false

        CurrentUser.token = response?.body!!
        getUserData()
        return true
    }

    fun registerUser(user: User):Boolean{
        val gson = Gson()
        val json = gson.toJson(user)

        val url = urlHost + "/user"
        val response:UsableResponse? = post(url, json, false)

        if (response?.message == "OK") {
            loginUser()
            return true
        }
        return false
    }

    fun modifyUser(user:User):Boolean{
        val gson = Gson()
        val json = gson.toJson(user)

        val url = urlHost + "/user"
        val response:UsableResponse? = put(url, json)

        if (response?.message == "OK") {
            getUserData()
            return true
        }

        return false
    }

    fun createEvent(event:Event):Boolean{
        val gson = Gson()
        val json = gson.toJson(event)
        val url = urlHost + "/event"
        val response:UsableResponse? = post(url, json, true)
        // TODO: this error is wrong, but needs to be fixed on server first.
        if (response?.message == "OK")
            return true
        return false
    }

    fun getEvents():List<Event>?{
        val gson = Gson()
        val url = urlHost + "/event"

        val response:UsableResponse? = get(url,CurrentUser.token)

//        https://futurestud.io/tutorials/gson-mapping-of-arrays-and-lists-of-objects
        val eventListType = object : TypeToken<List<Event>>() {
        }.type
        val list:List<Event> = gson.fromJson(response?.body, eventListType)
        return list
    }

    fun getEventSubscriptionsFrom(user:User):List<Event>?{
        val gson = Gson()
        val url = urlHost + "/user/events"

        val response:UsableResponse? = get(url,CurrentUser.token)

        var event12:Event = Event("Event1", "0.0", "2017-12-03T12:30", "2017-12-03T13:30", "PARTY",2.2)
        var event22:Event = Event("Event2", "1.1", "2017-12-03T13:00", "2017-12-03T13:30", "FOOD",0.5)
        var event32:Event = Event("Event3", "2.1", "2017-12-03T10:00", "2017-12-03T14:30", "FOOD",7.5)
        var event42:Event = Event("Event4", "2.1", "2017-12-03T09:00", "2017-12-03T10:30", "FOOD",5.5)
        val events2:List<Event> = listOf(event12,event22,event32,event42)

//        https://futurestud.io/tutorials/gson-mapping-of-arrays-and-lists-of-objects
        val eventListType = object : TypeToken<List<Event>>() {
        }.type
        val list:List<Event> = gson.fromJson(response?.body, eventListType)
        return events2
    }

    fun getUsersSubscribedTo(event:Event):List<User>?{
        val gson = Gson()
        val url = urlHost + "/user/events"

        val response:UsableResponse? = get(url,CurrentUser.token)

        val user1:User = User("Tom", "tom59", "tom@google.com")
        val user2:User = User("Jerry", "jerryferry", "jerry@fashion.com")
        val user3:User = User("Ron Paul", "pauliepaul", "ron@paul.com")
        val user4:User = User("Arnold", "arniebeast", "arnold@schwarzenegger.com")
        val list:List<User> = listOf(user1, user2, user3, user4)
//        https://futurestud.io/tutorials/gson-mapping-of-arrays-and-lists-of-objects
//        val eventListType = object : TypeToken<List<Event>>() {
//        }.type
//        val list:List<Event> = gson.fromJson(response?.body, eventListType)
        return list
    }

    fun getEvents(queryMap:Map<String,String>):List<Event>?{
        val gson = Gson()
        var query = ""
        queryMap.forEach { key, value -> query += ",$key=$value" }
        query = query.removePrefix(",")
        val url = urlHost + "/event?$query"
        System.out.println(url)
        val response:UsableResponse? = get(url,CurrentUser.token)

//        https://futurestud.io/tutorials/gson-mapping-of-arrays-and-lists-of-objects
        val eventListType = object : TypeToken<List<Event>>() {
        }.type
        val list:List<Event> = gson.fromJson(response?.body, eventListType)
        return list
    }

    fun getEvent(event:Event):Event?{
        val gson = Gson()
        val url = urlHost + "/event/" + event.id
        val response:UsableResponse? = get(url, CurrentUser.token)
        if (response?.message == "OK")
            return gson.fromJson(response?.body, Event::class.java)
        return null

    }

    fun isAttending(event: Event):Boolean?{
        val gson = Gson()
        val url = urlHost + "/event/" + event.id
        val response:UsableResponse? = get(url, CurrentUser.token)
        // TODO: this error is wrong, but needs to be fixed on server first.
        if (response?.message == "OK")
            return gson.fromJson(response?.body, Event::class.java).subscription
        return null

    }


    fun searchEvent():Boolean{
        val gson = Gson()
        val json = gson.toJson(CurrentUser)
        val url = urlHost + "/event"
        val response:UsableResponse? = post(url, json, true)
        // TODO: this error is wrong, but needs to be fixed on server first.
        if (response?.message == "401")
            return false
        return true
    }

    fun toggleSubscriptionToEvent(event:Event):Boolean{
        val gson = Gson()
        val json = gson.toJson(CurrentUser)
        val url = urlHost + "/event/" + event.id
        val response:UsableResponse? = post(url, json, true)
        // TODO: this error is wrong, but needs to be fixed on server first.
        if (response?.message == "401")
            return false
        return true
    }

    //------------------
    //  HTTP METHODS
    //------------------

    @Throws(IOException::class)
    fun post(url:String, json:String, needsToken:Boolean): UsableResponse? {

        val reqBody = RequestBody.create(JSON, json)
        var request: Request?
        if (needsToken) {
            request = Request.Builder()
                    .url(url)
                    .header("Authorization", CurrentUser.token)
                    .post(reqBody)
                    .build()
        }else {
            request = Request.Builder()
                    .url(url)
                    .post(reqBody)
                    .build()
        }
        val response = client?.newCall(request)?.execute()

        return UsableResponse(response)
    }

    @Throws(IOException::class)
    fun put(url:String, json:String): UsableResponse? {

        val reqBody = RequestBody.create(JSON, json)
        var request = Request.Builder()
                        .url(url)
                        .header("Authorization", CurrentUser.token)
                        .put(reqBody)
                        .build()

        val response = client?.newCall(request)?.execute()

        return UsableResponse(response)
    }

    @Throws(IOException::class)
    fun get(url:String, authorization:String): UsableResponse? {
        val request = Request.Builder()
                .url(url)
                .header("Authorization", authorization)
                .get()
                .build()

        val response = client.newCall(request)?.execute()
        return UsableResponse(response)
    }


}
