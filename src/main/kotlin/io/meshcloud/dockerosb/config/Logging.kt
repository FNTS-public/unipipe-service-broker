package io.meshcloud.dockerosb.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.CommonsRequestLoggingFilter
import org.slf4j.LoggerFactory
import org.springframework.web.filter.AbstractRequestLoggingFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.ServletRequest
import java.io.BufferedReader
import org.springframework.web.util.ContentCachingRequestWrapper;

val DEBUG_MODE = System.getenv("DEBUG_MODE")

class RequestLoggingFilter: AbstractRequestLoggingFilter() {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun shouldLog(request: HttpServletRequest): Boolean {
        return true
    }

    /**
     * Writes a log message before the request is processed.
     */
    override fun beforeRequest(request: HttpServletRequest, message: String) {
        if (DEBUG_MODE == null ||  DEBUG_MODE == "false") {
        } else {
            var thisrequest = ContentCachingRequestWrapper(request);
            log.info(message)
            val sb = StringBuilder()
            val reader: BufferedReader = thisrequest.reader
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                sb.append(line)
            }
            val requestBody: String = sb.toString()
            log.info(requestBody)
        }
    }

    /**
     * Writes a log message after the request is processed.
     */
    override fun afterRequest(request: HttpServletRequest, message: String) {
        log.info(message)
    }

}

@Configuration
class SpringBootRequestLoggingConfiguration {
    @Bean
    fun requestLoggingFilter(): RequestLoggingFilter {
        val filter = RequestLoggingFilter()
        filter.setIncludeClientInfo(false)
        filter.setIncludeQueryString(true)
        filter.setIncludePayload(false)
        filter.setMaxPayloadLength(8000)
        filter.setIncludeHeaders(false)

        return filter
    }

}