package packagename.repository

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import packagename.domain.port.ObtainExample
import packagename.repository.dao.ExampleDao

@SpringBootApplication
class ExampleJpaAdapterApplication {

  fun main(args: Array<String>) {
    SpringApplication.run(ExampleJpaAdapterApplication::class.java, *args)
  }

  @TestConfiguration
  class ExampleJpaTestConfig {
    @Bean
    fun getObtainExampleRepository(exampleDao: ExampleDao): ObtainExample {
      return ExampleRepository(exampleDao)
    }
  }
}
