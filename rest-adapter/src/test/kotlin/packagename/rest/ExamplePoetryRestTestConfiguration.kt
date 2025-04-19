package packagename.rest

import org.springframework.boot.SpringBootConfiguration
import org.springframework.context.annotation.ComponentScan

@SpringBootConfiguration
@ComponentScan(basePackages = ["packagename"])
class ExamplePoetryRestTestConfiguration
