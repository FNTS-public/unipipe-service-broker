package io.meshcloud.dockerosb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class DockerOsbApplication

fun main(args: Array<String>) {
  System.setProperty("org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH", "true");
 
  @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
      UrlPathHelper urlPathHelper = new UrlPathHelper();
      urlPathHelper.setUrlDecode(false);
      configurer.setUrlPathHelper(urlPathHelper);
    }
  runApplication<DockerOsbApplication>(*args)
}
