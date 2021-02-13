package com.cookieparking.recommend.external
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder;
import com.amazonaws.services.comprehend.model.*
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@PropertySource("classpath:aws.properties")
@Component
class Comprehend (val environment: Environment) {

    private var awsCreds: BasicAWSCredentials = BasicAWSCredentials(environment.getProperty("accessKey"), environment.getProperty("secretKey"))

    private var comprehendClient: AmazonComprehend = AmazonComprehendClientBuilder.standard()
        .withCredentials(AWSStaticCredentialsProvider(awsCreds))
        .withRegion("ap-northeast-2")
        .build()

    fun getKeyPhrases (text: String) = run {
        val languageCode = this.getDominantLanguage(text)
        val detectKeyPhrasesRequest:DetectKeyPhrasesRequest = DetectKeyPhrasesRequest().withText(text)
            .withLanguageCode(languageCode)
        val detectKeyPhrasesResult:DetectKeyPhrasesResult = this.comprehendClient.detectKeyPhrases(detectKeyPhrasesRequest)
        detectKeyPhrasesResult.getKeyPhrases().forEach(System.out::println)
    }

    private fun getDominantLanguage (text: String): String = run {
        val detectDominantLanguageRequest: DetectDominantLanguageRequest = DetectDominantLanguageRequest().withText(text)
        val detectDominantLanguageResult: DetectDominantLanguageResult = this.comprehendClient.detectDominantLanguage(detectDominantLanguageRequest)
        var dominantLanguage: String = ""
        var dominantLanguageScore: Float = 0.0F
        detectDominantLanguageResult.getLanguages().forEach { em ->
            if (em.score > dominantLanguageScore) {
                dominantLanguageScore = em.score
                dominantLanguage = em.languageCode
            }
        }
        return dominantLanguage
    }
}