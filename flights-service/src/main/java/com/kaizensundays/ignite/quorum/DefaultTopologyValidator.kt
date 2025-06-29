package com.kaizensundays.ignite.quorum

import org.apache.ignite.cluster.ClusterNode
import org.apache.ignite.configuration.TopologyValidator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created: Monday 2/20/2023, 11:16 AM Eastern Time
 *
 * @author Sergey Chuykov
 */
class DefaultTopologyValidator(val quorum: Int) : TopologyValidator {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(DefaultTopologyValidator::class.java)
    }

    override fun validate(nodes: MutableCollection<ClusterNode>): Boolean {

        nodes.forEach { node ->
            logger.info("$node")
        }

        val isActive = nodes.size >= quorum
        logger.info("isActive=$isActive")

        return isActive
    }

}