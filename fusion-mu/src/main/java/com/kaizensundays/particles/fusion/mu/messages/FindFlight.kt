package com.kaizensundays.particles.fusion.mu.messages

import java.io.Serializable

/**
 * Created: Sunday 10/10/2021, 1:47 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
data class FindFlight(
        val user: String,
        val ip: String,
        val from: String,
        val to: String,
        val depart: String,
        val goback: String,
        var uuid: String = ""
) : Event, Serializable