package packagename.repository

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Import
import packagename.repository.config.JpaAdapterConfig

@SpringBootApplication
class ExampleJpaAdapterApplication {

    fun main(args: Array<String>) {
        SpringApplication.run(ExampleJpaAdapterApplication::class.java)
    }

    @TestConfiguration
    @Import(JpaAdapterConfig::class)
    class ExampleJpaTestConfig
}
