package com.kaizensundays.flights.service.messages

import java.util.*

/**
 * Created: Saturday 3/4/2023, 11:55 AM Eastern Time
 *
 * @author Sergey Chuykov
 */
data class Journal(
    val id: Long,
    val state: Int,
    val time: Date,
    val uuid: String,
    val msg: String,
    @Transient
    var event: Event? = null
) : JacksonSerializable