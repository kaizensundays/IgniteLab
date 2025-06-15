package com.kaizensundays.flights.service

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Created: Sunday 4/27/2025, 3:26 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
@ConfigurationProperties(prefix = "node")
class NodeProperties {

    var tcpDiscoveryAddresses = listOf<String>()

    var kubeEnabled = false
    var kubeNamespace = "default"
    var kubeServiceName = "ignite"

}