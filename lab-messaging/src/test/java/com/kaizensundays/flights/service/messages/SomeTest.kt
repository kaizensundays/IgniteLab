package com.kaizensundays.flights.service.messages

import com.kaizensundays.lab.grpc.Flight
import com.kaizensundays.lab.grpc.FlightStatus
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.test.assertEquals

/**
 * Created: Saturday 5/31/2025, 1:14 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
class SomeTest {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    val dtf = DateTimeFormatter.ISO_ZONED_DATE_TIME
        .withLocale(Locale.US)
        .withZone(ZoneId.of("America/Los_Angeles"))

    fun zonedDateTime(s: String): ZonedDateTime {
        return ZonedDateTime.parse(s, dtf)
    }

    @Test
    fun test() {

        val departureTime = zonedDateTime("2025-05-31T11:17:00-07:00[America/Los_Angeles]")
        val arrivalTime = zonedDateTime("2025-05-31T12:33:00-07:00[America/Los_Angeles]")

        val departure = dtf.format(departureTime)
        val arrival = dtf.format(arrivalTime)

        val flight = Flight.newBuilder()
            .setId(1)
            .setNumber(1)
            .setAirlineId(1)
            .setDepartureAirport("SFO")
            .setArrivalAirport("LAS")
            .setDeparture(departure)
            .setArrival(arrival)
            .setStatus(FlightStatus.SCHEDULED)
            .build()

        assertEquals(FlightStatus.SCHEDULED, flight.status)
    }

}