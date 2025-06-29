package com.kaizensundays.ignite.quorum

import jakarta.annotation.PostConstruct
import org.apache.ignite.Ignite
import org.apache.ignite.cluster.ClusterNode
import org.apache.ignite.events.DiscoveryEvent
import org.apache.ignite.events.Event
import org.apache.ignite.events.EventType
import org.apache.ignite.lang.IgnitePredicate
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created: Monday 2/20/2023, 12:10 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
class NodeState(val quorum: Int, val ignite: Ignite) : IgnitePredicate<Event>, NodeStateListener {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private val active = AtomicBoolean(false)

    private val nodeStateListeners = mutableListOf<NodeStateListener>()

    fun addListener(listener: NodeStateListener) {
        nodeStateListeners.add(listener)
    }

    override fun onStateChange(active: Boolean) {

        logger.info("Node is active: $active")

        if (this.active.getAndSet(active) != active) {
            nodeStateListeners.forEach { listener -> listener.onStateChange(active) }
        }
    }

    fun isActive(nodes: Collection<ClusterNode>): Boolean {

        return nodes.size >= quorum
    }

    private fun print(nodes: Collection<ClusterNode>) {
        nodes.forEach { node ->
            val id = node.id().toString()
            logger.info("{}", id)
        }
    }

    override fun apply(event: Event): Boolean {

        logger.info("event: {}", event)

        if (event is DiscoveryEvent) {
            val nodes = event.topologyNodes()

            print(nodes)

            val active = isActive(nodes)

            onStateChange(active)
        }

        return true
    }

    @PostConstruct
    fun start() {

        ignite.events().localListen(this, *EventType.EVTS_DISCOVERY)

        val nodes = ignite.cluster().forServers().nodes()

        print(nodes)

        val active = isActive(nodes)

        onStateChange(active)

        logger.info("Stared({${active}})")
    }

}