package com.kaizensundays.flights.service.messages;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Created: Sunday 6/1/2025, 12:35 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
@Entity
@Table(name = "flights")
public class FlightExt {

    @Id
    public String code;
    public String airline;

}
