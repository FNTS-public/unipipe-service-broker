package io.meshcloud.dockerosb.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.util.UrlPathHelper

@Configuration
class SpringApplicationConfig {
    @Override
    fun configurePathMatch(configurer: PathMatchConfigurer) {
      UrlPathHelper helper = new UrlPathHelper() {
        setUrlDecode(false)
        }
     configurer.setUrlPathHelper(helper)
    }
}