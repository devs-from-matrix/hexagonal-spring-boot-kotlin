package packagename.respository

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Import
import packagename.repository.config.R2dbcAdapterConfig

@SpringBootApplication
class ExampleR2dbcAdapterTestApplication {

  fun main(args: Array<String>) {
    SpringApplication.run(ExampleR2dbcAdapterTestApplication::class.java)
  }

  @TestConfiguration
  @Import(R2dbcAdapterConfig::class)
  class ExampleR2dbcTestConfig
}