package packagename.boot.blocking

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["packagename"])
class ExampleApplication

fun main() {
  SpringApplication.run(ExampleApplication::class.java)
}
