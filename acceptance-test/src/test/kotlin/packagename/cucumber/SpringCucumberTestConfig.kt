package packagename.cucumber

import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import packagename.boot.ExampleApplication

@SpringBootTest(classes = [ExampleApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration
@ActiveProfiles("test")
class SpringCucumberTestConfig
