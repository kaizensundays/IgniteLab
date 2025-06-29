package com.kaizensundays.flights.service.dao

import com.kaizensundays.flights.service.Flights.format
import com.kaizensundays.flights.service.messages.FindFlight
import org.springframework.jdbc.core.RowMapper
import java.sql.Date
import java.sql.ResultSet
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

/**
 * Created: Sunday 10/24/2021, 1:53 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
class FindFlightRowMapper : RowMapper<FindFlight> {

    fun toLocalDate(date: Date): LocalDate = Instant.ofEpochMilli(date.getTime())
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    override fun mapRow(rs: ResultSet, rowNum: Int): FindFlight {
        return FindFlight(
            rs.getString("user_id"),
            rs.getString("ip"),
            rs.getString("init"),
            rs.getString("dest"),
            toLocalDate(rs.getDate("depart")).format(),
            toLocalDate(rs.getDate("goback")).format(),
            rs.getString("uuid"),
        )
    }

}