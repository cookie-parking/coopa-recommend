package com.cookieparking.recommend.job

import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Slf4j
@Configuration
class SimpleJobConfiguration {

    @Autowired
    lateinit var stepBuilderFactory: StepBuilderFactory

    @Autowired
    lateinit var jobBuilderFactory: JobBuilderFactory

    @Bean
    fun step1() = stepBuilderFactory.get("step1")
            .tasklet { stepContribution, chunkContext ->
                println("This is a batch job in Kotlin")
                RepeatStatus.FINISHED
            }
            .build()

    @Bean
    fun job() = jobBuilderFactory.get("job1")
            .start(step1())
            .build()
}