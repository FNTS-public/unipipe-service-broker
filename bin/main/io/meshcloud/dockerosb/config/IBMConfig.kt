package io.meshcloud.dockerosb.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.web.firewall.HttpFirewall
import org.springframework.security.web.firewall.StrictHttpFirewall
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.util.UrlPathHelper
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.http.HttpHeaders
import org.springframework.beans.factory.annotation.Value

@Configuration
class WebMvcConfig : WebMvcConfigurer {
    override fun configurePathMatch(configurer: PathMatchConfigurer) {
        val urlPathHelper = UrlPathHelper()
        urlPathHelper.setUrlDecode(false)
        configurer.setUrlPathHelper(urlPathHelper)
    }


    override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping("/**")
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            .allowedHeaders("Accept", "Content-Type", "Authorization", "x-xsrf-token")
            .exposedHeaders(HttpHeaders.AUTHORIZATION)
            .allowCredentials(true)
            .maxAge(500000)
    }
}