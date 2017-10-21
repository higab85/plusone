package plusone.plusone

/**
 * Created by Gabriel on 6/10/17.
 */

import android.app.usage.UsageEvents
import java.sql.*
import java.util.Properties


object DatabaseConnection {

    // Constructor
    init{
        getConnection()
    }

    internal var conn: Connection? = null // connection to db

    // log user in. This will create a CurrentUser(static) with the user that has logged in
    fun loginUser(user:String, pass:String):Boolean{
        if (verifyUser(user, pass)) {
            try{
                val stmt = conn!!.createStatement()
                val query = "SELECT * FROM plusone.users WHERE username = '$user';"
                val resultSet = stmt!!.executeQuery(query)
                resultSet!!.next()
                CurrentUser.email = resultSet.getString("email")
                CurrentUser.name  = resultSet.getString("username")
                CurrentUser.userLoggedIn = true
                CurrentUser.username = user
                return true
            }
            catch (ex: SQLException){
                ex.printStackTrace()
            }
        }
        return false
    }

    // Returns true if password matches user on server.
    fun verifyUser(user:String, pass:String): Boolean {
        try{
            val stmt = conn!!.createStatement()
            val query = "SELECT * FROM plusone.users WHERE username = '$user';"
            val resultSet = stmt!!.executeQuery(query)
            resultSet!!.next()
            try {
                return pass == resultSet.getString("pw")
            }
            catch (ex:SQLException) {
                // TODO: log this
                ex.printStackTrace()
            }
        }
        catch (ex: SQLException){
            // TODO: log this
            ex.printStackTrace()
        }
        return false
    }
    // TODO: registers user
    fun registerUser(user:String, pass:String){

    }


    //Send informations of the event to the DB
    @Throws (SQLException::class)
    fun createEventDB(event: Event): Boolean{


        try{

            val stmt = conn!!.createStatement()

            // TODO: Add description
            val query = "INSERT INTO plusone.events (name,description,start_date, end_date," +
                    "location, type, req_people) VALUES ('${event.name}','${event.description}'," +
                    "'${event.start}','${event.end}','${event.location}','${event.type}'," +
                    "'${event.reqPeople}');"

            stmt!!.executeUpdate(query)

//        conn!!.commit()
//        conn!!.close()
            return true
        } catch (ex: SQLException) {
            // TODO: log this
            ex.printStackTrace()
        }
        return false
    }



    /**
     * This method makes a connection to MySQL Server and initialises conn.
     */
    private fun getConnection():Boolean {
        val connectionProps = Properties()
        connectionProps.put("user", "plusone")
        connectionProps.put("password", "plusone")
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance()
            conn = DriverManager.getConnection(
                    "jdbc:" + "mysql" + "://" +
                            "plusoneapp.ddns.net" +
                            ":" + "3306" + "/" +
                            "",
                    connectionProps)
            return true
        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
        } catch (ex: Exception) {
            // handle any errors
            ex.printStackTrace()
        }
        return false
    }


}