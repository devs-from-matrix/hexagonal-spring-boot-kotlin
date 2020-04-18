package packagename.rest

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.ComponentScan
import packagename.domain.port.RequestExample

@SpringBootApplication
@ComponentScan(basePackages = ["packagename"])
class ExamplePoetryRestAdapterApplication {

  @MockBean
  private lateinit var requestExample: RequestExample
}

fun main(args: Array<String>) {
  SpringApplication.run(ExamplePoetryRestAdapterApplication::class.java, *args)
}
