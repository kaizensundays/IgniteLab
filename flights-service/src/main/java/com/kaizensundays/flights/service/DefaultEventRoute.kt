package com.kaizensundays.flights.service

import com.kaizensundays.flights.service.messages.Event
import com.kaizensundays.flights.service.messages.JacksonObjectConverter
import com.kaizensundays.flights.service.messages.Journal
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.BlockingQueue
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import jakarta.annotation.PostConstruct

/**
 * Created: Sunday 3/5/2023, 11:24 AM Eastern Time
 *
 * @author Sergey Chuykov
 */
class DefaultEventRoute(
    private val messageQueue: BlockingQueue<Journal>,
    private val journalManager: JournalManager,
    private val handlers: Map<Class<out Event>, Handler<Event>>
) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS z")

    private val jsonConverter = JacksonObjectConverter<Event>()

    private val defaultExecutor: ExecutorService = Executors.newSingleThreadExecutor { r -> Thread(r, "D") }

    private val journalExecutor: ExecutorService = Executors.newSingleThreadExecutor { r -> Thread(r, "J") }

    private val running = AtomicBoolean(true)

    init {
        df.timeZone = TimeZone.getTimeZone("America/New_York")
    }

    private fun execute(journal: Journal) {

        val event = journal.event
        if (event != null) {

            val handler = handlers[event.javaClass]
            if (handler != null) {
                handler.handle(event)
            } else {
                logger.info("Unexpected message type: ${event.javaClass}")
            }
        }
        if (journalManager.commit(journal) == 0) {
            logger.error("Unable to commit journal")
        }
    }

    private fun execute() {

        while (running.get()) {
            val journal = messageQueue.poll(1, TimeUnit.SECONDS)
            if (journal != null) {
                execute(journal)
            }
        }
    }

    fun handle(event: Event) {
        logger.info("" + event)

        journalExecutor.execute { journalManager.accept(event) }
    }

    @PostConstruct
    fun start() {

        defaultExecutor.execute { journalManager.load() }

        defaultExecutor.execute { execute() }

        logger.info("Started")
    }

}