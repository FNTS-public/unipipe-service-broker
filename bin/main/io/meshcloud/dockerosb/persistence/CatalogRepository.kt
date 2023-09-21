package io.meshcloud.dockerosb.persistence

import io.meshcloud.dockerosb.config.MeshServiceDefinition
import mu.KotlinLogging
import org.springframework.cloud.servicebroker.model.catalog.Catalog
import org.springframework.cloud.servicebroker.model.catalog.ServiceDefinition
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private val log = KotlinLogging.logger { }

@Configuration
class CatalogRepository(
    private val yamlHandler: YamlHandler,
    private val gitHandler: GitHandler
) {

  @Bean
  fun catalog(): Catalog {
    return getCatalog()
  }

  fun getCatalog(): Catalog {
    val catalogYml = gitHandler.fileInRepo("catalog.yml")

    if (!catalogYml.isFile) {
      log.error { "Could not read catalog.yml file from '${catalogYml.absolutePath}'. Will start with an empty catalog." }
      return Catalog.builder().build()
    }

    val catalog = yamlHandler.readObject(catalogYml, YamlCatalog::class.java)

    val regexPattern = Regex("^[-a-zA-Z0-9\\s]+\$")
    if (catalog.services.any { it -> !(regexPattern.containsMatchIn(it.id)) })
      throw IllegalArgumentException("ServiceDefinitionId cannot contain any characters other than a-z, A-Z, 0-9 and - in your catalog!")
    else
      return Catalog.builder()
            .serviceDefinitions(catalog.services)
            .build()
  }

  class YamlCatalog(
      val services: List<MeshServiceDefinition>
  )
}
