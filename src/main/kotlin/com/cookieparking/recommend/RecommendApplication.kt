package com.cookieparking.recommend

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@EnableBatchProcessing
@SpringBootApplication
class RecommendApplication

fun main(args: Array<String>) {
    SpringApplication.run(RecommendApplication::class.java, *args)
}