package com.kaizensundays.flights.service.messages

import jakarta.persistence.Entity

/**
 * Created: Saturday 10/30/2021, 1:25 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
data class Flight(
        var code: String,
        var airline: String,
        var price: Double,
        var departure: Long,
        var arrival: Long
) : Event