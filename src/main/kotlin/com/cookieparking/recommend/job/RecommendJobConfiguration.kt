package com.cookieparking.recommend.job

import com.cookieparking.recommend.external.Comprehend
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Slf4j
@Configuration
class RecommendJobConfiguration (
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory,
    val comprehend: Comprehend) {

    private val log = LoggerFactory.getLogger(RecommendJobConfiguration::class.java)

    @Bean
    fun job() = jobBuilderFactory.get("recommend")
        .start(extractKeywordStep())
        .build()

    @Bean
    @JobScope
    fun extractKeywordStep(): Step {
        return stepBuilderFactory.get("extractKeyword")
            .tasklet(extractKeywordTasklet(null, null))
            .build()
    }

    @Bean
    @StepScope
    fun extractKeywordTasklet(
        @Value("#{jobParameters[version]}") version: String?,
        @Value("#{jobParameters[userId]}") userId: String?
    ): Tasklet {
        return Tasklet { _, _ ->
            log.info(">>>>> This is Step")
            log.info(">>>>> version: " + version!!)
            log.info(">>>>> userId: " + userId!!)
            val text = "WATCHA가 일하는 방식 - 수습의 시선에서"
            this.comprehend.getKeyPhrases(text)
            RepeatStatus.FINISHED
        }
    }
}