package com.cookieparking.recommend

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableBatchProcessing
@SpringBootApplication
class RecommendApplication

fun main(args: Array<String>) {
    runApplication<RecommendApplication>(*args)
}
