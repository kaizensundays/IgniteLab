package com.kaizensundays.flights.service

import com.fasterxml.jackson.core.type.TypeReference
import com.kaizensundays.flights.service.messages.FindFlight
import com.kaizensundays.flights.service.messages.Flight
import org.springframework.core.io.ClassPathResource
import org.springframework.util.FileCopyUtils
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Created: Saturday 10/30/2021, 2:03 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
object Flights {

    val flightTypeRef = object : TypeReference<Map<String, Array<Flight>>>() {}

    val findFlightTypeRef = object : TypeReference<Map<String, Array<FindFlight>>>() {}

    fun read(location: String): String {
        val reader = InputStreamReader(ClassPathResource(location).inputStream, "UTF-8")
        return FileCopyUtils.copyToString(reader)
    }

    fun String.adjustFormat(): String {
        return replace("\" :".toRegex(), "\":")
            .replace(" \\{".toRegex(), "\r\n    {")
            .replace(" {4}\"".toRegex(), "      \"")
            .replace(" {2}}".toRegex(), "    }")
            .replace(" ]".toRegex(), "\r\n  ]")
    }

    private var localDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun String.parseLocalDate(): LocalDate {
        return LocalDate.parse(this, localDateFormatter)
    }

    fun LocalDate.format(): String {
        return localDateFormatter.format(this)
    }

}