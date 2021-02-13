package com.cookieparking.recommend.controller

import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
class JobLauncherController (
    val jobLauncher: JobLauncher,
    val job: Job
) {

    private val log = LoggerFactory.getLogger(JobLauncherController::class.java)

    @GetMapping("/recommend")
    @Throws(Exception::class)
    fun handle(@RequestParam("userId") userId: Long): String? {
        try {
            val version = "recommend" + System.currentTimeMillis().toString()
            val jobParameters = JobParametersBuilder()
                .addString("version", version)
                .addString("userId", userId.toString())
                .toJobParameters()
            jobLauncher!!.run(job, jobParameters)
        } catch (e: Exception) {
            log.info(e.message)
            return e.message
        }
        return "SUCCESS!"
    }
}