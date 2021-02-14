package com.cookieparking.recommend.entity

import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import javax.persistence.*

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name="KEYWORD_TB")
open class Keyword (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null,
    open var cookieId: Long,
    open var phrase: String
){
}