package com.cookieparking.recommend.dto

import com.cookieparking.recommend.entity.Keyword

data class CreateKeywordDTO (
    val cookieId: Long,
    val phrase: String
){
    fun toEntity(): Keyword {
        return Keyword(
            cookieId = cookieId,
            phrase = phrase
        )
    }
}