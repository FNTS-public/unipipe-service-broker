package io.meshcloud.dockerosb.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.CommonsRequestLoggingFilter
import org.slf4j.LoggerFactory
import org.springframework.web.filter.AbstractRequestLoggingFilter
import javax.servlet.http.HttpServletRequest

class RequestLoggingFilter: AbstractRequestLoggingFilter() {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun shouldLog(request: HttpServletRequest): Boolean {
        return true
    }

    /**
     * Writes a log message before the request is processed.
     */
    override fun beforeRequest(request: HttpServletRequest, message: String) {
        log.info(message)
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