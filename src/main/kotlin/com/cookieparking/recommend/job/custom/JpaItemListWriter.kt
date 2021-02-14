package com.cookieparking.recommend.job.custom

import org.springframework.batch.item.database.JpaItemWriter

open class JpaItemListWriter<T>(private val jpaItemWriter: JpaItemWriter<T>): JpaItemWriter<List<T>>() {
    override fun write(items: List<out List<T>>) {
        val totalList = ArrayList<T>()
        for (list in items) {
            totalList.addAll(list)
        }
        jpaItemWriter.write(totalList)
    }
}