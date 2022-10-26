package packagename.rest

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.ComponentScan
import packagename.domain.port.RequestExample

@SpringBootConfiguration
@ComponentScan(basePackages = ["packagename"])
class ExamplePoetryRestTestConfiguration {

    @MockBean
    private lateinit var requestExample: RequestExample
}
