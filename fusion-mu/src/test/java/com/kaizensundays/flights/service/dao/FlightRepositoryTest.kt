package com.kaizensundays.flights.service.dao

import com.kaizensundays.flights.service.messages.FlightExt
import junit.framework.TestCase.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

/**
 * Created: Sunday 6/1/2025, 12:23 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
@ImportAutoConfiguration(
    classes = [
        DataSourceAutoConfiguration::class,
        DataSourceTransactionManagerAutoConfiguration::class,
        JpaRepositoriesAutoConfiguration::class,
        HibernateJpaAutoConfiguration::class,
        TransactionAutoConfiguration::class
    ]
)
@EntityScan(basePackageClasses = [FlightExt::class])
@EnableJpaRepositories(basePackageClasses = [FlightRepository::class])
@ActiveProfiles("test")
@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = [JpaTestContext::class])
class FlightRepositoryTest {

    @Autowired
    lateinit var repo: FlightRepository

    @Test
    fun test() {

        val result = repo.findAll()

        assertNotNull(result)
    }

}