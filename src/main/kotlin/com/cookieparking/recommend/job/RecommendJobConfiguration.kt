package com.cookieparking.recommend.job

import com.cookieparking.recommend.entity.Cookie
import com.cookieparking.recommend.entity.Keyword
import com.cookieparking.recommend.external.Comprehend
import com.cookieparking.recommend.util.ItemListProcessor
import com.cookieparking.recommend.util.JpaItemListWriter
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManagerFactory

@Slf4j
@Configuration
class RecommendJobConfiguration (
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory,
    val entityManagerFactory: EntityManagerFactory,
    val comprehend: Comprehend
) {

    private val log = LoggerFactory.getLogger(RecommendJobConfiguration::class.java)

    @Bean
    fun job() = jobBuilderFactory.get("recommend")
        .start(extractKeywordStep())
        .build()

    private fun extractKeywordStep(): Step {
        return stepBuilderFactory.get("cookieReaderStep")
            .chunk<Cookie, List<Keyword>>(100)
            .reader(cookieReader(null))
            .processor(extractKeywordProcessor(null))
            .writer(keywordWriterList())
            .build()
    }

    @Bean
    @StepScope
    fun cookieReader(@Value("#{jobParameters[userId]}") userId: String?): JpaPagingItemReader<Cookie> {
        val reader:JpaPagingItemReader<Cookie> = JpaPagingItemReader()
        reader.setQueryString("SELECT c FROM Cookie c WHERE userId = $userId")
        reader.setEntityManagerFactory(entityManagerFactory)
        reader.pageSize = 100
        return reader
    }

    @Bean
    @StepScope
    fun extractKeywordProcessor (
        @Value("#{jobParameters[version]}") version: String?,
    ): ItemProcessor<Cookie, List<Keyword>> {
        return ItemListProcessor(comprehend)
    }

    private fun keywordWriterList(): JpaItemListWriter<Keyword> {
        val jpaItemWriter: JpaItemWriter<Keyword> = JpaItemWriter()
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory)
        return JpaItemListWriter(jpaItemWriter)
    }
}