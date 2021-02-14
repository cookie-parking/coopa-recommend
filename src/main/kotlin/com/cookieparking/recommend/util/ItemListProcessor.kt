package com.cookieparking.recommend.util

import com.amazonaws.services.comprehend.model.KeyPhrase
import com.cookieparking.recommend.dto.CreateKeywordDTO
import com.cookieparking.recommend.entity.Cookie
import com.cookieparking.recommend.entity.Keyword
import com.cookieparking.recommend.external.Comprehend
import org.springframework.batch.item.ItemProcessor

class ItemListProcessor(private val comprehend: Comprehend): ItemProcessor<Cookie, List<Keyword>> {
    override fun process(cookie: Cookie): List<Keyword> {
        val text = cookie.title
        val keyPhrases = this.comprehend.getKeyPhrases(text)
        val keywords = keyPhrases!!.map { keyPhrase: KeyPhrase ->
            CreateKeywordDTO(cookie.id!!, keyPhrase.text).toEntity()
        }.filter { keyword -> keyword.phrase.length > 1 }
        return keywords
    }
}