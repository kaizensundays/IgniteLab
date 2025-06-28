package com.kaizensundays.flights.service.dao

import com.kaizensundays.flights.service.messages.FlightExt
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component

/**
 * Created: Sunday 6/1/2025, 12:22 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
@Component
interface FlightRepository : JpaRepository<FlightExt, String>
