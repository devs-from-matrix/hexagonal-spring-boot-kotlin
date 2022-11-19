package packagename.repository.config

import io.r2dbc.spi.ConnectionFactory
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator
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

  @Bean
  fun initializer(connectionFactory: ConnectionFactory): ConnectionFactoryInitializer {
    val initializer = ConnectionFactoryInitializer()
    initializer.setConnectionFactory(connectionFactory)
    val populator = CompositeDatabasePopulator()
    populator.addPopulators(ResourceDatabasePopulator(ClassPathResource("schema.sql")))
    initializer.setDatabasePopulator(populator)
    return initializer
  }
}