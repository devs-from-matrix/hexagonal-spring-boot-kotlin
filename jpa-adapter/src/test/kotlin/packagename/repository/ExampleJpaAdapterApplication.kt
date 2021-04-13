package packagename.repository

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import packagename.domain.port.ObtainExample
import packagename.repository.dao.ExampleDao

@SpringBootApplication
class ExampleJpaAdapterApplication {

  fun main(args: Array<String>) {
    SpringApplication.run(ExampleJpaAdapterApplication::class.java, *args)
  }

  @TestConfiguration
  @EnableJpaRepositories("packagename.repository.dao", repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean::class)
  class ExampleJpaTestConfig {
    @Bean
    fun getObtainExampleRepository(exampleDao: ExampleDao): ObtainExample {
      return ExampleRepository(exampleDao)
    }
  }
}
