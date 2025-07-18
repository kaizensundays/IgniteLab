package com.kaizensundays.flights.service

import com.kaizensundays.flights.service.messages.Event
import com.kaizensundays.flights.service.messages.FindFlight
import com.kaizensundays.flights.service.messages.Flight
import com.kaizensundays.flights.service.messages.JacksonObjectConverter
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

/**
 * Created: Monday 9/27/2021, 1:27 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
class FrontEndWebSocketHandler(
    private val handler: FindFlightHandler
) : NodeStateAwareWebSocketHandler() {

    private val converter = JacksonObjectConverter<Event>()

    fun handleEvent(event: Event): Flux<Flight> {

        return when (event) {
            is FindFlight ->
                handler.getFlights(Flux.just(event))
            else ->
                Flux.empty()
        }
    }

    @SuppressWarnings("kotlin:S6508") // "Unit" should be used instead of "Void"
    override fun handle(session: WebSocketSession): Mono<Void> {

        val subscriber = session.receive()
            .doOnSubscribe { subscription -> addSession(session, subscription) }
            .map { msg -> msg.payloadAsText }
            .log()
            .publishOn(Schedulers.boundedElastic())
            .map { json -> converter.toObject(json) }
            .flatMap { event -> handleEvent(event) }
            .map { flight -> converter.fromObject(flight) }
            .log()
            .map { json -> session.textMessage(json) }


        return session.send(subscriber)
            .doOnTerminate { removeSession(session) }
    }

}