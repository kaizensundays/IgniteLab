package com.kaizensundays.flights.service

import com.kaizensundays.flights.service.messages.FindFlight
import com.kaizensundays.flights.service.messages.JacksonObjectConverter
import com.kaizensundays.flights.service.messages.JacksonSerializable
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created: Friday 11/12/2021, 10:59 AM Eastern Time
 *
 * @author Sergey Chuykov
 */
abstract class MuTestSupport {

    val logger: Logger = LoggerFactory.getLogger(javaClass)

    val converter = JacksonObjectConverter<JacksonSerializable>()

    fun Map<String, Array<FindFlight>>.mergeValues(): List<FindFlight> {
        return this.values.fold(mutableListOf()) { flights, values ->
            flights.addAll(values); flights
        }
    }

    fun unixNL(s: String) = s.replace("\r\n", "\n")

}