package io.github.antoniaturcatto.docfind.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DatabaseConfiguration(
    @Value("\${spring.datasource.url}")
    private val url: String,

    @Value("\${spring.datasource.username}")
    val username:String,

    @Value("\${spring.datasource.password}")
    val password: String,

    @Value("\${spring.datasource.driver-class-name}")
    val driver: String) {

    @Bean
    fun hikariDataSource():HikariDataSource{
        val config = HikariConfig()
        config.jdbcUrl = url
        config.username = username
        config.password = password
        config.driverClassName = driver

        //connection pool
        config.maximumPoolSize = 10
        config.minimumIdle = 1
        config.poolName = "docfindPool"
        config.maxLifetime = 600000
        config.connectionTimeout = 100000
        config.connectionTestQuery = "select 1"

        return HikariDataSource(config)
    }
}