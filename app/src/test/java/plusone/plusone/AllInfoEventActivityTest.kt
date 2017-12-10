/*
package plusone.plusone

import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import kotlin.test.*
*/
/**
 * Created by Juanan on 10/11/2017.
 *//*

class AllInfoEventActivityTest {
    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {

    }
    @Test
    @Throws(NumberFormatException:: class)
    fun comprobarQueElIdEventNoPuedeRecibirUnStringEnFuncionSubscribeEvent(){
        var activity = AllInfoEventActivity()
        val input1: String = "juanan"
        assertFailsWith(NumberFormatException:: class) { activity.subscribeEvent(iduser = 0,idevent = input1.toInt()) }

    }
    @Test
    @Throws(NumberFormatException:: class)
    fun comprobarQueElIdEventNoPuedeRecibirUnStringEnFuncionUnSubscribeEvent(){
        var activity = AllInfoEventActivity()
        val input1: String = "juanan"
        assertFailsWith(NumberFormatException:: class) { activity.unsubscribeEvent(iduser = 0,idevent = input1.toInt()) }

    }
    @Test
    fun comprobarQueLosValoresDeSuscribeEventNoSonNulos(){
        var activity = AllInfoEventActivity()
        assertNotNull(activity.subscribeEvent(idevent=0, iduser = 0))

    }
    @Test
    fun comprobarQueLosValoresDeUnSuscribeEventNoSonNulos(){
        var activity = AllInfoEventActivity()
        assertNotNull(activity.unsubscribeEvent(idevent=0, iduser = 0))

    }


}*/
