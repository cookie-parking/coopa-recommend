package com.cookieparking.recommend.entity

import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import javax.persistence.*

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name="COOKIE_TB")
open class Cookie (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null,
    @Column(name="user_id")
    open var userId: Long,
    open var link: String?,
    open var title: String,
    open var content: String?,
    open var provider: String?
){
}