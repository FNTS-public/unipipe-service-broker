package io.meshcloud.dockerosb.config

import org.springframework.context.annotation.Bean
import org.springframework.web.filter.CommonsRequestLoggingFilter
import org.slf4j.LoggerFactory
import org.springframework.web.filter.AbstractRequestLoggingFilter
import javax.servlet.http.HttpServletRequest

class RequestLoggingFilter: AbstractRequestLoggingFilter() {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun shouldLog(request: HttpServletRequest): Boolean {
        return log.isDebugEnabled
    }

    /**
     * Writes a log message before the request is processed.
     */
    override fun beforeRequest(request: HttpServletRequest, message: String) {
        log.debug(message)
    }

    /**
     * Writes a log message after the request is processed.
     */
    override fun afterRequest(request: HttpServletRequest, message: String) {
        log.debug(message)
    }

}

@Bean
fun requestLoggingFilter(): CommonsRequestLoggingFilter {
    val loggingFilter = CommonsRequestLoggingFilter()
    loggingFilter.setIncludeClientInfo(true)
    loggingFilter.setIncludeQueryString(true)
    loggingFilter.setIncludePayload(true)
    loggingFilter.setMaxPayloadLength(64000)
    return loggingFilter
}
