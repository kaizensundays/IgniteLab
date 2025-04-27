package com.kaizensundays.flights.service

import com.kaizensundays.ignite.quorum.DefaultTopologyValidator
import com.kaizensundays.flights.service.dao.FindFlightDao
import com.kaizensundays.flights.service.messages.FindFlight
import org.apache.ignite.cache.CacheAtomicityMode
import org.apache.ignite.configuration.CacheConfiguration
import org.apache.ignite.configuration.IgniteConfiguration
import org.apache.ignite.configuration.TopologyValidator
import org.apache.ignite.events.EventType
import org.apache.ignite.logger.slf4j.Slf4jLogger
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit
import javax.cache.expiry.CreatedExpiryPolicy
import javax.cache.expiry.Duration

/**
 * Created: Saturday 9/25/2021, 1:53 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
@Configuration
open class IgniteContext {

    @Bean
    open fun findFlightCacheStore(dao: FindFlightDao) = FindFlightCacheStore(dao)

    @Bean
    open fun topologyValidator(@Value("\${cluster.quorum}") quorum: Int): TopologyValidator {
        return DefaultTopologyValidator(quorum)
    }

    @Bean
    open fun ignite(findFlightCacheStore: FindFlightCacheStore, topologyValidator: TopologyValidator): IgniteFactoryBean {

        val configuration = IgniteConfiguration()
            .setGridLogger(Slf4jLogger())
            .setDiscoverySpi(
                TcpDiscoverySpi()
                    .setLocalPort(47701)
                    .setLocalPortRange(5)
                    .setIpFinder(TcpDiscoveryVmIpFinder()
                        .setAddresses(listOf(
                            "flights-0.flights.default.svc.cluster.local:47701..47705",
                            "flights-1.flights.default.svc.cluster.local:47701..47705",
                            "flights-2.flights.default.svc.cluster.local:47701..47705"
                        )))
            )
            .setCacheConfiguration(
                CacheConfiguration<String, FindFlight>()
                    .setName(CacheName.Requests)
                    .setTopologyValidator(topologyValidator)
                    .setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL)
                    .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration(TimeUnit.DAYS, 1)))
                    .setCacheStoreFactory(findFlightCacheStore.factory())
                    .setReadThrough(true)
                    .setWriteThrough(true),
                CacheConfiguration<String, String>()
                    .setName(CacheName.Results)
                    .setTopologyValidator(topologyValidator)
                    .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration(TimeUnit.DAYS, 1))),
            )
            .setIncludeEventTypes(
                *EventType.EVTS_DISCOVERY
            )

        return IgniteFactoryBean(configuration)
    }

}