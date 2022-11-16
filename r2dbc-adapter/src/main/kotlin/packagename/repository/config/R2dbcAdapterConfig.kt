package packagename.repository.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import packagename.domain.port.ObtainExample
import packagename.repository.ExampleRepository
import packagename.repository.dao.ExampleDao


@Configuration
@EntityScan("packagename.repository.entity")
@EnableR2dbcRepositories("packagename.repository.dao")
class R2dbcAdapterConfig {

  @Bean
  fun getExampleRepository(exampleDao: ExampleDao): ObtainExample {
    return ExampleRepository(exampleDao)
  }
}