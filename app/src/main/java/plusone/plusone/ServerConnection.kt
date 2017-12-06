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


    fun loginUser():Boolean{
        val url = urlHost + "/login"
        val credential = Credentials.basic(CurrentUser.email, CurrentUser.password)
        val response:UsableResponse? = get(url,credential)

        if (response?.message == "NOT FOUND" || response?.message == "UNAUTHORIZED")
            return false

        CurrentUser.token = response?.body!!
        return true
    }

    fun registerUser():Boolean{
        val gson = Gson()
        val json = gson.toJson(CurrentUser)

        val url = urlHost + "/user"
        val response:UsableResponse? = post(url, json, false)

        if (response?.message == "OK") {
            loginUser()
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
        val url = urlHost + "/user/" + event.id
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
        val url = urlHost + "/user"
        val response:UsableResponse? = post(url, json, true)
        // TODO: this error is wrong, but needs to be fixed on server first.
        if (response?.message == "401")
            return false
        return true
    }

    fun toggleSubscriptionToEvent(event:Event):Boolean{
        val gson = Gson()
        val json = gson.toJson(CurrentUser)
        val url = urlHost + "/event" + event.id
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
