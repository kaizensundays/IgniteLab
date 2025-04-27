package com.kaizensundays.flights.service.messages

/**
 * Created: Sunday 3/5/2023, 11:26 AM Eastern Time
 *
 * @author Sergey Chuykov
 */
data class AddAirline(val id: Int, val code: String, val name: String) : Event