import org.springframework.context.annotation.Bean
import org.springframework.web.filter.CommonsRequestLoggingFilter

@Bean
fun requestLoggingFilter(): CommonsRequestLoggingFilter {
    val loggingFilter = CommonsRequestLoggingFilter()
    loggingFilter.setIncludeClientInfo(true)
    loggingFilter.setIncludeQueryString(true)
    loggingFilter.setIncludePayload(true)
    loggingFilter.setMaxPayloadLength(64000)
    return loggingFilter
}
