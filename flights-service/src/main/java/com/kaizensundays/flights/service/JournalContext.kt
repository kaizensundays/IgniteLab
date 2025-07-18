package com.kaizensundays.flights.service

import com.kaizensundays.flights.service.dao.JournalDao
import org.h2.jdbcx.JdbcDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.sql.DataSource

/**
 * Created: Saturday 3/4/2023, 8:03 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
@Configuration
@EnableConfigurationProperties(value = [JournalProperties::class])
open class JournalContext {

    @Bean
    @Qualifier("journalH2DataSource")
    open fun journalH2DataSource(props: JournalProperties): DataSource {
        val ds = JdbcDataSource()
        ds.setURL(props.h2Url())
        ds.user = props.h2User
        ds.password = props.h2Password
        return ds
    }

    @Bean
    open fun journalH2Jdbc(@Qualifier("journalH2DataSource") journalH2DataSource: DataSource): NamedParameterJdbcTemplate {
        return NamedParameterJdbcTemplate(journalH2DataSource)
    }

    @Bean
    open fun journalH2Dao(journalH2Jdbc: NamedParameterJdbcTemplate) = JournalDao(journalH2Jdbc)

    @Bean
    open fun journalManager(journalDao: JournalDao) = JournalManager(journalDao)

}