package packagename

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import packagename.domain.ExampleDomain
import packagename.domain.port.ObtainExample
import packagename.domain.port.RequestExample
import packagename.repository.config.JpaAdapterConfig

@SpringBootApplication
class ExampleE2EApplication {

  @TestConfiguration
  @Import(JpaAdapterConfig::class)
  class ExampleConfig {
    @Bean
    fun getRequestExample(obtainExample: ObtainExample): RequestExample {
      return ExampleDomain(obtainExample)
    }
  }
}

fun main(args: Array<String>) {
  SpringApplication.run(ExampleE2EApplication::class.java)
}
