package io.meshcloud.dockerosb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class DockerOsbApplication

fun main(args: Array<String>) {
  // allows to use %2F in path segments
  System.setProperty("org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH", "true");
  
  runApplication<DockerOsbApplication>(*args)
}
